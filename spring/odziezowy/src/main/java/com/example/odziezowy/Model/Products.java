package com.example.odziezowy.Model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "products")
public class Products {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;

    @Column
    private String name;

    @Column
    private Double price;

    @Column
    private String description;

    @Column
    private String brand;

    @Column
    private Long amount;

    @Column
    private String image;
    @ManyToOne(cascade = CascadeType.ALL)
    @JsonManagedReference
    @JoinColumn(name = "categories_id")
    private Categories categories;

    @OneToMany(mappedBy = "products", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonBackReference
    private List<OrdersProducts> ordersProductses = new ArrayList<>();

    @OneToMany(mappedBy = "productsId", orphanRemoval = true)
    @JsonBackReference
    private List<Opinions> opinionses = new ArrayList<>();

    @OneToMany(mappedBy = "productsSizes", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonBackReference
    private List<Sizes> sizeses = new ArrayList<>();

}
