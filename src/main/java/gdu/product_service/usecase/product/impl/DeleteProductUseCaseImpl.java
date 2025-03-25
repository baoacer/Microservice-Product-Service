package gdu.product_service.usecase.product.impl;

import gdu.product_service.entity.ProductEntity;
import gdu.product_service.exception.NotFoundException;
import gdu.product_service.repository.ProductRepository;
import gdu.product_service.usecase.product.DeleteProductUseCase;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class DeleteProductUseCaseImpl implements DeleteProductUseCase {

    private final ProductRepository productRepository;
    private final Logger logger = LoggerFactory.getLogger(DeleteProductUseCaseImpl.class);


    @Override
    public Boolean execute(short productId) {
        try {
            ProductEntity foundProduct = this.productRepository.findById(productId)
                    .orElseThrow(() -> new NotFoundException("Product not found"));

            this.productRepository.delete(foundProduct);

            return this.productRepository.existsById(productId);
        } catch (RuntimeException e) {
            this.logger.error("DeleteProductUseCase execute error {}", e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }
}