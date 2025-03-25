package gdu.product_service.repository;

import gdu.product_service.entity.ImagesEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ImageRepository extends JpaRepository<ImagesEntity, Integer> {
    @Query("SELECT MAX(i.position) from images i where i.product.id = :productId")
    Byte getMaxPosition(short productId);
}
