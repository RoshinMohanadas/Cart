package com.roshin.cart.persistence;

import com.roshin.cart.model.CartEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CartRepository extends JpaRepository<CartEntity, Long>
{
    Optional<CartEntity> findByUsername(String name);
}
