package gdu.product_service.dto.request.product;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.domain.Sort;

@Data
@Builder
public class GetAllProductRequest {
    private byte size;
    private byte page;
    private String sortBy = "id";
    private Sort.Direction sortDirection = Sort.Direction.ASC;
}
