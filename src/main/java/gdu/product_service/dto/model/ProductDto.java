package gdu.product_service.dto.model;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
public class ProductDto {
    private short id;
    private String name;
    private String description;
    private BigDecimal price;
    private String category;
    private InventoryDto inventory;
    private List<ImagesDto> images;
}
