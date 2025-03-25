package gdu.product_service.repository;

import gdu.product_service.entity.InventoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InventoryRepository extends JpaRepository<InventoryEntity, Short> {
    InventoryEntity findByProduct_Id(short product_id);
}
