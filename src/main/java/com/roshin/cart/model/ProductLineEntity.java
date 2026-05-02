package com.roshin.cart.model;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;

@Entity
@Builder
@Data
public class ProductLineEntity
{
    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "product_id")
    private Long productId;

    @Column(name = "product_cost")
    private Double productCost;

    @Column(name = "count")
    private Long count;

    @Transient
    @Column(name = "cost")
    private Double cost;

    public Double getCost()
    {
        return productCost * count;
    }
}
