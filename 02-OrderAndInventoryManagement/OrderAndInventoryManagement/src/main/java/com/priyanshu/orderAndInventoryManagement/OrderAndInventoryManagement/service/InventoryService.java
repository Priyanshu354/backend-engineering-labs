package com.priyanshu.orderAndInventoryManagement.OrderAndInventoryManagement.service;

import com.priyanshu.orderAndInventoryManagement.OrderAndInventoryManagement.dto.inventory.InventoryPageResponse;
import com.priyanshu.orderAndInventoryManagement.OrderAndInventoryManagement.dto.inventory.InventoryRequest;
import com.priyanshu.orderAndInventoryManagement.OrderAndInventoryManagement.dto.inventory.InventoryResponse;
import com.priyanshu.orderAndInventoryManagement.OrderAndInventoryManagement.dto.products.ProductResponse;
import com.priyanshu.orderAndInventoryManagement.OrderAndInventoryManagement.entity.Inventory;
import com.priyanshu.orderAndInventoryManagement.OrderAndInventoryManagement.entity.Product;
import com.priyanshu.orderAndInventoryManagement.OrderAndInventoryManagement.errors.exception.BadRequestException;
import com.priyanshu.orderAndInventoryManagement.OrderAndInventoryManagement.errors.exception.ResourceNotFoundException;
import com.priyanshu.orderAndInventoryManagement.OrderAndInventoryManagement.mapper.InventoryMapper;
import com.priyanshu.orderAndInventoryManagement.OrderAndInventoryManagement.repo.InventoryRepo;
import com.priyanshu.orderAndInventoryManagement.OrderAndInventoryManagement.repo.ProductRepo;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jspecify.annotations.Nullable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
@Slf4j
public class InventoryService {

    private final InventoryRepo inventoryRepo;
    private final InventoryMapper inventoryMapper;
    private final ProductRepo productRepo;

    @PreAuthorize("hasAuthority('INVENTORY_READ')")
    public InventoryResponse getInventoryByProductId(Long productId) {
        Inventory inventory = inventoryRepo.findByProductId(productId).orElseThrow(() -> new ResourceNotFoundException("Inventory", productId));
        return inventoryMapper.inventoryToInventoryResponse(inventory);
    }

    @PreAuthorize("hasAuthority('INVENTORY_READ')")
    public List<InventoryPageResponse> getInventories() {
        List<Inventory> inventories = inventoryRepo.findAllInventory();
        return inventoryMapper.toListInventory(inventories);
    }

    @PreAuthorize("hasAuthority('INVENTORY_CREATE')")
    public InventoryResponse createInventory(InventoryRequest inventoryRequest) {
        inventoryRepo.findByProductId(inventoryRequest.productId()).ifPresent((inventory) -> {
                new BadRequestException("inventory is already present");
            }
        );

        Product product = productRepo.findById(inventoryRequest.productId()).orElseThrow(() ->
                new ResourceNotFoundException("Product", inventoryRequest.productId()));

        Inventory inventory = new Inventory();
        inventory.setQuantity(inventoryRequest.quantity());
        inventory.setProduct(product);

        inventoryRepo.save(inventory);

        log.info("inventory created for productId : {}", inventoryRequest.productId());
        return inventoryMapper.inventoryToInventoryResponse(inventory);
    }

    @PreAuthorize("hasAuthority('INVENTORY_UPDATE')")
    public InventoryResponse updateProduct(InventoryRequest inventoryRequest) {
        Inventory inventory = inventoryRepo.findByProductId(inventoryRequest.productId()).orElseThrow(() ->
                    new ResourceNotFoundException("Inventory", inventoryRequest.productId()));

        inventory.setQuantity(inventoryRequest.quantity());
        log.info("inventory updated for productId : {}", inventoryRequest.productId());
        return inventoryMapper.inventoryToInventoryResponse(inventory);
    }

    @PreAuthorize("hasAuthority('INVENTORY_DELETE')")
    public void deleteInventory(Long productId) {
        Inventory inventory = inventoryRepo.findByProductId(productId).orElseThrow(() ->
                new ResourceNotFoundException("Inventory", productId));

        inventoryRepo.delete(inventory);
        log.info("inventory delete for productId : {}", productId);
    }

    @Transactional
    public void decreaseStock(Long productId, Integer quantity) {
        Inventory inventory = inventoryRepo.findByProductId(productId).orElseThrow(() ->
                new ResourceNotFoundException("Inventory", productId));

        if(inventory.getQuantity() < quantity) {
            new BadRequestException("Insufficient stock");
        }

        inventory.setQuantity(inventory.getQuantity() - quantity);
    }

}
