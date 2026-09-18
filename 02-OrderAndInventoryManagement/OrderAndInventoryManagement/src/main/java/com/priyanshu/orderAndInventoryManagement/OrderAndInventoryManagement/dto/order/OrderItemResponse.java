package com.priyanshu.orderAndInventoryManagement.OrderAndInventoryManagement.dto.order;

public record OrderItemResponse(
        Long productId,
        String productName,
        Double price,
        Integer quantity
) {
}
