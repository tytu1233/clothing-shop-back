package com.example.odziezowy.DTOS;

import com.example.odziezowy.Model.Products;
import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * A DTO for the {@link Products} entity
 */
@Data
public class ProductsDto implements Serializable {
    private final String id;
    @NotBlank(message = "Ilość jest wymagana")
    @Max(value = 10000, message = "Maksymalna ilość nie większa niż 10000")
    private final int quantity;
    @NotBlank(message = "Cena jest wymagana")
    private final Double finalPrice;
    @NotBlank(message = "Rozmiar jest wymagany")
    private final String size;
    @NotBlank(message = "Nazwa jest wymagana")
    @Size(max = 255, message = "Nazwa nie dłuższa niż 255 znaków")
    private final String name;
    @NotBlank(message = "Cena za produkt jest wymagana")
    private final Double price;
    @NotBlank(message = "Opis jest wymagany")
    private final String description;
    @NotBlank(message = "Producent jest wymagany")
    private final String brand;
    @NotBlank(message = "Zdjęcie jest wymagane")
    @Size(max = 255, message = "Nazwa zdjęcia nie dłuższa niż 255 znaków")
    private final String image;
    @NotBlank(message = "Kategoria jest wymagana")
    private final String category;
}