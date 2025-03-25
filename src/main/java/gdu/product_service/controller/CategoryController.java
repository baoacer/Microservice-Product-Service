package gdu.product_service.controller;

import gdu.product_service.dto.model.CategoryDto;
import gdu.product_service.dto.request.category.CreateCategoryRequest;
import gdu.product_service.dto.request.category.UpdateCategoryRequest;
import gdu.product_service.usecase.category.CreateCategoryUseCase;
import gdu.product_service.usecase.category.DeleteCategoryUseCase;
import gdu.product_service.usecase.category.GetAllCategoryUseCase;
import gdu.product_service.usecase.category.UpdateCategoryUseCase;
import lombok.AllArgsConstructor;
import lombok.Builder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/api/v1")
@AllArgsConstructor
@Builder
public class CategoryController {

    private final CreateCategoryUseCase createCategoryUseCase;
    private final UpdateCategoryUseCase updateCategoryUseCase;
    private final GetAllCategoryUseCase getAllCategoryUseCase;
    private final DeleteCategoryUseCase deleteCategoryUseCase;


    @GetMapping("/category")
    public ResponseEntity<List<CategoryDto>> category() {
        List<CategoryDto> categoryDtos = this.getAllCategoryUseCase.execute();
        return ResponseEntity.status(HttpStatus.OK).body(categoryDtos);
    }

    @PostMapping("/category")
    public ResponseEntity<CategoryDto> createCategory(@RequestBody CreateCategoryRequest request) {
        CategoryDto dto = this.createCategoryUseCase.execute(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(dto);
    }

    @PutMapping("/category/{id}")
    public ResponseEntity<CategoryDto> updateCategory(
            @PathVariable byte id,
            @RequestBody String name
    ) {
        UpdateCategoryRequest request = UpdateCategoryRequest
                .builder()
                .id(id)
                .name(name)
                .build();

        CategoryDto dto = this.updateCategoryUseCase.execute(request);
        return ResponseEntity.status(HttpStatus.OK).body(dto);
    }

    @DeleteMapping("/category/{id}")
    public ResponseEntity<Boolean> deleteCategory(@PathVariable byte id) {
        Boolean isDeleted = this.deleteCategoryUseCase.execute(id);
        return ResponseEntity.status(HttpStatus.OK).body(isDeleted);
    }
}
