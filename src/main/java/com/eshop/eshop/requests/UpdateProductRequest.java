package com.eshop.eshop.requests;

import com.eshop.eshop.model.Category;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class UpdateProductRequest {
    private Long id;
    private String name;
    private String brand;
    private String Description;
    private BigDecimal price;
    private int inventory;
    private Category category;
}
