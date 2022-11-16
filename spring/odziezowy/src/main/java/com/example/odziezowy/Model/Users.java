package com.example.odziezowy.Model;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "users")
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id_user;

    @Basic
    @Column(unique=true)
    private String email;

    @Basic
    @Column(unique=true)
    private String login;

    @Basic
    @Column
    private String password;

    @Basic
    @Column
    private String name;

    @Basic
    @Column
    private String surname;

    @Basic
    @Column
    private String city;

    @Basic
    @Column
    private String zipCode;

    @Basic
    @Column
    private String street;

    @Basic
    @Column
    private Integer active;

    @ManyToOne()
    @JsonManagedReference
    @JoinColumn(name = "id_role")
    private Roles roles;

    @OneToMany(mappedBy = "users", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonBackReference
    private List<Orders> orders_id = new ArrayList<>();

    @OneToMany(mappedBy = "usersOpinion")
    @JsonBackReference
    private List<Opinions> opinionses = new ArrayList<>();

}
