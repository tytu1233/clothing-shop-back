package com.example.odziezowy.Mappers;


import com.example.odziezowy.DTOS.RolesAllDto;
import com.example.odziezowy.DTOS.RolesDto;
import com.example.odziezowy.DTOS.UserGetDto;
import com.example.odziezowy.Model.Roles;
import com.example.odziezowy.Model.Users;
import org.mapstruct.Mapper;
import org.springframework.data.domain.Page;

import java.util.List;

@Mapper(componentModel = "spring")
public interface MapStructMapper {

    UserGetDto userToUserGetDto(Users users);

    List<UserGetDto> usersToUsersAllDto(Page<Users> users);

    RolesAllDto rolesToRolesAllDto(Roles roles);
    RolesDto rolesToRolesDto(Roles roles);


}
