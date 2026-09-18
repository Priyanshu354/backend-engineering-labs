package com.priyanshu.orderAndInventoryManagement.OrderAndInventoryManagement.service;

import com.priyanshu.orderAndInventoryManagement.OrderAndInventoryManagement.dto.cart.CartResponse;
import com.priyanshu.orderAndInventoryManagement.OrderAndInventoryManagement.entity.Cart;
import com.priyanshu.orderAndInventoryManagement.OrderAndInventoryManagement.entity.CartItem;
import com.priyanshu.orderAndInventoryManagement.OrderAndInventoryManagement.entity.Product;
import com.priyanshu.orderAndInventoryManagement.OrderAndInventoryManagement.entity.User;
import com.priyanshu.orderAndInventoryManagement.OrderAndInventoryManagement.errors.exception.ResourceNotFoundException;
import com.priyanshu.orderAndInventoryManagement.OrderAndInventoryManagement.mapper.CartMapper;
import com.priyanshu.orderAndInventoryManagement.OrderAndInventoryManagement.repo.CartItemRepo;
import com.priyanshu.orderAndInventoryManagement.OrderAndInventoryManagement.repo.CartRepo;
import com.priyanshu.orderAndInventoryManagement.OrderAndInventoryManagement.repo.ProductRepo;
import com.priyanshu.orderAndInventoryManagement.OrderAndInventoryManagement.repo.UserRepo;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class CartItemService {
    private final CartItemRepo cartItemRepo;
    private final CartMapper cartMapper;
    private final CartRepo cartRepo;
    private final ProductRepo productRepo;

    public List<CartResponse> getCartItems(Long cartId){
        List<CartItem> cartItems = cartItemRepo.findAllByCartId(cartId);
        return cartMapper.CartItemToCartResponse(cartItems);
    }

    public void addCartItem(Long cartId, Long productId, Integer quantity){
        CartItem cartItem = new CartItem();

        Cart cart = cartRepo.getReferenceById(cartId);
        Product product = productRepo.getReferenceById(productId);

        cartItem.setCart(cart);
        cartItem.setProduct(product);
        cartItem.setQuantity(quantity);
        cartItemRepo.save(cartItem);
    }


    public void manageCart(Long cartId, Long productId, Integer value) {
        CartItem cartItem = cartItemRepo.findByCartIdAndProductId(cartId, productId).orElseThrow(() -> new ResourceNotFoundException("Cart Item", productId));

        cartItem.setQuantity(cartItem.getQuantity() + value);
        if(cartItem.getQuantity() == 0){
            cartItemRepo.delete(cartItem);
        }
    }

    public void removeCartItem(Long cartId, Long productId) {
        CartItem cartItem = cartItemRepo.findByCartIdAndProductId(cartId, productId).orElseThrow(() -> new ResourceNotFoundException("Cart Item", productId));

        cartItemRepo.delete(cartItem);
    }

    public void saveCart(Long guestCartId, Long userCartId) {
        List<CartItem> guestCartList = cartItemRepo.findAllByCartId(guestCartId);
        List<CartItem> userCartList = cartItemRepo.findAllByCartId(userCartId);

        userCartList.addAll(guestCartList);
        cartItemRepo.saveAll(userCartList);
        cartItemRepo.deleteAll(guestCartList);
    }
}
