package com.roshin.cart.dto;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
public class ProductLine implements Serializable
{
    private Long id;

    private Long productId;

    private Double productCost;

    private Long count;

    private Double lineCost;
}
