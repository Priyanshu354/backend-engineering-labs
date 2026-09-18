package com.priyanshu.orderAndInventoryManagement.OrderAndInventoryManagement.controllers;

import com.priyanshu.orderAndInventoryManagement.OrderAndInventoryManagement.dto.products.ProductPaginatedResponse;
import com.priyanshu.orderAndInventoryManagement.OrderAndInventoryManagement.dto.products.ProductCreateRequest;
import com.priyanshu.orderAndInventoryManagement.OrderAndInventoryManagement.dto.products.ProductResponse;
import com.priyanshu.orderAndInventoryManagement.OrderAndInventoryManagement.dto.products.ProductUpdateRequest;
import com.priyanshu.orderAndInventoryManagement.OrderAndInventoryManagement.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api")
@RequiredArgsConstructor
@Slf4j
@RestController
public class ProductController {

    private final ProductService productService;

    @GetMapping("/products/{id}")
    public ResponseEntity<ProductResponse> getProductById(@PathVariable Long id){
        return ResponseEntity.ok(productService.getProductById(id));
    }

    @GetMapping("/products")
    public ResponseEntity<ProductPaginatedResponse> getProducts(@RequestParam(required = false) Long cursorKey,
                                                                @RequestParam(defaultValue = "20") int limit){
        return ResponseEntity.ok(productService.getProducts(cursorKey, limit));
    }

    @PostMapping("/admin/products")
    public ResponseEntity<ProductResponse> createProduct(@RequestBody ProductCreateRequest productCreateRequest){
        log.info("create Product request , {} : ", productCreateRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(productService.createProduct(productCreateRequest));
    }

    @PatchMapping("/admin/products/{id}")
    public ResponseEntity<ProductResponse> updateProduct(@RequestBody ProductUpdateRequest productUpdateRequest, @PathVariable Long id){
        log.info("update Product request , {} : id {} ", productUpdateRequest, id);
        return ResponseEntity.ok(productService.updateProduct(productUpdateRequest, id));
    }

    @DeleteMapping("/admin/products/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id){
        log.info("delete product request id : {} ", id);
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }
}
