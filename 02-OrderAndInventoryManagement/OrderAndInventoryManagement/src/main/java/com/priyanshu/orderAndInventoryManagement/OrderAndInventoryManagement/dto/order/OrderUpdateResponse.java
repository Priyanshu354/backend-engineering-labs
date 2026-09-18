package com.priyanshu.orderAndInventoryManagement.OrderAndInventoryManagement.dto.order;

import com.priyanshu.orderAndInventoryManagement.OrderAndInventoryManagement.enums.OrderStatus;

public record OrderUpdateResponse(
        Long orderId,
        OrderStatus orderStatus
) {
}
