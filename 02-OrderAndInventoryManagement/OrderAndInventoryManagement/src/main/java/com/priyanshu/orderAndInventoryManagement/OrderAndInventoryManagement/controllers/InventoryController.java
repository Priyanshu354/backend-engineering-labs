package com.priyanshu.orderAndInventoryManagement.OrderAndInventoryManagement.controllers;

import com.priyanshu.orderAndInventoryManagement.OrderAndInventoryManagement.dto.inventory.InventoryPageResponse;
import com.priyanshu.orderAndInventoryManagement.OrderAndInventoryManagement.dto.inventory.InventoryRequest;
import com.priyanshu.orderAndInventoryManagement.OrderAndInventoryManagement.dto.inventory.InventoryResponse;
import com.priyanshu.orderAndInventoryManagement.OrderAndInventoryManagement.dto.products.ProductResponse;
import com.priyanshu.orderAndInventoryManagement.OrderAndInventoryManagement.service.InventoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/inventory")
@Slf4j
@RequiredArgsConstructor
public class InventoryController {

    private final InventoryService inventoryService;

    @GetMapping("/{productId}")
    public ResponseEntity<InventoryResponse> getInventoryByProductId(@PathVariable Long productId){
        return ResponseEntity.ok(inventoryService.getInventoryByProductId(productId));
    }

    @GetMapping
    public ResponseEntity<List<InventoryPageResponse>> getInventories(){
        return ResponseEntity.ok(inventoryService.getInventories());
    }

    @PostMapping
    public ResponseEntity<InventoryResponse> createInventory(@RequestBody InventoryRequest inventoryRequest){
        log.info("create inventory request for product , {} : ", inventoryRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(inventoryService.createInventory(inventoryRequest));
    }

    @PatchMapping("/{productId}")
    public ResponseEntity<InventoryResponse> updateInventory(@RequestBody InventoryRequest inventoryRequest){
        log.info("update inventory request for product , {} : ", inventoryRequest);
        return ResponseEntity.ok(inventoryService.updateProduct(inventoryRequest));
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<Void> deleteInventory(@PathVariable Long productId){
        log.info("delete inventory for product id : {} ", productId);
        inventoryService.deleteInventory(productId);
        return ResponseEntity.noContent().build();
    }
}
