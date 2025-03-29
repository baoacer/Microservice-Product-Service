package gdu.product_service.usecase.product.impl;

import gdu.product_service.dto.model.ImagesDto;
import gdu.product_service.dto.request.product.SearchProductRequest;
import gdu.product_service.dto.response.SearchResponse;
import gdu.product_service.entity.ProductEntity;
import gdu.product_service.usecase.product.SearchProductUseCase;
import gdu.product_service.utils.Contains;
import jakarta.persistence.EntityManager;
import lombok.AllArgsConstructor;
import org.hibernate.search.engine.search.query.SearchResult;
import org.hibernate.search.mapper.orm.Search;
import org.hibernate.search.mapper.orm.scope.SearchScope;
import org.hibernate.search.mapper.orm.session.SearchSession;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class SearchProductUseCaseImpl implements SearchProductUseCase {

    private EntityManager entityManager;

    @Override
    public Page<SearchResponse> execute(SearchProductRequest request) {
        try {
            SearchSession searchSession = Search.session(entityManager);
            SearchScope<ProductEntity> scope = searchSession.scope(ProductEntity.class);

            Pageable pageable = PageRequest.of(request.getPage(), request.getSize());
            byte offset = (byte) pageable.getOffset();
            byte limit = (byte) pageable.getPageSize();

            SearchResult<ProductEntity> result = searchSession.search(scope)
                    .where(
                            scope.predicate().match()
                            .field("name")
                            .matching(request.getQuery())
                            .toPredicate()
                    ).fetch((int) limit, (int) offset);
            List<ProductEntity> products = result.hits();


            List<SearchResponse> responses = products.stream().map(
                    product -> SearchResponse
                            .builder()
                            .id(product.getId())
                            .name(product.getName())
                            .description(product.getDescription())
                            .price(product.getPrice())
                            .images(product.getImages().stream().map(
                                    image -> ImagesDto
                                            .builder()
                                            .id(image.getId())
                                            .src(image.getSrc())
                                            .position(image.getPosition())
                                            .build()
                                    ).toList()
                            )
                            .build()).toList();

            return new PageImpl<>(responses, pageable, result.total().hitCount());
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
    }
}
