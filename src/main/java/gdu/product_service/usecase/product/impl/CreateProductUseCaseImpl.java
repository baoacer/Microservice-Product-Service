package gdu.product_service.usecase.product.impl;

import gdu.product_service.dto.model.ImagesDto;
import gdu.product_service.dto.model.InventoryDto;
import gdu.product_service.dto.model.ProductDto;
import gdu.product_service.dto.request.inventory.CreateInventoryRequest;
import gdu.product_service.dto.request.product.CreateProductRequest;
import gdu.product_service.entity.CategoryEntity;
import gdu.product_service.entity.ImagesEntity;
import gdu.product_service.entity.ProductEntity;
import gdu.product_service.exception.NotFoundException;
import gdu.product_service.repository.CategoryRepository;
import gdu.product_service.repository.ImageRepository;
import gdu.product_service.repository.ProductRepository;
import gdu.product_service.usecase.inventory.CreateInventoryUseCase;
import gdu.product_service.usecase.product.CreateProductUseCase;
import gdu.product_service.utils.InventoryStatus;
import gdu.product_service.utils.SlugUtils;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class CreateProductUseCaseImpl implements CreateProductUseCase {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final ImageRepository imageRepository;
    private final CreateInventoryUseCase createInventoryUseCase;


    private final Logger logger = LoggerFactory.getLogger(CreateProductUseCaseImpl.class);

    @Override
    public ProductDto execute(CreateProductRequest request) {
        try {
            CategoryEntity category = categoryRepository.findById(request.getCategoryId())
                    .orElseThrow(() -> new NotFoundException("Category not found"));

            byte position = 0;

            ProductEntity product = new ProductEntity();
            product.setName(request.getName());
            product.setDescription(request.getDescription());
            product.setPrice(request.getPrice());
            product.setCategory(category);

            List<ImagesEntity> images = new ArrayList<>();

            for(ImagesDto image : request.getImages()) {
                images.add(
                        ImagesEntity
                                .builder()
                                .src(image.getSrc())
                                .position(++position)
                                .product(product)
                                .build()
                );
            }

            product.setImages(images);

            ProductEntity saveProduct = productRepository.save(product);

            // Inventory
            CreateInventoryRequest inventoryRequest = new CreateInventoryRequest();
            inventoryRequest.setProductId(saveProduct.getId());
            inventoryRequest.setQuantity(request.getQuantity());
            inventoryRequest.setStatus(request.getQuantity() != 0 ? InventoryStatus.IN_STOCK : InventoryStatus.OUT_OF_STOCK);
            InventoryDto inventoryDto = this.createInventoryUseCase.execute(inventoryRequest);

            List<ImagesDto> imageDtos = saveProduct.getImages().stream().map(
                    img -> ImagesDto.builder()
                            .id(img.getId())
                            .src(img.getSrc())
                            .position(img.getPosition())
                            .build()
            ).toList();

            return ProductDto.builder()
                    .id(saveProduct.getId())
                    .name(saveProduct.getName())
                    .price(saveProduct.getPrice())
                    .description(saveProduct.getDescription())
                    .category(saveProduct.getCategory().getName())
                    .inventory(inventoryDto)
                    .images(imageDtos)
                    .build();
        }catch (NotFoundException e) {
            this.logger.error("CreateProductUseCase execute failed {}", e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }
}
