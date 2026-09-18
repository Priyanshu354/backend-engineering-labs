package com.priyanshu.orderAndInventoryManagement.OrderAndInventoryManagement.repo;

import com.priyanshu.orderAndInventoryManagement.OrderAndInventoryManagement.entity.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface InventoryRepo extends JpaRepository<Inventory, Long> {

    Optional<Inventory> findByProductId(Long productId);

    @Query("""
        SELECT i
        FROM Inventory i
        JOIN FETCH i.product
        """)
    List<Inventory> findAllInventory();
}
