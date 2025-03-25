package gdu.product_service.usecase.product;

import gdu.product_service.dto.request.product.GetAllProductRequest;
import gdu.product_service.dto.response.GetProductResponse;
import org.springframework.data.domain.Page;

public interface GetAllProductUseCase {
    Page<GetProductResponse> execute(GetAllProductRequest request);
}
