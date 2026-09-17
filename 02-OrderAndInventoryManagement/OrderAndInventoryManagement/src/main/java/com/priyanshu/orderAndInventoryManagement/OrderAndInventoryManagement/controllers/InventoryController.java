package com.priyanshu.orderAndInventoryManagement.OrderAndInventoryManagement.controllers;

import com.priyanshu.orderAndInventoryManagement.OrderAndInventoryManagement.dto.inventory.InventoryResponse;
import com.priyanshu.orderAndInventoryManagement.OrderAndInventoryManagement.dto.products.ProductCreateRequest;
import com.priyanshu.orderAndInventoryManagement.OrderAndInventoryManagement.dto.products.ProductPaginatedResponse;
import com.priyanshu.orderAndInventoryManagement.OrderAndInventoryManagement.dto.products.ProductResponse;
import com.priyanshu.orderAndInventoryManagement.OrderAndInventoryManagement.dto.products.ProductUpdateRequest;
import com.priyanshu.orderAndInventoryManagement.OrderAndInventoryManagement.service.InventoryService;
import com.priyanshu.orderAndInventoryManagement.OrderAndInventoryManagement.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/inventory")
@Slf4j
@RequiredArgsConstructor
public class InventoryController {

    private final InventoryService inventoryService;

    @GetMapping("/{id}")
    public ResponseEntity<InventoryResponse> getInventoryByProductId(@PathVariable Long productId){
        return ResponseEntity.ok(inventoryService.getInventoryByProductId(id));
    }

    @GetMapping
    public ResponseEntity<List<InventoryResponse>> getInventories(){
        return ResponseEntity.ok(inventoryService.getInventoies());
    }

    @PostMapping("/{productId}")
    public ResponseEntity<InventoryResponse> createInventory(@PathVariable Long productId){
        log.info("create Product request , {} : ", productCreateRequest);
        return ResponseEntity.ok(inventoryService.createInventory(productId));
    }

    @PatchMapping("/{productId}")
    public ResponseEntity<ProductResponse> updateInventory(@PathVariable Long productId){
        return ResponseEntity.ok(inventoryService.updateProduct(productUpdateRequest, productId));
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<Void> deleteInventory(@PathVariable Long productId){
        inventoryService.deleteInventory(productId);
        return ResponseEntity.noContent().build();
    }
}
