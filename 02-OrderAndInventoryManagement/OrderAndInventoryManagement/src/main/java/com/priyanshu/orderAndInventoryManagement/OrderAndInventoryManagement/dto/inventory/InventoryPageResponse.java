package com.priyanshu.orderAndInventoryManagement.OrderAndInventoryManagement.dto.inventory;

import java.math.BigDecimal;

public record InventoryPageResponse(
        Long productId,
        String name,
        String description,
        BigDecimal price,
        Integer quantity

) {
}
