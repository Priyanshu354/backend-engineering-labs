package com.priyanshu.orderAndInventoryManagement.OrderAndInventoryManagement.mapper;

import com.priyanshu.orderAndInventoryManagement.OrderAndInventoryManagement.dto.products.ProductPaginatedResponse;
import com.priyanshu.orderAndInventoryManagement.OrderAndInventoryManagement.dto.products.ProductResponse;
import com.priyanshu.orderAndInventoryManagement.OrderAndInventoryManagement.dto.products.ProductUpdateRequest;
import com.priyanshu.orderAndInventoryManagement.OrderAndInventoryManagement.entity.Product;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    ProductResponse ProductToProductResponse(Product product);

    @BeanMapping(
            nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
    )
    void updateProduct(
            ProductUpdateRequest request,
            @MappingTarget Product product
    );

    ProductPaginatedResponse toProductPaginatedResponse(List<Product> products, Long cursorKey);
}
