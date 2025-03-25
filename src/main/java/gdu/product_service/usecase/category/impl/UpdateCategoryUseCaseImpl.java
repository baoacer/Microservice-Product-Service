package gdu.product_service.usecase.category.impl;

import gdu.product_service.dto.model.CategoryDto;
import gdu.product_service.dto.request.category.UpdateCategoryRequest;
import gdu.product_service.entity.CategoryEntity;
import gdu.product_service.exception.NotFoundException;
import gdu.product_service.repository.CategoryRepository;
import gdu.product_service.usecase.category.UpdateCategoryUseCase;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Transactional
public class UpdateCategoryUseCaseImpl implements UpdateCategoryUseCase {

    private final CategoryRepository categoryRepository;

    @Override
    public CategoryDto execute(UpdateCategoryRequest request) {
        CategoryEntity category = categoryRepository.findById(request.getId())
                .orElseThrow(() -> new NotFoundException("Category Not Found"));

        category.setName(request.getName());

        CategoryEntity savedCategory = categoryRepository.save(category);

        return CategoryDto
                .builder()
                .id(savedCategory.getId())
                .name(savedCategory.getName())
                .build();
    }
}
