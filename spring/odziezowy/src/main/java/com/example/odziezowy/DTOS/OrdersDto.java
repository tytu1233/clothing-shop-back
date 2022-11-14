package com.example.odziezowy.DTOS;

import com.example.odziezowy.Model.Orders;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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