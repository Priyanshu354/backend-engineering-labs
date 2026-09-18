package com.priyanshu.orderAndInventoryManagement.OrderAndInventoryManagement.dto.cart;

import jakarta.validation.constraints.NotNull;

public record CartDeleteRequest(
        @NotNull
        Long productId
) {
}
