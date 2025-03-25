package gdu.product_service.dto.request.inventory;

import gdu.product_service.utils.InventoryStatus;
import lombok.Data;

@Data
public class CreateInventoryRequest {
    private Short productId;
    private Short quantity;
    private InventoryStatus status;
}
