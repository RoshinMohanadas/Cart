package com.roshin.cart.dto;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
@Builder
public class Cart implements Serializable
{
    private Long id;

    private String user;

    private List<ProductLine> productLines;

    private Double totalCost;
}
