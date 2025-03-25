package gdu.product_service.usecase.category;

import gdu.product_service.dto.model.CategoryDto;

import java.util.List;

public interface GetAllCategoryUseCase {
    List<CategoryDto> execute();
}
