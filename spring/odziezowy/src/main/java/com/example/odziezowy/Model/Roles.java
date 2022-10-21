package com.example.odziezowy.Model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "roles")
public class Roles {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id_role;

    @Column
    private String role_name;

    @OneToMany(mappedBy = "roles", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Users> user_id = new ArrayList<>();

}
