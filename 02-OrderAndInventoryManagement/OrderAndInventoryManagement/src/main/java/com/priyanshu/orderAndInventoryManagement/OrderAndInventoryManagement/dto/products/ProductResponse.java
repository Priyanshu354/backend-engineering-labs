package com.priyanshu.orderAndInventoryManagement.OrderAndInventoryManagement.dto.products;

import java.math.BigDecimal;

public record ProductResponse(
        Long id,
        String name,
        BigDecimal price,
        String description

) {
}
