package gdu.product_service.dto.model;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ImagesDto {
    private int id;

    @NotBlank
    private String src;

    private byte position;
}
