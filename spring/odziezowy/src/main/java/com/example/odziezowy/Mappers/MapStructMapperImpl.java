package com.example.odziezowy.Mappers;

import com.example.odziezowy.DTOS.RolesAllDto;
import com.example.odziezowy.DTOS.RolesDto;
import com.example.odziezowy.DTOS.UserGetDto;
import com.example.odziezowy.Model.Roles;
import com.example.odziezowy.Model.Users;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class MapStructMapperImpl implements MapStructMapper {
    @Override
    public UserGetDto userToUserGetDto(Users users) {
        if (users == null)
            return null;

        UserGetDto userGetDto = new UserGetDto();

        userGetDto.setId_user(users.getId_user());
        userGetDto.setName(users.getName());
        userGetDto.setSurname(users.getSurname());
        userGetDto.setLogin(users.getLogin());
        userGetDto.setPassword(users.getPassword());
        userGetDto.setEmail(users.getEmail());
        userGetDto.setPhone_number(users.getPhone_number());
        userGetDto.setRoles(roleToRoleDto(users.getRoles()));


        return userGetDto;
    }

    @Override
    public List<UserGetDto> usersToUsersAllDto(Page<Users> users) {
        if(users == null)
            return null;

        List<UserGetDto> list = new ArrayList<UserGetDto>( );
        for ( Users user : users ) {
            list.add( userToUserGetDto( user ) );
        }

        return list;
    }

    @Override
    public RolesAllDto rolesToRolesAllDto(Roles roles) {
        if (roles == null)
            return null;


        RolesAllDto rolesAllDto = new RolesAllDto();

        rolesAllDto.setId_role(roles.getId_role());
        rolesAllDto.setRole_name(roles.getRole_name());
        rolesAllDto.setUser_id(usersListToUsersDtoSet(roles.getUser_id()));

        return rolesAllDto;
    }

    @Override
    public RolesDto rolesToRolesDto(Roles roles) {
        if(roles == null)
            return null;

        RolesDto rolesDto = new RolesDto();

        rolesDto.setId_role( roles.getId_role() );
        rolesDto.setRole_name( roles.getRole_name() );

        return rolesDto;
    }

    private List<UserGetDto> usersListToUsersDtoSet(List<Users> set) {
        if ( set == null ) {
            return null;
        }

        List<UserGetDto> set1 = new ArrayList<UserGetDto>( Math.max( (int) ( set.size() / .75f ) + 1, 16 ) );
        for ( Users author : set ) {
            set1.add( userToUserGetDto( author ) );
        }

        return set1;
    }

    private RolesDto roleToRoleDto(Roles roles) {
        if(roles == null)
            return null;

        RolesDto rolesDto = new RolesDto();
        rolesDto.setId_role(roles.getId_role());
        rolesDto.setRole_name(roles.getRole_name());

        return rolesDto;
    }

    private List<RolesDto> rolesListToRolesDtoSet(List<Roles> list) {
        if ( list == null ) {
            return null;
        }

        List<RolesDto> list1 = new ArrayList<RolesDto>( Math.max( (int) ( list.size() / .75f ) + 1, 16 ) );
        for ( Roles roles : list ) {
            list1.add( rolesToRolesDto( roles ) );
        }

        return list1;
    }
}
