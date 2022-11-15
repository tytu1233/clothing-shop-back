package com.example.odziezowy.Service;

import com.example.odziezowy.DTOS.UsersDto;
import com.example.odziezowy.Exception.ResourceNotFoundException;
import com.example.odziezowy.Model.Roles;
import com.example.odziezowy.Model.Users;
import com.example.odziezowy.Repository.RolesRepository;
import com.example.odziezowy.Repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class UsersService {

    private final UsersRepository usersRepository;
    private final RolesRepository rolesRepository;
    private final PasswordEncoder passwordEncoder;
    @Autowired
    public UsersService(UsersRepository usersRepository, RolesRepository rolesRepository) {
        this.usersRepository = usersRepository;
        this.rolesRepository = rolesRepository;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    public ResponseEntity<Users> updateUserService(Long id, UsersDto usersDto) {
        Users updateUser = usersRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Can not be updated " + id));
        Roles roles = rolesRepository.findByRoleName(usersDto.getRoles());
        updateUser.setRoles(roles);
        updateUser.setName(usersDto.getName());
        updateUser.setSurname(usersDto.getSurname());
        updateUser.setLogin(usersDto.getLogin());
        String encodedPassword = this.passwordEncoder.encode(usersDto.getPassword());
        updateUser.setPassword(encodedPassword);
        updateUser.setEmail(usersDto.getEmail());
        updateUser.setCity(usersDto.getCity());
        updateUser.setStreet(usersDto.getStreet());
        updateUser.setZipCode(usersDto.getZipCode());

        usersRepository.save(updateUser);
        return new ResponseEntity<>(updateUser, HttpStatus.ACCEPTED);
    }

    public ResponseEntity<String> deleteUserService(Long id) {
        Users users = usersRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Can not find user" + id));

        usersRepository.delete(users);
        return new ResponseEntity<>(HttpStatus.OK);
    }


    public ResponseEntity<Users> registerUserService(Long id, UsersDto user) {
        Roles roles = rolesRepository.findById(id).get();
        Users users = new Users();
        users.setRoles(roles);
        users.setName(user.getName());
        users.setSurname(user.getSurname());
        users.setLogin(user.getLogin());
        String encodedPassword = this.passwordEncoder.encode(user.getPassword());
        users.setPassword(encodedPassword);
        users.setEmail(user.getEmail());
        users.setCity(user.getCity());
        users.setZipCode(user.getZipCode());
        users.setStreet(user.getStreet());
        usersRepository.save(users);
        return new ResponseEntity<>(users, HttpStatus.CREATED);
    }
}
