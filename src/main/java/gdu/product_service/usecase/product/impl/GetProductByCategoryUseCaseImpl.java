package gdu.product_service.usecase.product.impl;

import gdu.product_service.dto.model.ImagesDto;
import gdu.product_service.dto.model.InventoryDto;
import gdu.product_service.dto.model.ProductDto;
import gdu.product_service.dto.request.product.GetAllProductByCategoryRequest;
import gdu.product_service.dto.response.ObjectResponse;
import gdu.product_service.entity.CategoryEntity;
import gdu.product_service.entity.ProductEntity;
import gdu.product_service.exception.NotFoundException;
import gdu.product_service.repository.CategoryRepository;
import gdu.product_service.repository.ProductRepository;
import gdu.product_service.usecase.product.GetProductByCategoryUseCase;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class GetProductByCategoryUseCaseImpl implements GetProductByCategoryUseCase {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    @Override
    public ObjectResponse<ProductDto> execute(
            GetAllProductByCategoryRequest request
    ) {
        Sort sort = Sort.by(request.getSortDirection(), request.getSortBy());
        PageRequest pageable = PageRequest.of(request.getPage(), request.getSize(), sort);
        CategoryEntity foundCategory = this.categoryRepository.findById(request.getCategoryId()).orElseThrow(
                () -> new NotFoundException("Category not found"));
        Page<ProductEntity> pageResult = this.productRepository.findAllByCategory(foundCategory, pageable);

        List<ProductDto> content = pageResult
                .getContent()
                .stream()
                .map(product -> ProductDto.builder()
                        .id(product.getId())
                        .name(product.getName())
                        .price(product.getPrice())
                        .description(product.getDescription())
                        .inventory(InventoryDto
                                .builder()
                                .id(product.getInventory().getId())
                                .stock(product.getInventory().getStock())
                                .status(product.getInventory().getStatus())
                                .updatedAt(product.getInventory().getUpdatedAt())
                                .build())
                        .images(product.getImages().stream()
                                .map(image -> ImagesDto.builder()
                                        .id(image.getId())
                                        .src(image.getSrc())
                                        .position(image.getPosition())
                                        .build())
                                .toList()
                        )
                        .build()).toList();

        ObjectResponse<ProductDto> response = new ObjectResponse<>();
        response.setContent(content);
        response.setTotalPages(pageResult.getTotalPages());
        response.setTotalElements(pageResult.getTotalElements());
        response.setSize(pageResult.getSize());
        response.setNumber(pageResult.getNumber());

        return response;

    }
}
