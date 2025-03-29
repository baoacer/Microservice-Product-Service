package gdu.product_service.repository;

import gdu.product_service.entity.CategoryEntity;
import gdu.product_service.entity.ProductEntity;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<ProductEntity, Short> {
    Page<ProductEntity> findAll(@NotNull Pageable pageable);

    Page<ProductEntity> findAllByCategory(CategoryEntity category, @NotNull Pageable pageable);
}
