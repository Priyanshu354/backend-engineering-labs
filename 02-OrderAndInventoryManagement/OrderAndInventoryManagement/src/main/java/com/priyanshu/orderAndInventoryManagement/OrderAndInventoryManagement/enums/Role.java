package com.priyanshu.orderAndInventoryManagement.OrderAndInventoryManagement.enums;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.priyanshu.orderAndInventoryManagement.OrderAndInventoryManagement.enums.Permission.*;

public enum Role {
    ROLE_USER(Set.of(ORDER_CREATE,ORDER_READ)),
    ROLE_ADMIN(Set.of(ORDER_CREATE,
            ORDER_UPDATE,
            ORDER_READ,
            ORDER_DELETE,
            ORDER_READ_ALL,

            INVENTORY_READ,
            INVENTORY_UPDATE,
            INVENTORY_CREATE,
            INVENTORY_DELETE,

            PRODUCT_UPDATE,
            PRODUCT_CREATE,
            PRODUCT_DELETE
    ));

    Set<Permission> permissions; // variable

    Role(Set<Permission> permissions){
        this.permissions = permissions;
    } // constructor -> value set kar dete h

    public List<GrantedAuthority> getAuthorities(Role role) {

        // old
//        return permissions.stream()
//                .map(permission ->
//                        new SimpleGrantedAuthority(permission.name())
//                )
//                .collect(Collectors.toList());

        List<GrantedAuthority> list = permissions.stream()
                .map(permission ->
                        new SimpleGrantedAuthority(permission.name())
                )
                .collect(Collectors.toList());

        list.add(new SimpleGrantedAuthority(role.name())); // ROLE_ADMIN, ROLE_USER

        return list;
    }


}
