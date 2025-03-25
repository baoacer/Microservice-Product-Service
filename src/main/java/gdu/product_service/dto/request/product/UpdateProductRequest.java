package gdu.product_service.dto.request.product;

import gdu.product_service.dto.model.ImagesDto;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class UpdateProductRequest {
    private short productId;
    private String name;
    private String description;
    private byte categoryId;
    private short quantity;
    private BigDecimal price;
    private List<ImagesDto> images;
}
