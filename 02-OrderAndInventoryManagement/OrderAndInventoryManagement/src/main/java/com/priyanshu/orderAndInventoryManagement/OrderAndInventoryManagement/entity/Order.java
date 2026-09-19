package com.priyanshu.orderAndInventoryManagement.OrderAndInventoryManagement.entity;

import com.priyanshu.orderAndInventoryManagement.OrderAndInventoryManagement.enums.OrderStatus;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.Instant;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "orders")
public class Order {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name="user_id")
    User user;

    @Enumerated(EnumType.STRING)
    OrderStatus status;

    @Column(nullable = false)
    String shippingAddress;

    @Column(nullable = false)
    BigDecimal totalAmount;

    @CreationTimestamp
    Instant created_at;

    @UpdateTimestamp
    Instant updated_at;

}
