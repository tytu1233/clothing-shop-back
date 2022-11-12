package com.example.odziezowy.Model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "orders_products")
public class OrdersProducts {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id_orders_products;

    @Column
    private int quantity;

    @Column
    private String size;

    @ManyToOne
    @JsonManagedReference
    @JoinColumn(name = "orders_id")
    private Orders orders;

    @ManyToOne
    @JsonManagedReference
    @JoinColumn(name = "products_id")
    private Products products;

}
