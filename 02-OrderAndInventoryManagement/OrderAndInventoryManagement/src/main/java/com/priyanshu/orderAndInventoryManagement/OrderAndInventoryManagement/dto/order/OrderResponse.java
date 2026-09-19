package com.priyanshu.orderAndInventoryManagement.OrderAndInventoryManagement.dto.order;

import com.priyanshu.orderAndInventoryManagement.OrderAndInventoryManagement.enums.OrderStatus;

import java.math.BigDecimal;
import java.time.Instant;

public record OrderResponse(
        Long id,
        Instant placedDate,
        BigDecimal totalAmount,
        OrderStatus orderStatus
) {
}
