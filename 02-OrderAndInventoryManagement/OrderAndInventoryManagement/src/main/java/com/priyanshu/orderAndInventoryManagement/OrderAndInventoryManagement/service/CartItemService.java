package com.priyanshu.orderAndInventoryManagement.OrderAndInventoryManagement.service;

import com.priyanshu.orderAndInventoryManagement.OrderAndInventoryManagement.dto.cart.CartResponse;
import com.priyanshu.orderAndInventoryManagement.OrderAndInventoryManagement.entity.Cart;
import com.priyanshu.orderAndInventoryManagement.OrderAndInventoryManagement.entity.CartItem;
import com.priyanshu.orderAndInventoryManagement.OrderAndInventoryManagement.entity.Product;
import com.priyanshu.orderAndInventoryManagement.OrderAndInventoryManagement.errors.exception.ResourceNotFoundException;
import com.priyanshu.orderAndInventoryManagement.OrderAndInventoryManagement.mapper.CartMapper;
import com.priyanshu.orderAndInventoryManagement.OrderAndInventoryManagement.repo.CartItemRepo;
import com.priyanshu.orderAndInventoryManagement.OrderAndInventoryManagement.repo.CartRepo;
import com.priyanshu.orderAndInventoryManagement.OrderAndInventoryManagement.repo.ProductRepo;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    @Transactional
    public void saveCart(Long guestCartId, Long userCartId) {

        List<CartItem> guestItems =
                cartItemRepo.findAllByCartId(guestCartId);

        List<CartItem> userItems =
                cartItemRepo.findAllByCartId(userCartId);

        Map<Long, Integer> userItemsMap = new HashMap<>();

        for (int i=0;i<userItems.size();i++) {
            Long productId = userItems.get(i).getProduct().getId();
            userItemsMap.put(productId, i);
        }

        List<CartItem> itemsToDelete = new ArrayList<>();

        guestItems.forEach(item -> {
            Long productId = item.getProduct().getId();

            if (userItemsMap.containsKey(productId)) {

                Integer indexOfUserItem = userItemsMap.get(productId);
                CartItem userItem = userItems.get(indexOfUserItem);
                userItem.setQuantity(
                        userItem.getQuantity() + item.getQuantity()
                );

                // Guest item is no longer needed
                itemsToDelete.add(item);

            } else {

                // Move guest item to user's cart
                Cart userCart = cartRepo.getReferenceById(userCartId);
                item.setCart(userCart);

                userItems.add(item);
            }
        });

        cartItemRepo.saveAll(userItems);
        cartItemRepo.deleteAll(itemsToDelete);
    }
}
