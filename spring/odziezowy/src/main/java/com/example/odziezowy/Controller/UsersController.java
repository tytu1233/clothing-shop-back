package com.example.odziezowy.Controller;

import com.example.odziezowy.Model.Credentials;
import com.example.odziezowy.Model.Users;
import com.example.odziezowy.DTOS.UsersDto;
import com.example.odziezowy.Repository.UsersRepository;
import com.example.odziezowy.Service.UsersService;
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

    private final UsersRepository usersRepository;
    private final UsersService usersService;

    @Autowired
    public UsersController(UsersRepository usersRepository, UsersService usersService) {
        this.usersRepository = usersRepository;
        this.usersService = usersService;
    }

    @PostMapping("/{id}")
    public ResponseEntity<Users> register(@PathVariable(value = "id") Long id, @RequestBody UsersDto user) {
        return usersService.registerUserService(id, user);
    }

    @GetMapping("/{id}")
    public Optional<Users> getById(@PathVariable(value = "id") Long id) {
        return usersRepository.findById(id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUserById(@PathVariable(value = "id") Long id) {
        return usersService.deleteUserService(id);
    }

    @GetMapping
    public Page<Users> getAll(@RequestParam(defaultValue = "0") Integer pageNo,
                              @RequestParam(defaultValue = "12") Integer pageSize) {
        Pageable paging = PageRequest.of(pageNo, pageSize);
        return usersRepository.findAll(paging);
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<String> getUserByEmail(@PathVariable(value = "email") String email) {
        if(usersRepository.findByEmail(email).isEmpty()) {
            return ResponseEntity.status(HttpStatus.OK).body("wolne");
        }
        return ResponseEntity.status(HttpStatus.OK).body("zajete");
    }

    @PutMapping("/{id}")
    public ResponseEntity<Users> updateUser(@PathVariable Long id, @RequestBody UsersDto usersDto) {
        return usersService.updateUserService(id, usersDto);
    }

    @PutMapping("/profile/{id}")
    public ResponseEntity<Users> updateUserProfile(@PathVariable Long id, @RequestBody UsersDto usersDto) {
        return usersService.updateUserProfileService(id, usersDto);
    }

    @PostMapping("/credentials")
    public ResponseEntity<String> passwordMatches(@RequestBody Credentials credentials) {
        return usersService.passwordMatchesService(credentials);
    }

    @GetMapping("/login/{login}")
    public ResponseEntity<String> getUserByLogin(@PathVariable(value = "login") String login) {
        if(usersRepository.findByLogin(login).isEmpty()) {
            return ResponseEntity.status(HttpStatus.OK).body("wolne");
        }
        return ResponseEntity.status(HttpStatus.OK).body("zajete");
    }
}
