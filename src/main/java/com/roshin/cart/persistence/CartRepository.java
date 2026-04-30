package com.roshin.cart.persistence;

import com.roshin.cart.model.CartEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<CartEntity, Long>
{
}
