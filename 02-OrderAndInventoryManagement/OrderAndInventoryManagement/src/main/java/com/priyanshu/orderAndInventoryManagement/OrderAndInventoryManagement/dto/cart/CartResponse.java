package com.priyanshu.orderAndInventoryManagement.OrderAndInventoryManagement.dto.cart;

public record CartResponse(
        String name,
        Double price,
        Integer quantity
) {
}
