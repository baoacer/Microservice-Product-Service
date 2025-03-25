package gdu.product_service.dto.response;

import gdu.product_service.dto.model.ImagesDto;
import gdu.product_service.dto.model.InventoryDto;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
public class UpdateProductResponse {
    private short id;
    private String name;
    private String description;
    private BigDecimal price;
    private byte categoryId;
    private short stock;
    private List<ImagesDto> images;
}
