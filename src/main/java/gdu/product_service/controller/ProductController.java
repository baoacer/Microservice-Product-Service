package gdu.product_service.controller;

import gdu.product_service.dto.model.ProductDto;
import gdu.product_service.dto.request.product.CreateProductRequest;
import gdu.product_service.dto.request.product.GetAllProductRequest;
import gdu.product_service.dto.request.product.SearchProductRequest;
import gdu.product_service.dto.request.product.UpdateProductRequest;
import gdu.product_service.dto.response.GetProductResponse;
import gdu.product_service.dto.response.SearchResponse;
import gdu.product_service.dto.response.UpdateProductResponse;
import gdu.product_service.usecase.product.*;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
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

    @GetMapping("/product")
    public ResponseEntity<Page<GetProductResponse>> getProduct(@RequestParam byte size, @RequestParam byte page) {
        GetAllProductRequest request = GetAllProductRequest
                .builder()
                .size(size)
                .page(page)
                .build();

        Page<GetProductResponse> response = this.getAllProductUseCase.execute(request);
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

    @DeleteMapping("/product/{id}")
    public ResponseEntity<Boolean> deleteProduct(@PathVariable short id) {
        Boolean isDelete = this.deleteProductUseCase.execute(id);
        return ResponseEntity.status(HttpStatus.OK).body(isDelete);
    }

    @PutMapping("/product")
    public ResponseEntity<UpdateProductResponse> updateProduct(@RequestBody UpdateProductRequest request) {
        UpdateProductResponse updateProduct = this.updateProductUseCase.execute(request);
        return ResponseEntity.status(HttpStatus.OK).body(updateProduct);
    }


}
