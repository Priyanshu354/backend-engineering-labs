package com.priyanshu.orderAndInventoryManagement.OrderAndInventoryManagement.errors;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.security.SignatureException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.framework.AopConfigException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.priyanshu.orderAndInventoryManagement.OrderAndInventoryManagement.errors.exception.BadRequestException;
import com.priyanshu.orderAndInventoryManagement.OrderAndInventoryManagement.errors.exception.ResourceNotFoundException;

import org.springframework.security.core.AuthenticationException;
import java.util.List;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ApiError> handleBadRequestException(BadRequestException badRequestException){
        ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, badRequestException.getMessage());
        log.error(apiError.toString(), badRequestException);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiError);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiError> handleResourceNotFound(ResourceNotFoundException ex){
        ApiError apiError=new ApiError(HttpStatus.NOT_FOUND, ex.getResourceName() + "with id : " + ex.getResourceId() + "Not Found");
        log.error(apiError.toString(), apiError);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(apiError);
    }

    @ExceptionHandler(ExpiredJwtException.class)
    public ResponseEntity<ApiError> handleExpiredJwt(ExpiredJwtException ex) {
        ApiError error = new ApiError(HttpStatus.UNAUTHORIZED, "JWT token has expired");
        log.error(error.toString(), ex);
        return ResponseEntity.status(error.status()).body(error);
    }

    @ExceptionHandler(MalformedJwtException.class)
    public ResponseEntity<ApiError> handleMalformedJwt(MalformedJwtException ex) {
        ApiError error = new ApiError(HttpStatus.UNAUTHORIZED, "Invalid JWT token");
        log.error(error.toString(), ex);
        return ResponseEntity.status(error.status()).body(error);
    }

    @ExceptionHandler(SignatureException.class)
    public ResponseEntity<ApiError> handleSignatureJwt(SignatureException ex) {
        ApiError error = new ApiError(HttpStatus.UNAUTHORIZED, "JWT signature validation failed");
        log.error(error.toString(), ex);
        return ResponseEntity.status(error.status()).body(error);
    }

    @ExceptionHandler(JwtException.class)
    public ResponseEntity<ApiError> handleGenericJwt(JwtException ex) {
        ApiError error = new ApiError(HttpStatus.UNAUTHORIZED, "JWT processing error");
        log.error(error.toString(), ex);
        return ResponseEntity.status(error.status()).body(error);
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ApiError> handleAuthenticationException(AuthenticationException ex) {
        ApiError error = new ApiError(HttpStatus.UNAUTHORIZED, "Authentication failed");
        log.error(error.toString(), ex);
        return ResponseEntity.status(error.status()).body(error);
    }

    @ExceptionHandler(ObjectOptimisticLockingFailureException.class)
    public ResponseEntity<?> handleOptimisticLock(
            ObjectOptimisticLockingFailureException ex) {

        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body("Product was modified by another request. Please retry.");
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiError> handleInputValidationError(MethodArgumentNotValidException ex) {
        List<ApiFieldError> errors = ex.getBindingResult().getFieldErrors().stream()
                .map(error -> new ApiFieldError(error.getField(), error.getDefaultMessage()))
                .toList();

        ApiError error = new ApiError(HttpStatus.BAD_REQUEST, "Input validation failed", errors);
        log.error(error.toString(), ex);
        return ResponseEntity.status(error.status()).body(error);
    }


}
