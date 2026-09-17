package com.priyanshu.orderAndInventoryManagement.OrderAndInventoryManagement.repo;

import com.priyanshu.orderAndInventoryManagement.OrderAndInventoryManagement.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import org.springframework.data.domain.Pageable;
import java.util.List;

@Repository
public interface ProductRepo extends JpaRepository<Product, Long> {


    @Query("""
    SELECT p
    FROM Product p
    WHERE (:cursorKey IS NULL OR p.id > :cursorKey)
    ORDER BY p.id ASC
""")
    List<Product> findAllPaginated(
            @Param("cursorKey") Long cursorKey,
            Pageable pageable
    );
}
