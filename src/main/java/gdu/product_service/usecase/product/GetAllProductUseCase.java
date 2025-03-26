package gdu.product_service.usecase.product;

import gdu.product_service.dto.model.ProductDto;
import gdu.product_service.dto.request.product.GetAllProductRequest;
import gdu.product_service.dto.response.ObjectResponse;

public interface GetAllProductUseCase {
    ObjectResponse<ProductDto> execute(GetAllProductRequest request);
}
