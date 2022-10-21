package com.example.odziezowy.DTOS;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class RolesAllDto {
    @JsonProperty("id_role")
    private Long id_role;

    @JsonProperty("role_name")
    private String role_name;

    @JsonProperty("user_id")
    private List<UserGetDto> user_id;
}
