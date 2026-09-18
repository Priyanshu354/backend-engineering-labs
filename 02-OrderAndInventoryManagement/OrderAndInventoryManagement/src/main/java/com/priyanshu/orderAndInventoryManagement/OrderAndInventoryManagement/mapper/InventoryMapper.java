package com.priyanshu.orderAndInventoryManagement.OrderAndInventoryManagement.mapper;

import com.priyanshu.orderAndInventoryManagement.OrderAndInventoryManagement.dto.inventory.InventoryPageResponse;
import com.priyanshu.orderAndInventoryManagement.OrderAndInventoryManagement.dto.inventory.InventoryResponse;
import com.priyanshu.orderAndInventoryManagement.OrderAndInventoryManagement.entity.Inventory;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface InventoryMapper {
    InventoryResponse inventoryToInventoryResponse(Inventory inventory);

    List<InventoryPageResponse> toListInventory(List<Inventory> inventories);
}
