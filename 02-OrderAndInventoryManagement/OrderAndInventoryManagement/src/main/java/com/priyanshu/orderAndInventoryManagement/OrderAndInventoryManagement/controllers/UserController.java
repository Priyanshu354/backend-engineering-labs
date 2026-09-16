package com.priyanshu.orderAndInventoryManagement.OrderAndInventoryManagement.controllers;

import com.priyanshu.orderAndInventoryManagement.OrderAndInventoryManagement.dto.users.LoginRequest;
import com.priyanshu.orderAndInventoryManagement.OrderAndInventoryManagement.dto.users.LoginResponse;
import com.priyanshu.orderAndInventoryManagement.OrderAndInventoryManagement.dto.users.SignUpRequest;
import com.priyanshu.orderAndInventoryManagement.OrderAndInventoryManagement.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Duration;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final UserService userService;

    @PostMapping("/login")
    ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest){
        log.info("login request for user {}", loginRequest.email());
        LoginResponse loginResponse = userService.login(loginRequest);

        String accessToken = loginResponse.accessToken();
        ResponseCookie cookie = ResponseCookie.from("accessToken", accessToken)
                .httpOnly(true)
                .secure(false)
                .sameSite("Strict")
                .path("/")
                .maxAge(Duration.ofHours(1))
                .build();

        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, cookie.toString()).body(loginResponse);
    }

    @PostMapping("/signup")
    ResponseEntity<LoginResponse> singup(@RequestBody SignUpRequest signUpRequest){
        log.info("signup request for user {}", signUpRequest.email());

        LoginResponse loginResponse = userService.signup(signUpRequest);

        String accessToken = loginResponse.accessToken();
        ResponseCookie cookie = ResponseCookie.from("accessToken", accessToken)
                .httpOnly(true)
                .secure(false)
                .sameSite("Strict")
                .path("/")
                .maxAge(Duration.ofHours(1))
                .build();

        return ResponseEntity.status(HttpStatus.CREATED).header(HttpHeaders.SET_COOKIE, cookie.toString()).body(loginResponse);
    }
}
