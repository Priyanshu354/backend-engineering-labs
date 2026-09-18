package com.priyanshu.orderAndInventoryManagement.OrderAndInventoryManagement.repo;

import com.priyanshu.orderAndInventoryManagement.OrderAndInventoryManagement.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartRepo extends JpaRepository<Cart, Long> {
    Cart findByUserId(Long userId);
}
