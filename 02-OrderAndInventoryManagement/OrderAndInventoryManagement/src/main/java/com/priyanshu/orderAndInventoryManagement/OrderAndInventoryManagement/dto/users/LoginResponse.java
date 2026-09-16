package com.priyanshu.orderAndInventoryManagement.OrderAndInventoryManagement.dto.users;

public record LoginResponse(
        String name,
        String email,
        String accessToken
) {
}
