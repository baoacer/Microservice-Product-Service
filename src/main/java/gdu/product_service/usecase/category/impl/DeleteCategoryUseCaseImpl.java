package gdu.product_service.usecase.category.impl;

import gdu.product_service.entity.CategoryEntity;
import gdu.product_service.exception.NotFoundException;
import gdu.product_service.repository.CategoryRepository;
import gdu.product_service.usecase.category.DeleteCategoryUseCase;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@Transactional
@AllArgsConstructor
public class DeleteCategoryUseCaseImpl implements DeleteCategoryUseCase {

    private CategoryRepository categoryRepository;

    @Override
    public Boolean execute(byte categoryId) {
        this.categoryRepository.findById(categoryId)
                .orElseThrow(() -> new NotFoundException("Category not found"));

        this.categoryRepository.deleteById(categoryId);

        return this.categoryRepository.existsById(categoryId);
    }
}
