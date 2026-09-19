package com.priyanshu.orderAndInventoryManagement.OrderAndInventoryManagement.controllers;

import com.priyanshu.orderAndInventoryManagement.OrderAndInventoryManagement.dto.cart.*;
import com.priyanshu.orderAndInventoryManagement.OrderAndInventoryManagement.security.JwtUtil;
import com.priyanshu.orderAndInventoryManagement.OrderAndInventoryManagement.service.CartService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cart")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    @GetMapping
    public ResponseEntity<List<CartResponse>> getCart(@RequestParam(required = false) Long guestId){
        Long userId = JwtUtil.getUserId();
        Long id = userId != null ? userId : guestId;

        return ResponseEntity.ok(cartService.getCart(id));
    }

    @PostMapping
    public ResponseEntity<CartMessage> addToCart(@RequestParam(required = false) Long guestId,
                                                 @RequestBody CartRequest cartRequest){
        Long userId = JwtUtil.getUserId();
        Long id = userId != null ? userId : guestId;

        return ResponseEntity.status(HttpStatus.CREATED).body(cartService.addToCart(cartRequest, id));
    }

    @PatchMapping
    public ResponseEntity<CartMessage> manageCart(@RequestParam(required = false) Long guestId,
                                                     @RequestBody CartMangeRequest cartMangeRequest){
        Long userId = JwtUtil.getUserId();
        Long id = userId != null ? userId : guestId;

        return ResponseEntity.ok(cartService.manageCart(cartMangeRequest,id));
    }

    @DeleteMapping
    public ResponseEntity<CartMessage> removeToCart(@RequestParam(required = false) Long guestId,
                                                    @Valid @RequestBody CartDeleteRequest cartDeleteRequest){
        Long userId = JwtUtil.getUserId();
        Long id = userId != null ? userId : guestId;

        return ResponseEntity.ok(cartService.removeToCart(cartDeleteRequest,id));
    }

    @PostMapping("/save")
    public ResponseEntity<CartMessage> saveCart(@RequestParam(required = true) Long guestId){
        Long userId = JwtUtil.getUserId();
        return ResponseEntity.ok(cartService.saveCart(userId, guestId));
    }


}
