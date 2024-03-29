package com.example.odziezowy.Model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@Table(name = "orders")
public class Orders {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long idOrders;

    @Column
    private Double finalPrice;

    @Column
    private String status;

    @Column
    private LocalDate date;

    @ManyToOne
    @JsonManagedReference
    @JoinColumn(name = "id_user")
    private Users users;

    @OneToMany(mappedBy = "orders", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonBackReference
    private List<OrdersProducts> ordersProductses = new ArrayList<>();
}
