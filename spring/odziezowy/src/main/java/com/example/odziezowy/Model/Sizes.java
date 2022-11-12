package com.example.odziezowy.Model;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "sizes")
public class Sizes {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long idSize;

    @Column
    private String sizeName;

    @Column
    private Long amount;

    @ManyToOne
    @JsonManagedReference
    @JoinColumn(name = "products_id")
    private Products productsSizes;

}
