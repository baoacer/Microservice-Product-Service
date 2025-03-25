package gdu.product_service.usecase.product;

import gdu.product_service.dto.model.ProductDto;
import gdu.product_service.dto.request.product.CreateProductRequest;

public interface CreateProductUseCase {
    ProductDto execute(CreateProductRequest request);
}
