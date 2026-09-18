package com.priyanshu.orderAndInventoryManagement.OrderAndInventoryManagement.dto.order;

import java.time.Instant;

public record OrderResponse(
        Long orderId,
        Instant placedDate,
        Double totalAmount
) {
}
