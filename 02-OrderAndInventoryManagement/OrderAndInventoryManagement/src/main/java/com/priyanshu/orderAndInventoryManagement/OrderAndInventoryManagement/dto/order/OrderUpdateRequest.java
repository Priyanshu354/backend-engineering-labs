package com.priyanshu.orderAndInventoryManagement.OrderAndInventoryManagement.dto.order;

import com.priyanshu.orderAndInventoryManagement.OrderAndInventoryManagement.enums.OrderStatus;

public record OrderUpdateRequest(
        Long orderItemId,
        OrderStatus orderStatus
) {
}
