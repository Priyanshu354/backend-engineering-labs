package com.priyanshu.orderAndInventoryManagement.OrderAndInventoryManagement.repo;

import com.priyanshu.orderAndInventoryManagement.OrderAndInventoryManagement.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface OrderRepo extends JpaRepository<Order , Long> {
    List<Order> findAllByUserId(Long userId);

    Optional<Order> findByUserIdAndOrderId(Long userId, Long orderId);
}
