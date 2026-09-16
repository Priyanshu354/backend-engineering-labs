package com.priyanshu.orderAndInventoryManagement.OrderAndInventoryManagement.errors.exception;


import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BadRequestException extends RuntimeException {
    String message;

    public BadRequestException(String message){
        this.message = message;
    }

}
