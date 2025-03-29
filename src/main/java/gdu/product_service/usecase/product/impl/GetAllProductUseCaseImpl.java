package gdu.product_service.usecase.product.impl;

import gdu.product_service.dto.model.ImagesDto;
import gdu.product_service.dto.model.InventoryDto;
import gdu.product_service.dto.model.ProductDto;
import gdu.product_service.dto.request.product.GetAllProductRequest;
import gdu.product_service.dto.response.ObjectResponse;
import gdu.product_service.entity.ProductEntity;
import gdu.product_service.repository.ProductRepository;
import gdu.product_service.usecase.product.GetAllProductUseCase;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class GetAllProductUseCaseImpl implements GetAllProductUseCase {
    private ProductRepository productRepository;

    private static final Logger logger = LoggerFactory.getLogger(GetAllProductUseCaseImpl.class);

    @Override
    public ObjectResponse<ProductDto> execute(GetAllProductRequest request) {
        try {
            Sort sort = Sort.by(request.getSortDirection(), request.getSortBy());
            PageRequest pageable = PageRequest.of(request.getPage(), request.getSize(), sort);
            Page<ProductEntity> pageResult = this.productRepository.findAll(pageable);

             List<ProductDto> content = pageResult
                     .getContent()
                     .stream()
                     .map(product -> ProductDto.builder()
                             .id(product.getId())
                             .name(product.getName())
                             .price(product.getPrice())
                             .description(product.getDescription())
                             .inventory(InventoryDto
                                     .builder()
                                     .id(product.getInventory().getId())
                                     .stock(product.getInventory().getStock())
                                     .status(product.getInventory().getStatus())
                                     .updatedAt(product.getInventory().getUpdatedAt())
                                     .build())
                             .images(product.getImages().stream()
                                     .map(image -> ImagesDto.builder()
                                             .id(image.getId())
                                             .src(image.getSrc())
                                             .position(image.getPosition())
                                             .build())
                                     .toList()
                             )
                             .build()).toList();

             ObjectResponse<ProductDto> response = new ObjectResponse<>();
             response.setContent(content);
             response.setTotalPages(pageResult.getTotalPages());
             response.setTotalElements(pageResult.getTotalElements());
             response.setSize(pageResult.getSize());
             response.setNumber(pageResult.getNumber());

             return response;

        }catch (RuntimeException e) {
            logger.error("GetAllProductUseCase execute error {}", e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }
}
