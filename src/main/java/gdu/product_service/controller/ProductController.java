package gdu.product_service.controller;

import gdu.product_service.dto.model.ProductDto;
import gdu.product_service.dto.request.product.*;
import gdu.product_service.dto.response.ObjectResponse;
import gdu.product_service.dto.response.SearchResponse;
import gdu.product_service.dto.response.UpdateProductResponse;
import gdu.product_service.usecase.product.*;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/api/v1")
@AllArgsConstructor
public class ProductController {

    private final CreateProductUseCase createProductUseCase;
    private final GetAllProductUseCase getAllProductUseCase;
    private final GetProductByIdUseCase getProductByIdUseCase;
    private final SearchProductUseCase searchProductUseCase;
    private final DeleteProductUseCase deleteProductUseCase;
    private final UpdateProductUseCase updateProductUseCase;
    private final GetProductByCategoryUseCase getProductByCategoryUseCase;

    @GetMapping("/product")
    public ResponseEntity<ObjectResponse<ProductDto>> getProduct(
            @RequestParam(defaultValue = "10")  byte size,
            @RequestParam(defaultValue = "0") byte page,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "DESC") Sort.Direction sortDirection
    ) {
        GetAllProductRequest request = GetAllProductRequest
                .builder()
                .size(size)
                .page(page)
                .sortBy(sortBy)
                .sortDirection(sortDirection)
                .build();

        ObjectResponse<ProductDto> response = this.getAllProductUseCase.execute(request);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/product-by")
    public ResponseEntity<ObjectResponse<ProductDto>> getProductByCategory(
            @RequestParam Byte categoryId,
            @RequestParam(defaultValue = "10")  byte size,
            @RequestParam(defaultValue = "0") byte page,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "DESC") Sort.Direction sortDirection
    ) {
        GetAllProductByCategoryRequest request = GetAllProductByCategoryRequest
                .builder()
                .categoryId(categoryId)
                .size(size)
                .page(page)
                .sortBy(sortBy)
                .sortDirection(sortDirection)
                .build();

        ObjectResponse<ProductDto> response = this.getProductByCategoryUseCase.execute(request);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/product/{id}")
    public ResponseEntity<ProductDto> getProduct(@PathVariable short id) {
        ProductDto productDto = this.getProductByIdUseCase.execute(id);
        return ResponseEntity.status(HttpStatus.OK).body(productDto);
    }

    @GetMapping("/search/product")
    public ResponseEntity<Page<SearchResponse>> searchProduct(
            @RequestParam String query,
            @RequestParam byte page,
            @RequestParam byte size
    ) {
        SearchProductRequest request = SearchProductRequest
                .builder()
                .query(query)
                .page(page)
                .size(size)
                .build();

        Page<SearchResponse> responses = this.searchProductUseCase.execute(request);
        return ResponseEntity.status(HttpStatus.OK).body(responses);
    }

    @PostMapping("/product")
    public ResponseEntity<ProductDto> createProduct(@RequestBody CreateProductRequest request) {
        ProductDto dto = this.createProductUseCase.execute(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(dto);
    }

    @DeleteMapping("/product")
    public ResponseEntity<Boolean> deleteProduct(
            @RequestParam short id
    ) {
        Boolean isDelete = this.deleteProductUseCase.execute(id);
        if(isDelete){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PutMapping("/product")
    public ResponseEntity<UpdateProductResponse> updateProduct(@RequestBody UpdateProductRequest request) {
        UpdateProductResponse updateProduct = this.updateProductUseCase.execute(request);
        return ResponseEntity.status(HttpStatus.OK).body(updateProduct);
    }


}
