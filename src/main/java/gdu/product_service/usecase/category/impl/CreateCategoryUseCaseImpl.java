package gdu.product_service.usecase.category.impl;

import gdu.product_service.dto.model.CategoryDto;
import gdu.product_service.dto.request.category.CreateCategoryRequest;
import gdu.product_service.dto.response.Response;
import gdu.product_service.entity.CategoryEntity;
import gdu.product_service.repository.CategoryRepository;
import gdu.product_service.usecase.category.CreateCategoryUseCase;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
@Transactional
@AllArgsConstructor
public class CreateCategoryUseCaseImpl implements CreateCategoryUseCase {

    private CategoryRepository categoryRepository;
    private final Logger logger = LoggerFactory.getLogger(CreateCategoryUseCaseImpl.class);

    @Override
    public CategoryDto execute(CreateCategoryRequest request) {
        try {
            CategoryEntity category = new CategoryEntity();
            category.setName(request.getName());
            CategoryEntity saveCategory = categoryRepository.save(category);

            return CategoryDto.builder()
                    .id(saveCategory.getId())
                    .name(saveCategory.getName())
                    .build();
        } catch (RuntimeException e) {
            this.logger.error("CreateCategoryUseCaseImpl execute Error", e);
            throw new RuntimeException(e);
        }
    }
}
