package com.example.odziezowy.Controller;

import com.example.odziezowy.Model.Roles;
import com.example.odziezowy.Model.Users;
import com.example.odziezowy.DTOS.UsersDto;
import com.example.odziezowy.Repository.RolesRepository;
import com.example.odziezowy.Repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@CrossOrigin("*")
@RequestMapping("/users")
public class UsersController {

    private UsersRepository usersRepository;

    private RolesRepository rolesRepository;

    @Autowired
    public UsersController(UsersRepository usersRepository, RolesRepository rolesRepository) {
        this.usersRepository = usersRepository;
        this.rolesRepository = rolesRepository;
    }

    @PostMapping("/{id}")
    public ResponseEntity<Users> register(@PathVariable(value = "id") Long id, @RequestBody UsersDto user) {
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

    @GetMapping("/{id}")
    public Optional<Users> getById(@PathVariable(value = "id") Long id) {
        return usersRepository.findById(id);
    }

    @DeleteMapping("/{idas}")
    public ResponseEntity<String> deleteUserById(@PathVariable(value = "id") Long id) {
        usersRepository.deleteById(id);
        return ResponseEntity.status(HttpStatus.OK).body("deleted");
    }

    @GetMapping
    public Page<Users> getAll(@RequestParam(defaultValue = "0") Integer pageNo,
                              @RequestParam(defaultValue = "10") Integer pageSize) {
        Pageable paging = PageRequest.of(pageNo, pageSize);
        return usersRepository.findAllByRoles(paging, rolesRepository.findById(Long.valueOf(2)));
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<String> getUserByEmail(@PathVariable(value = "email") String email) {
        if(usersRepository.findByEmail(email).isEmpty()) {
            return ResponseEntity.status(HttpStatus.OK).body("wolne");
        }
        return ResponseEntity.status(HttpStatus.OK).body("zajete");
    }

    @GetMapping("/login/{login}")
    public ResponseEntity<String> getUserByLogin(@PathVariable(value = "login") String login) {
        if(usersRepository.findByLogin(login).isEmpty()) {
            return ResponseEntity.status(HttpStatus.OK).body("wolne");
        }
        return ResponseEntity.status(HttpStatus.OK).body("zajete");
    }
}
