package gdu.product_service.dto.model;

import gdu.product_service.utils.InventoryStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class InventoryDto {
    private short id;
    private short stock;
    private InventoryStatus status;
    private LocalDate updatedAt;
}
