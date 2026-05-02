package com.roshin.cart.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@AllArgsConstructor
@Builder
public class ProductInfo implements Serializable
{
    private Long id;

    private String name;

    private String description;

    private Double price;
}
