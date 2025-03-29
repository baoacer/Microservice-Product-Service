package gdu.product_service.usecase.product;

import gdu.product_service.dto.model.ProductDto;
import gdu.product_service.dto.request.product.GetAllProductByCategoryRequest;
import gdu.product_service.dto.response.ObjectResponse;

public interface GetProductByCategoryUseCase {
    ObjectResponse<ProductDto> execute(GetAllProductByCategoryRequest request);
}
