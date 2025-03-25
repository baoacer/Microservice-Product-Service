package gdu.product_service.usecase.inventory.impl;

import gdu.product_service.dto.model.InventoryDto;
import gdu.product_service.dto.request.inventory.CreateInventoryRequest;
import gdu.product_service.entity.InventoryEntity;
import gdu.product_service.entity.ProductEntity;
import gdu.product_service.exception.NotFoundException;
import gdu.product_service.repository.InventoryRepository;
import gdu.product_service.repository.ProductRepository;
import gdu.product_service.usecase.inventory.CreateInventoryUseCase;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CreateInventoryUseCaseImpl implements CreateInventoryUseCase {
    private final InventoryRepository inventoryRepository;
    private final ProductRepository productRepository;
    private static final Logger log = LoggerFactory.getLogger(CreateInventoryUseCaseImpl.class);

    @Override
    public InventoryDto execute(CreateInventoryRequest request) {
        try {
            ProductEntity product = this.productRepository.findById(request.getProductId())
                    .orElseThrow(() -> new NotFoundException("Product not found"));

            InventoryEntity inventory = new InventoryEntity();
            inventory.setProduct(product);
            inventory.setStock(request.getQuantity());
            inventory.setStatus(request.getStatus());

            InventoryEntity saveInventory = this.inventoryRepository.save(inventory);

            return InventoryDto.builder()
                    .id(saveInventory.getId())
                    .productId(saveInventory.getProduct().getId())
                    .stock(saveInventory.getStock())
                    .status(saveInventory.getStatus())
                    .updatedAt(saveInventory.getUpdatedAt())
                    .build();
        } catch (RuntimeException e) {
            log.error("CreateInventoryUseCaseImpl Execute Error: {}", e.getMessage(), e);
            throw new RuntimeException("CreateInventoryUseCaseImpl Execute Error", e);
        }
    }
}
