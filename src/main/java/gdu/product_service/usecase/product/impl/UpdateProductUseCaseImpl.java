package gdu.product_service.usecase.product.impl;

import gdu.product_service.dto.model.ImagesDto;
import gdu.product_service.dto.model.ProductDto;
import gdu.product_service.dto.request.product.UpdateProductRequest;
import gdu.product_service.dto.response.UpdateProductResponse;
import gdu.product_service.entity.CategoryEntity;
import gdu.product_service.entity.ImagesEntity;
import gdu.product_service.entity.InventoryEntity;
import gdu.product_service.entity.ProductEntity;
import gdu.product_service.exception.NotFoundException;
import gdu.product_service.repository.CategoryRepository;
import gdu.product_service.repository.ImageRepository;
import gdu.product_service.repository.InventoryRepository;
import gdu.product_service.repository.ProductRepository;
import gdu.product_service.usecase.product.UpdateProductUseCase;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
@Transactional
public class UpdateProductUseCaseImpl implements UpdateProductUseCase {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final InventoryRepository inventoryRepository;
    private final ImageRepository imageRepository;
    private static final Logger log = LoggerFactory.getLogger(UpdateProductUseCaseImpl.class);

    @Override
    public UpdateProductResponse execute(UpdateProductRequest request) {
        try {
            ProductEntity foundProduct = this.productRepository.findById(request.getProductId())
                    .orElseThrow(() -> new NotFoundException("Product not found"));

            CategoryEntity category = this.categoryRepository.findByName(request.getCategory());

            if(category == null) {
                throw new NotFoundException("Category not found");
            }

            InventoryEntity inventory = this.inventoryRepository.findByProduct_Id(foundProduct.getId());
            inventory.setStock(request.getQuantity());

            Byte maxPosition = imageRepository.getMaxPosition(foundProduct.getId());

            if(request.getName() != null){
                foundProduct.setName(request.getName());
            }
            if(request.getDescription() != null){
                foundProduct.setDescription(request.getDescription());
            }
            if(request.getPrice() != null){
                foundProduct.setPrice(request.getPrice());
            }
            if(request.getImages() != null){
                List<ImagesEntity> images = new ArrayList<>();

                for(ImagesDto image : request.getImages()) {
                    images.add(
                            ImagesEntity
                                    .builder()
                                    .src(image.getSrc())
                                    .position((maxPosition != null) ? (byte) (maxPosition + 1) : 1)
                                    .product(foundProduct)
                                    .build()
                    );
                }
                foundProduct.getImages().addAll(images);
            }
            foundProduct.setCategory(category);
            foundProduct.setInventory(inventory);


            ProductEntity updatedProduct = this.productRepository.save(foundProduct);
            this.inventoryRepository.save(inventory);

            List<ImagesDto> imageDtos = updatedProduct.getImages().stream().map(
                    img -> ImagesDto.builder()
                            .id(img.getId())
                            .src(img.getSrc())
                            .position(img.getPosition())
                            .build()
            ).toList();

            return UpdateProductResponse
                    .builder()
                    .id(updatedProduct.getId())
                    .name(updatedProduct.getName())
                    .description(updatedProduct.getDescription())
                    .price(updatedProduct.getPrice())
                    .categoryId(updatedProduct.getCategory().getId())
                    .stock(updatedProduct.getInventory().getStock())
                    .images(imageDtos)
                    .build();
        } catch (RuntimeException e) {
            log.error("UpdateProductUseCase execute error {} ", e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }
}
