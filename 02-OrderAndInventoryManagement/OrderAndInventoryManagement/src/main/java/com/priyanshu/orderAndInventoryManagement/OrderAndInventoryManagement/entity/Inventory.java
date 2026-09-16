package com.priyanshu.orderAndInventoryManagement.OrderAndInventoryManagement.entity;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.OptimisticLock;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Inventory {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @OneToOne(fetch = FetchType.LAZY) @JoinColumn(name = "product_id", unique = true)
    Product product;

    @Column(nullable = false)
    Integer quantity;

    @Version
    Integer version;

    @CreationTimestamp
    Instant created_at;

    @UpdateTimestamp
    Instant updated_at;

}
