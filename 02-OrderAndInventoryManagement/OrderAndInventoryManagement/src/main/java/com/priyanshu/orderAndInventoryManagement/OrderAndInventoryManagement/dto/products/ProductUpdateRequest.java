package com.priyanshu.orderAndInventoryManagement.OrderAndInventoryManagement.dto.products;

import java.math.BigDecimal;
public record ProductUpdateRequest(
        String name,
        String description,
        BigDecimal price
) {
}
