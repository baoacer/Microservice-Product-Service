package gdu.product_service.dto.request.product;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SearchProductRequest {
    private String query;
    private byte size;
    private byte page;
}
