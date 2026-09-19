package com.priyanshu.orderAndInventoryManagement.OrderAndInventoryManagement.dto.inventory;

import jakarta.validation.constraints.NotNull;

public record InventoryRequest(
        @NotNull
        Long productId,

        @NotNull
        Integer quantity
) {
}
