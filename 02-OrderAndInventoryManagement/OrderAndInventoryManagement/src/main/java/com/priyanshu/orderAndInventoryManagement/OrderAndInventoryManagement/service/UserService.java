package com.priyanshu.orderAndInventoryManagement.OrderAndInventoryManagement.service;

import com.priyanshu.orderAndInventoryManagement.OrderAndInventoryManagement.dto.users.LoginRequest;
import com.priyanshu.orderAndInventoryManagement.OrderAndInventoryManagement.dto.users.LoginResponse;
import com.priyanshu.orderAndInventoryManagement.OrderAndInventoryManagement.dto.users.SignUpRequest;
import com.priyanshu.orderAndInventoryManagement.OrderAndInventoryManagement.entity.User;
import com.priyanshu.orderAndInventoryManagement.OrderAndInventoryManagement.enums.Role;
import com.priyanshu.orderAndInventoryManagement.OrderAndInventoryManagement.errors.exception.BadRequestException;
import com.priyanshu.orderAndInventoryManagement.OrderAndInventoryManagement.mapper.LoginOrSignupResponseMapper;
import com.priyanshu.orderAndInventoryManagement.OrderAndInventoryManagement.repo.UserRepo;
import com.priyanshu.orderAndInventoryManagement.OrderAndInventoryManagement.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;

    private final LoginOrSignupResponseMapper loginOrSignupResponseMapper;
    private final AuthenticationManager authenticationManager;

    private final JwtUtil jwtUtil;

    public LoginResponse login(LoginRequest loginRequest) {
        // User user = userRepo.findByEmail(loginRequest.email()).orElseThrow(() -> new BadRequestException("User doesn't exist."));

        Authentication authentication =
                authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(
                                loginRequest.email(),
                                loginRequest.password()
                        )
                );

        User user = (User) authentication.getPrincipal();

        String accessToken = jwtUtil.generateAccessToken(user);

        log.info("user id : {} is loggedIn", user.getId());

        return loginOrSignupResponseMapper.LoginORSignupRequestToLoginResponse(user,accessToken);
    }

    public LoginResponse signup(SignUpRequest signUpRequest) {
        userRepo.findByEmail(signUpRequest.email())
                .ifPresent(user -> {
                    throw new BadRequestException("User has already signed up.");
                });

        String password = signUpRequest.password();
        String hashedPassword = passwordEncoder.encode(password);

        User user = new User();
        user.setEmail(signUpRequest.email());
        user.setPassword(hashedPassword);
        user.setName(signUpRequest.name());
        user.setRole(Role.ROLE_USER);

        userRepo.save(user);
        String accessToken = jwtUtil.generateAccessToken(user);

        log.info("user email : {} is signedUp", user.getEmail());

        return loginOrSignupResponseMapper.LoginORSignupRequestToLoginResponse(user, accessToken);
    }
}
