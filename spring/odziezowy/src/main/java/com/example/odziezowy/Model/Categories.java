package com.example.odziezowy.Model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "categories")
public class Categories {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long idCategory;

    @Column
    private String categoryName;

    @OneToMany(mappedBy = "categories", orphanRemoval = true)
    @JsonBackReference
    private List<Products> productses = new ArrayList<>();

}
