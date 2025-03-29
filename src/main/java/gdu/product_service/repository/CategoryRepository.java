package gdu.product_service.repository;

import gdu.product_service.entity.CategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface CategoryRepository extends JpaRepository<CategoryEntity, Byte> {
    CategoryEntity findByName(String name);
}
