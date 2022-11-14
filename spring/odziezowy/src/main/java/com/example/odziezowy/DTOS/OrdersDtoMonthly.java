package com.example.odziezowy.DTOS;

import com.example.odziezowy.Model.Orders;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.Entity;
import java.io.Serializable;

/**
 * A DTO for the {@link Orders} entity
 */
@Data
public class OrdersDtoMonthly implements Serializable {
    private final int month;
    private final Double finalPrice;

    public OrdersDtoMonthly(Double finalPrice, int month) {
        this.month = month;
        this.finalPrice = finalPrice;
    }
}