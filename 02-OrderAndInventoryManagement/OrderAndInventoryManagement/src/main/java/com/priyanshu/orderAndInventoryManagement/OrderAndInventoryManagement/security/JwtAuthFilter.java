package com.priyanshu.orderAndInventoryManagement.OrderAndInventoryManagement.security;

import com.priyanshu.orderAndInventoryManagement.OrderAndInventoryManagement.enums.Role;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.io.IOException;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final HandlerExceptionResolver handlerExceptionResolver;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        try {
            Cookie[] cookies = request.getCookies();

            String accessToken = null;


            if (cookies != null) {
                for (Cookie cookie : cookies) {
                    if (cookie.getName().equals("accessToken")) {
                        accessToken = cookie.getValue();
                    }
                }
            }

            if (accessToken == null) {
                filterChain.doFilter(request, response);
                return;
            }

            log.info("access Token, {} ", accessToken);

            Claims userClaims = jwtUtil.verifyToken(accessToken);
            String roleName = userClaims.get("role", String.class);

            Role role = Role.valueOf(roleName);

            log.info("JWT ROLE = {}", roleName);
            log.info("SPRING AUTHORITIES = {}", role.getAuthorities(role));

            if (userClaims != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                Authentication authentication = new UsernamePasswordAuthenticationToken(
                        userClaims, // Principle
                        null,
                        role.getAuthorities(role) //List<SimpleGrantedAuthority
                );

                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
            log.info("AUTH = {}",
                    SecurityContextHolder.getContext().getAuthentication());

            log.info("BEFORE filterChain");

            filterChain.doFilter(request, response);

            log.info("AFTER filterChain");
        }
        catch (Exception ex){
            handlerExceptionResolver.resolveException(request, response, null, ex);
        }
    }
}
