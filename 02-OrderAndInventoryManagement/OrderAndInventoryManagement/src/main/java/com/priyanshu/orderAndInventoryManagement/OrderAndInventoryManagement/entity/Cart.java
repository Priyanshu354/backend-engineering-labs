package com.priyanshu.orderAndInventoryManagement.OrderAndInventoryManagement.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Cart {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @OneToOne(fetch = FetchType.LAZY) @JoinColumn(name = "user_id")
    User user;

    @CreationTimestamp
    Instant created_at;

    @UpdateTimestamp
    Instant updated_at;

}
