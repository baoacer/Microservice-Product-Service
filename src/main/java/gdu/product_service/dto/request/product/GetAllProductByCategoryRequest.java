package gdu.product_service.dto.request.product;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.domain.Sort;

@Data
@Builder
public class GetAllProductByCategoryRequest {
    private Byte categoryId;
    private byte page;
    private byte size;
    private String sortBy = "id";
    private Sort.Direction sortDirection = Sort.Direction.ASC;
}
