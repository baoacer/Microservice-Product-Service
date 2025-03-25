package gdu.product_service.usecase.category;

import gdu.product_service.dto.model.CategoryDto;
import gdu.product_service.dto.request.category.CreateCategoryRequest;

public interface CreateCategoryUseCase {
    CategoryDto execute(CreateCategoryRequest request);
}
