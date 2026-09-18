package com.priyanshu.orderAndInventoryManagement.OrderAndInventoryManagement.mapper;

import com.priyanshu.orderAndInventoryManagement.OrderAndInventoryManagement.dto.cart.CartMessage;
import com.priyanshu.orderAndInventoryManagement.OrderAndInventoryManagement.dto.cart.CartResponse;
import com.priyanshu.orderAndInventoryManagement.OrderAndInventoryManagement.entity.CartItem;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CartMapper {
    List<CartResponse> CartItemToCartResponse(List<CartItem> cartItem);

    CartMessage toCartMessage(String message);
}
