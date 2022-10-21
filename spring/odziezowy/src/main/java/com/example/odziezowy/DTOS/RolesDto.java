package com.example.odziezowy.DTOS;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RolesDto {
    @JsonProperty("id_role")
    private Long id_role;

    @JsonProperty("role_name")
    private String role_name;
}
