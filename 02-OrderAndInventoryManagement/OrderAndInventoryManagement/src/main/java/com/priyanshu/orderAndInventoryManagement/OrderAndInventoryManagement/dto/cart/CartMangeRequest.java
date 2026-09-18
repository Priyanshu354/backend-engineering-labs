package com.priyanshu.orderAndInventoryManagement.OrderAndInventoryManagement.dto.cart;


import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotNull;

public record CartMangeRequest(
        @NotNull
        Long productId,

        @NotNull
        Integer value
) {
    @AssertTrue(message = "value must be either 1 or -1")
    public boolean isValidValue() {
        return value != null && (value == 1 || value == -1);
    }
}
