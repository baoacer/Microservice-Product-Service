package gdu.product_service.usecase.category.impl;

import gdu.product_service.dto.model.CategoryDto;
import gdu.product_service.entity.CategoryEntity;
import gdu.product_service.repository.CategoryRepository;
import gdu.product_service.usecase.category.GetAllCategoryUseCase;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class GetAllCategoryUseCaseImpl implements GetAllCategoryUseCase {

    private final CategoryRepository categoryRepository;
    private final Logger logger = LoggerFactory.getLogger(GetAllCategoryUseCaseImpl.class);

    @Override
    public List<CategoryDto> execute() {
       try {
           List<CategoryEntity> categories = categoryRepository.findAll();
           return categories.stream().map(
                           category -> CategoryDto
                                   .builder()
                                   .id(category.getId())
                                   .name(category.getName())
                                   .created_at(category.getCreatedAt())
                                   .updated_at(category.getUpdatedAt())
                                   .build())
                   .collect(Collectors.toList());
       } catch (RuntimeException e) {
           logger.error("GetAllCategoryUseCase execute error", e);
           throw new RuntimeException(e);
       }
    }
}
