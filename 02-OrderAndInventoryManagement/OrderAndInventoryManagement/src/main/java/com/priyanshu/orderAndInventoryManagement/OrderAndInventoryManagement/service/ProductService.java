package com.priyanshu.orderAndInventoryManagement.OrderAndInventoryManagement.service;

import com.priyanshu.orderAndInventoryManagement.OrderAndInventoryManagement.dto.products.ProductPaginatedResponse;
import com.priyanshu.orderAndInventoryManagement.OrderAndInventoryManagement.dto.products.ProductCreateRequest;
import com.priyanshu.orderAndInventoryManagement.OrderAndInventoryManagement.dto.products.ProductResponse;
import com.priyanshu.orderAndInventoryManagement.OrderAndInventoryManagement.dto.products.ProductUpdateRequest;
import com.priyanshu.orderAndInventoryManagement.OrderAndInventoryManagement.entity.Inventory;
import com.priyanshu.orderAndInventoryManagement.OrderAndInventoryManagement.entity.Product;
import com.priyanshu.orderAndInventoryManagement.OrderAndInventoryManagement.errors.exception.ResourceNotFoundException;
import com.priyanshu.orderAndInventoryManagement.OrderAndInventoryManagement.mapper.ProductMapper;
import com.priyanshu.orderAndInventoryManagement.OrderAndInventoryManagement.repo.InventoryRepo;
import com.priyanshu.orderAndInventoryManagement.OrderAndInventoryManagement.repo.ProductRepo;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aopalliance.intercept.Interceptor;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import org.springframework.data.domain.Pageable;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductService {
    private final ProductRepo productRepo;
    private final ProductMapper productMapper;

    public ProductResponse getProductById(Long id) {
        Product product = productRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Product", id));
        return productMapper.ProductToProductResponse(product);
    }

    public ProductPaginatedResponse getProducts(Long cursorKey, int limit) {
        Pageable pageable = PageRequest.of(0, limit);

        List<Product> products =
                productRepo.findAllPaginated(cursorKey, pageable);

        Long nextCursor = products.isEmpty() ? null : products.getLast().getId();

        return productMapper.toProductPaginatedResponse(products, nextCursor);
    }

    @PreAuthorize("hasAuthority('PRODUCT_CREATE')")
    public ProductResponse createProduct(ProductCreateRequest productCreateRequest) {
        Product newProduct = new Product();

        newProduct.setName(productCreateRequest.name());
        newProduct.setPrice(productCreateRequest.price());
        newProduct.setDescription(productCreateRequest.description());

        productRepo.save(newProduct);

        log.info("product Id : {} is created", newProduct.getId());

        return productMapper.ProductToProductResponse(newProduct);
    }

    @PreAuthorize("hasAuthority('PRODUCT_UPDATE')")
    public ProductResponse updateProduct(ProductUpdateRequest productUpdateRequest, Long id) {
        Product product = productRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Product", id));
        productMapper.updateProduct(productUpdateRequest, product);
        productRepo.save(product);

        log.info("product Id : {} is updated", id);

        return productMapper.ProductToProductResponse(product);
    }

    @PreAuthorize("hasAuthority('PRODUCT_DELETE')")
    public void deleteProduct(Long id) {
        Product product = productRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Product", id));
        productRepo.delete(product);

        log.info("product name : {} or Id : {} is deleted", product.getName(), id);

        return ;
    }
}
