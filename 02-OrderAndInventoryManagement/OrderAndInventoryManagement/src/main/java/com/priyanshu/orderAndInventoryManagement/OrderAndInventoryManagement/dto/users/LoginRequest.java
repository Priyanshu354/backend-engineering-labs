package com.priyanshu.orderAndInventoryManagement.OrderAndInventoryManagement.dto.users;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.NonNull;

public record LoginRequest(

        @Email(message = "email should be valid")
        @NotBlank(message = "email can't be blank")
        String email,

        @NotBlank(message = "password can't be blank")
        String password
) {
}
