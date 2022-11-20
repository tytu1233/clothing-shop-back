package com.example.odziezowy.DTOS;

import com.example.odziezowy.Model.Categories;
import com.example.odziezowy.Model.Products;
import lombok.Data;

import java.io.Serializable;

/**
 * A DTO for the {@link Products} entity
 */
@Data
public class ProductsCountDto implements Serializable {
    private final Long id;
    private final Categories categories;

    public ProductsCountDto(Long id, Categories categories) {
        this.id = id;
        this.categories = categories;
    }
}