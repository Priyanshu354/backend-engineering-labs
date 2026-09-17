package com.priyanshu.orderAndInventoryManagement.OrderAndInventoryManagement.dto.products;

public record ProductResponse(
        Long id,
        String name,
        double price,
        String description

) {
}
