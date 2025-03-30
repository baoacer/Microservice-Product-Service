package gdu.product_service.dto.request.product;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Sort;

@Data
@NoArgsConstructor
public class GetAllProductByCategoryRequest {
    private Byte categoryId;
    private byte page;
    private byte size;
    private String sortBy = "id";
    private Sort.Direction sortDirection = Sort.Direction.DESC;
}
