package com.priyanshu.orderAndInventoryManagement.OrderAndInventoryManagement.repo;

import com.priyanshu.orderAndInventoryManagement.OrderAndInventoryManagement.entity.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepo extends JpaRepository<User, Long> {
    Optional<User> findByEmail(@Email(message = "email should be valid") @NotBlank(message = "email can't be blank") String email);
}
