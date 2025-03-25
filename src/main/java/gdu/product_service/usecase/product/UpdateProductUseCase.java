package gdu.product_service.usecase.product;

import gdu.product_service.dto.model.ProductDto;
import gdu.product_service.dto.request.product.UpdateProductRequest;
import gdu.product_service.dto.response.UpdateProductResponse;

public interface UpdateProductUseCase {
    UpdateProductResponse execute(UpdateProductRequest request);
}
