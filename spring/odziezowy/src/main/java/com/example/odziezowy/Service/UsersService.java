package com.example.odziezowy.Service;

import com.example.odziezowy.DTOS.UsersDto;
import com.example.odziezowy.Model.Roles;
import com.example.odziezowy.Model.Users;
import com.example.odziezowy.Repository.RolesRepository;
import com.example.odziezowy.Repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

@Service
public class UsersService {

    private final UsersRepository usersRepository;
    private final RolesRepository rolesRepository;

    @Autowired
    public UsersService(UsersRepository usersRepository, RolesRepository rolesRepository) {
        this.usersRepository = usersRepository;
        this.rolesRepository = rolesRepository;
    }


    public ResponseEntity<Users> registerUserService(Long id, UsersDto user) {
        Roles roles = rolesRepository.findById(id).get();
        Users users = new Users();
        users.setRoles(roles);
        users.setName(user.getName());
        users.setSurname(user.getSurname());
        users.setLogin(user.getLogin());
        users.setPassword(user.getPassword());
        users.setEmail(user.getEmail());
        users.setAddress(user.getAddress());
        usersRepository.save(users);
        return new ResponseEntity<>(users, HttpStatus.CREATED);
    }
}