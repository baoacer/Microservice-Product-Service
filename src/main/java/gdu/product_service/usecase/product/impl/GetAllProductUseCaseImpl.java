package gdu.product_service.usecase.product.impl;

import gdu.product_service.dto.model.ImagesDto;
import gdu.product_service.dto.model.InventoryDto;
import gdu.product_service.dto.request.product.GetAllProductRequest;
import gdu.product_service.dto.response.GetProductResponse;
import gdu.product_service.entity.ProductEntity;
import gdu.product_service.repository.CategoryRepository;
import gdu.product_service.repository.InventoryRepository;
import gdu.product_service.repository.ProductRepository;
import gdu.product_service.usecase.product.GetAllProductUseCase;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class GetAllProductUseCaseImpl implements GetAllProductUseCase {
    private ProductRepository productRepository;
    private CategoryRepository categoryRepository;
    private InventoryRepository inventoryRepository;

    private static final Logger logger = LoggerFactory.getLogger(GetAllProductUseCaseImpl.class);

    @Override
    public Page<GetProductResponse> execute(GetAllProductRequest request) {
        try {
            PageRequest pageable = PageRequest.of(request.getPage(), request.getSize());
            Page<ProductEntity> products = this.productRepository.findAll(pageable);

            return products.map(product -> GetProductResponse.builder()
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
                                    .alt(image.getAlt())
                                    .position(image.getPosition())
                                    .build())
                            .toList()
                    )
                    .build());
        }catch (RuntimeException e) {
            logger.error("GetAllProductUseCase execute error {}", e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }
}
