package com.example.odziezowy.Model;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

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
    @Column
    private String email;

    @Basic
    @Column
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
    private String phone_number;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_role")
    private Roles roles;

}
