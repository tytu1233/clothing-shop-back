package com.example.odziezowy.DTOS;

import com.example.odziezowy.Model.Roles;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserGetDto {
    @JsonProperty("id_user")
    private Long id_user;

    @JsonProperty("email")
    private String email;

    @JsonProperty("login")
    private String login;

    @JsonProperty("password")
    private String password;

    @JsonProperty("name")
    private String name;

    @JsonProperty("surname")
    private String surname;

    @JsonProperty("phone_number")
    private String phone_number;

    @JsonProperty("roles")
    private RolesDto roles;
}
