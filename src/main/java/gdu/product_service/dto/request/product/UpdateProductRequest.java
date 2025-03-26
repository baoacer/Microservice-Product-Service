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
    private Byte categoryId;
    private Short quantity;
    private BigDecimal price;
    private List<ImagesDto> images;
}
