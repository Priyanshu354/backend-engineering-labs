package com.priyanshu.orderAndInventoryManagement.OrderAndInventoryManagement.dto.products;

public record ProductUpdateRequest(
        String name,
        String description,
        BigDecimal price
) {
}
