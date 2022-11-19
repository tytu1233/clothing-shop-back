package com.example.odziezowy.DTOS;

import com.example.odziezowy.Model.Users;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.Max;
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
    @Max(value = 50, message = "Maksymalnie 50 znaków")
    @NotBlank(message = "Login jest wymagany")
    private final String login;
    @Max(value = 50, message = "Maksymalnie 50 znaków")
    @NotBlank(message = "Hasło jest wymagane")
    private final String password;
    @Max(value = 50, message = "Maksymalnie 50 znaków")
    @NotBlank(message = "Imie jest wymagane")
    private final String name;
    @Max(value = 50, message = "Maksymalnie 50 znaków")
    @NotBlank(message = "Nazwisko jest wymagane")
    private final String surname;
    @Max(value = 50, message = "Maksymalnie 50 znaków")
    @NotBlank(message = "Miasto jest wymagane")
    private final String city;
    @NotBlank(message = "Kod pocztowy jest wymagany")
    @Max(value = 6, message = "Maksymalnie 6 znaków")
    private final String zipCode;
    @Max(value = 50, message = "Maksymalnie 50 znaków")
    @NotBlank(message = "Ulica jest wymagana")
    private final String street;
    @Max(value = 1, message = "Maksymalnie 1 znak")
    @NotBlank(message = "Rola jest wymagana")
    private final String roles;
    private final Integer active;
}