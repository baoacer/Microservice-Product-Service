package gdu.product_service.dto.model;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
public class CategoryDto {
    private short id;
    private String name;
    private LocalDateTime created_at;
    private LocalDateTime updated_at;
}
