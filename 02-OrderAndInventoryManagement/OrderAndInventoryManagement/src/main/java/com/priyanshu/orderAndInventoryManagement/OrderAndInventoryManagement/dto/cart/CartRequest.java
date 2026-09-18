package com.priyanshu.orderAndInventoryManagement.OrderAndInventoryManagement.dto.cart;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record CartRequest(
        @NotNull
        Long productId,
        @NotNull @Min(value = 1)
        Integer quantity
) {
}
