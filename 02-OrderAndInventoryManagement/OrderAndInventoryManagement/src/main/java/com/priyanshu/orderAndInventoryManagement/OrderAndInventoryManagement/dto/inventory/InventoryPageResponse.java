package com.priyanshu.orderAndInventoryManagement.OrderAndInventoryManagement.dto.inventory;

public record InventoryPageResponse(
        Long productId,
        String name,
        String description,
        BigDecimal price,
        Integer quantity

) {
}
