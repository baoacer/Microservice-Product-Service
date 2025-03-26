package gdu.product_service.dto.response;

import lombok.Data;

import java.util.List;

@Data
public class ObjectResponse<T> {
    private List<T> content;
    private int totalPages;
    private long totalElements;
    private int size;
    private int number;
}
