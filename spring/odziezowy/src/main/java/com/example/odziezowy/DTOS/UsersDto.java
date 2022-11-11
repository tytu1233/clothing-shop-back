package com.example.odziezowy.DTOS;

import com.example.odziezowy.Model.Users;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * A DTO for the {@link Users} entity
 */
@Data
public class UsersDto implements Serializable {
    private final Long id_user;
    @Email
    @NotBlank(message = "E-mail jest wymagany")
    private final String email;
    @Max(50)
    @NotBlank(message = "Login jest wymagany")
    private final String login;
    @Max(50)
    @NotBlank(message = "Hasło jest wymagane")
    private final String password;
    @Max(50)
    @NotBlank(message = "Imie jest wymagane")
    private final String name;
    @Max(50)
    @NotBlank(message = "Nazwisko jest wymagane")
    private final String surname;
    @Max(50)
    @NotBlank(message = "Miasto jest wymagane")
    private final String city;
    @NotBlank(message = "Kod pocztowy jest wymagany")
    @Max(6)
    private final String zipCode;
    @Max(50)
    @NotBlank(message = "Ulica jest wymagana")
    private final String street;
    @Max(2)
    @NotBlank(message = "Rola jest wymagana")
    private final String roles;
}