package gdu.product_service.usecase.inventory;

import gdu.product_service.dto.model.InventoryDto;
import gdu.product_service.dto.request.inventory.CreateInventoryRequest;

public interface CreateInventoryUseCase {
    InventoryDto execute(CreateInventoryRequest request);
}