package com.priyanshu.orderAndInventoryManagement.OrderAndInventoryManagement.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrderItem {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "order_id")
    Order order;

    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "product_id")
    Product product;

    @Column(nullable = false)
    String productName;

    @Column(nullable = false)
    Integer productPrice;

    @Column(nullable = false)
    Integer quantity;

    @CreationTimestamp
    Instant created_at;
}
