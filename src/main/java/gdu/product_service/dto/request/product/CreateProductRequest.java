package gdu.product_service.dto.request.product;

import gdu.product_service.dto.model.ImagesDto;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
public class CreateProductRequest {
    @NotBlank
    private String name;

    @NotBlank
    private short quantity;

    @NotBlank
    private String description;

    @NotBlank
    private BigDecimal price;

    @NotBlank
    private byte categoryId;

    @NotBlank
    private List<ImagesDto> images;
}
