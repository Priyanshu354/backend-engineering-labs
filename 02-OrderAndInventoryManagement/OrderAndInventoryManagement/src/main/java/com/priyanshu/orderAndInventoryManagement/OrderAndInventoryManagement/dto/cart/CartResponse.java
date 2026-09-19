package com.priyanshu.orderAndInventoryManagement.OrderAndInventoryManagement.dto.cart;

import java.math.BigDecimal;

public record CartResponse(
        Long productId,
        String productName,
        BigDecimal productPrice,
        Integer quantity
) {
}
