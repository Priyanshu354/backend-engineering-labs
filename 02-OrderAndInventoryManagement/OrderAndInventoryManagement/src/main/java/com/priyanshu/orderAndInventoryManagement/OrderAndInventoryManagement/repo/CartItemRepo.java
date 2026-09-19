package com.priyanshu.orderAndInventoryManagement.OrderAndInventoryManagement.repo;

import com.priyanshu.orderAndInventoryManagement.OrderAndInventoryManagement.entity.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CartItemRepo extends JpaRepository<CartItem, Long> {

    Optional<CartItem> findByCartIdAndProductId(Long cartId, Long productId);

    @Query("""
            SELECT ci FROM CartItem ci
            JOIN FETCH ci.product
            WHERE ci.cart.id = :cartId
            """)
    List<CartItem> findAllByCartId(Long cartId);

    void deleteAllByCartId(Long cartId);
}
