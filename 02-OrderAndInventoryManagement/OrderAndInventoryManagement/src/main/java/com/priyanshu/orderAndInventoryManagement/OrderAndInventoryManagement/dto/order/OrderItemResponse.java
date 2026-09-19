package com.priyanshu.orderAndInventoryManagement.OrderAndInventoryManagement.dto.order;

import java.math.BigDecimal;

public record OrderItemResponse(
        Long productId,
        String productName,
        BigDecimal productPrice,
        Integer quantity
) {
}
