package com.priyanshu.orderAndInventoryManagement.OrderAndInventoryManagement.enums;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.priyanshu.orderAndInventoryManagement.OrderAndInventoryManagement.enums.Permission.*;

public enum Role {
    USER(Set.of(ORDER_CREATE,ORDER_READ)),
    ADMIN(Set.of(ORDER_CREATE,
            ORDER_UPDATE,
            ORDER_READ,
            ORDER_DELETE,

            INVENTORY_READ,
            INVENTORY_UPDATE,
            INVENTORY_CREATE,
            INVENTORY_DELETE
    ));

    Set<Permission> permissions;

    Role(Set<Permission> permissions){
        this.permissions = permissions;
    }

    public List<GrantedAuthority> getAuthorities() {
        return permissions.stream()
                .map(permission ->
                        new SimpleGrantedAuthority(permission.name())
                )
                .collect(Collectors.toList());
    }


}
