package gdu.product_service.entity;

import gdu.product_service.utils.InventoryStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import lombok.Data;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;

@Entity
@Table(name = "inventorys")
@Data
public class InventoryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "SMALLINT UNSIGNED")
    private short id;

    @Min(0)
    @Column(nullable = false, columnDefinition = "SMALLINT UNSIGNED")
    private short stock;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private InventoryStatus status = InventoryStatus.IN_STOCK;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "product_id")
    private ProductEntity product;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDate updatedAt;
}
