package com.priyanshu.orderAndInventoryManagement.OrderAndInventoryManagement.dto.inventory;

public record InventoryResponse(
        Long id,
        String name,
        double price,
        String description,
        int quantity
) {
}
