package gdu.product_service.usecase.product.impl;

import gdu.product_service.dto.model.ImagesDto;
import gdu.product_service.dto.model.InventoryDto;
import gdu.product_service.dto.model.ProductDto;
import gdu.product_service.entity.CategoryEntity;
import gdu.product_service.entity.InventoryEntity;
import gdu.product_service.entity.ProductEntity;
import gdu.product_service.exception.NotFoundException;
import gdu.product_service.repository.CategoryRepository;
import gdu.product_service.repository.InventoryRepository;
import gdu.product_service.repository.ProductRepository;
import gdu.product_service.usecase.product.GetProductByIdUseCase;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class GetProductByIdUseCaseImpl implements GetProductByIdUseCase {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final InventoryRepository inventoryRepository;
    private final Logger logger = LoggerFactory.getLogger(GetProductByIdUseCaseImpl.class);

    @Override
    public ProductDto execute(short productId) {
        try {
            ProductEntity foundProduct = this.productRepository.findById(productId)
                    .orElseThrow(() -> new NotFoundException("Product not found"));

            CategoryEntity category = this.categoryRepository.findById(foundProduct.getCategory().getId())
                    .orElseThrow(() -> new NotFoundException("Category not found"));

            InventoryEntity inventory = this.inventoryRepository.findById(foundProduct.getInventory().getId())
                    .orElseThrow(() -> new NotFoundException("Inventory not found"));


            return ProductDto
                    .builder()
                    .id(foundProduct.getId())
                    .name(foundProduct.getName())
                    .price(foundProduct.getPrice())
                    .description(foundProduct.getDescription())
                    .category(category.getName())
                    .inventory(InventoryDto
                            .builder()
                            .id(inventory.getId())
                            .stock(inventory.getStock())
                            .status(inventory.getStatus())
                            .updatedAt(inventory.getUpdatedAt())
                            .build()
                    )
                    .images(foundProduct.getImages().stream()
                            .map(image -> ImagesDto.builder()
                                    .id(image.getId())
                                    .src(image.getSrc())
                                    .position(image.getPosition())
                                    .build())
                            .toList()
                    )
                    .build();
        } catch (RuntimeException e) {
            logger.error("GetProductByIdUseCase execute error {}", e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }
}
