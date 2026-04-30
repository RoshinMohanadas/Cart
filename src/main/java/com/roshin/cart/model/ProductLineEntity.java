package com.roshin.cart.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
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

    @Column(name = "count")
    private Long count;
}
