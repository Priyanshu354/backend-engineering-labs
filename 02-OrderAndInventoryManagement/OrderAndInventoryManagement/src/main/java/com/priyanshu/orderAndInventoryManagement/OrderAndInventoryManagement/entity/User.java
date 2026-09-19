package com.priyanshu.orderAndInventoryManagement.OrderAndInventoryManagement.entity;

import com.priyanshu.orderAndInventoryManagement.OrderAndInventoryManagement.enums.Role;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.Instant;
import java.util.Collection;
import java.util.List;

@Table(name="users")
@Entity
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(nullable=false)
    String name;

    @Column(unique = true, nullable=false)
    String email;

    @Column(nullable = false)
    String password;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    Role role;

    @CreationTimestamp
    Instant created_at;

    @UpdateTimestamp
    Instant updated_at;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return role.getAuthorities(role);
    }

    @Override
    public String getUsername() {
        return email;
    }
}
