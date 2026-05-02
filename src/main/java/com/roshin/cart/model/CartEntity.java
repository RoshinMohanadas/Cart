package com.roshin.cart.model;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Entity
@Data
public class CartEntity
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "username")
    private String username;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @JoinColumn(name = "cart_id")
    private List<ProductLineEntity> productLines;

    @Transient
    private Double totalCost;

    public Double getTotalCost()
    {
        if(CollectionUtils.isEmpty(productLines))
        {
            return 0.0d;
        }
        else
        {
            return productLines.stream().map(ProductLineEntity::getCost).reduce(0.0d, Double::sum);
        }
    }
}
