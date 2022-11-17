package com.example.odziezowy.DTOS;

import com.example.odziezowy.Model.Products;
import lombok.Data;

import java.io.Serializable;

/**
 * A DTO for the {@link Products} entity
 */
@Data
public class ProductsDto implements Serializable {
    private final String id;
    private final int quantity;
    private final Double finalPrice;
    private final String size;
    private final String name;
    private final Double price;
    private final String description;
    private final String brand;
    private final String image;
    private final String category;
}