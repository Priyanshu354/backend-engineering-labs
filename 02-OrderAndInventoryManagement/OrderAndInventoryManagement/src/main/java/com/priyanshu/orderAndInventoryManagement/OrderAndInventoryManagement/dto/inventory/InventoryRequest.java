package com.priyanshu.orderAndInventoryManagement.OrderAndInventoryManagement.dto.inventory;

import jakarta.validation.constraints.NotBlank;

public record InventoryRequest(
        @NotBlank
        Long productId,

        @NotBlank
        Integer quantity
) {
}
