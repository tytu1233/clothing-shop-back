package com.example.odziezowy.DTOS;

import com.example.odziezowy.Model.Users;
import lombok.Data;

import java.io.Serializable;

/**
 * A DTO for the {@link Users} entity
 */
@Data
public class UsersDto implements Serializable {
    private final Long id_user;
    private final String email;
    private final String login;
    private final String password;
    private final String name;
    private final String surname;
    private final String address;
    private final String roles;
}