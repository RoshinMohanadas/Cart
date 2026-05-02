package com.roshin.cart.util;

import com.roshin.cart.dto.Cart;
import com.roshin.cart.dto.ProductLine;
import com.roshin.cart.model.CartEntity;

public class Mapper
{
    private Mapper()
    {
        // Prevent instantiation
    }

    public static Cart convertEntityToDTO(CartEntity entity)
    {
        return Cart.builder()
                .id(entity.getId())
                .user(entity.getUsername())
                .productLines(entity.getProductLines().stream().map(pl -> ProductLine.builder()
                        .id(pl.getId())
                        .productId(pl.getProductId())
                        .count(pl.getCount())
                        .productCost(pl.getProductCost())
                        .lineCost(pl.getCost())
                        .build())
                        .toList())
                .totalCost(entity.getTotalCost())
                .build();
    }
}
