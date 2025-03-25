package gdu.product_service.usecase.product;

import gdu.product_service.dto.model.ProductDto;

public interface GetProductByIdUseCase {
    ProductDto execute(short productId);
}
