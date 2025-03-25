package gdu.product_service.usecase.product;

import gdu.product_service.dto.request.product.SearchProductRequest;
import gdu.product_service.dto.response.SearchResponse;
import org.springframework.data.domain.Page;


public interface SearchProductUseCase {
    Page<SearchResponse> execute(SearchProductRequest request);
}
