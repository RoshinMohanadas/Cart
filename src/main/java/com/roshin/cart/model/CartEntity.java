package com.roshin.cart.model;

import com.roshin.cart.dto.ProductLine;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
public class CartEntity
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user")
    private String user;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @JoinColumn(name = "cart_id")
    private List<ProductLineEntity> productLines;
}
