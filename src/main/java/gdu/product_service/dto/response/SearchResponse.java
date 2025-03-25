package gdu.product_service.dto.response;

import gdu.product_service.dto.model.ImagesDto;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
public class SearchResponse {
    private short id;
    private String name;
    private String description;
    private BigDecimal price;
    private List<ImagesDto> images;
}
