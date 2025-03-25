package gdu.product_service.dto.request.category;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UpdateCategoryRequest {
    private byte id;
    private String name;
}
