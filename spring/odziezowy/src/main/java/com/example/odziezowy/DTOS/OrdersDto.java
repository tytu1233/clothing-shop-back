package com.example.odziezowy.DTOS;

import com.example.odziezowy.Model.Orders;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * A DTO for the {@link Orders} entity
 */
@Data
public class OrdersDto implements Serializable {
    private final Long idOrders;
    private final Double finalPrice;
    private final String status;
    private final LocalDate date;
}