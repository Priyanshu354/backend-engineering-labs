package com.priyanshu.orderAndInventoryManagement.OrderAndInventoryManagement.service;

import com.priyanshu.orderAndInventoryManagement.OrderAndInventoryManagement.dto.cart.*;
import com.priyanshu.orderAndInventoryManagement.OrderAndInventoryManagement.entity.Cart;
import com.priyanshu.orderAndInventoryManagement.OrderAndInventoryManagement.entity.User;
import com.priyanshu.orderAndInventoryManagement.OrderAndInventoryManagement.errors.exception.BadRequestException;
import com.priyanshu.orderAndInventoryManagement.OrderAndInventoryManagement.errors.exception.ResourceNotFoundException;
import com.priyanshu.orderAndInventoryManagement.OrderAndInventoryManagement.mapper.CartMapper;
import com.priyanshu.orderAndInventoryManagement.OrderAndInventoryManagement.repo.CartRepo;
import com.priyanshu.orderAndInventoryManagement.OrderAndInventoryManagement.repo.UserRepo;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.Nullable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CartService {

    private final CartRepo cartRepo;
    private final CartItemService cartItemService;
    private final UserRepo userRepo;
    private final CartMapper cartMapper;

    public List<CartResponse> getCart(Long userId) {
        Cart cart = cartRepo.findByUserId(userId);
        if(cart == null){
            return new ArrayList<>();
        }
        return cartItemService.getCartItems(cart.getId());
    }

    @Transactional
    public CartMessage addToCart(CartRequest cartRequest, Long userId) {
        Cart cart = cartRepo.findByUserId(userId);

        if(cart == null){
            cart = new Cart();
            // User user = userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", userId));
            User user = userRepo.getReferenceById(userId);
            cart.setUser(user);
            cartRepo.save(cart);
        }

        cartItemService.addCartItem(cart.getId(), cartRequest.productId(), cartRequest.quantity());
        return  cartMapper.toCartMessage("Cart Added Successful");
    }

    public CartMessage manageCart(CartMangeRequest cartMangeRequest, Long userId) {
        Cart cart = cartRepo.findByUserId(userId);

        if(cart == null){
            throw new BadRequestException("cart doesn't exist");
        }

        cartItemService.manageCart(cart.getId(), cartMangeRequest.productId(), cartMangeRequest.value());
        return cartMapper.toCartMessage("Cart quantity value manage Successful");
    }

    public CartMessage saveCart(Long userId, Long guestId) {
        Cart guestCart = cartRepo.findByUserId(guestId);

        if(guestCart == null){
            throw new BadRequestException("cart doesn't exist");
        }

        Cart userCart = cartRepo.findByUserId(userId);

        if(userCart == null){
            User user = userRepo.getReferenceById(userId);
            guestCart.setUser(user);
            cartRepo.save(guestCart);
        }else{
            cartItemService.saveCart(guestCart.getId(),userCart.getId());
        }

        return cartMapper.toCartMessage("Cart save to user");
    }

    public CartMessage removeToCart(CartDeleteRequest cartDeleteRequest, Long userId) {
        Cart cart = cartRepo.findByUserId(userId);

        if(cart == null){
            throw new BadRequestException("cart doesn't exist");
        }

        cartItemService.removeCartItem(cart.getId(),cartDeleteRequest.productId());
        return cartMapper.toCartMessage("Cart Item deleted Successful");
    }
}
