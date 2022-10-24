package com.example.odziezowy.Controller;

import com.example.odziezowy.Model.Users;
import com.example.odziezowy.Repository.RolesRepository;
import com.example.odziezowy.Repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UsersController {

    private UsersRepository usersRepository;

    private RolesRepository rolesRepository;

    @Autowired
    public UsersController(UsersRepository usersRepository, RolesRepository rolesRepository) {
        this.usersRepository = usersRepository;
        this.rolesRepository = rolesRepository;
    }

    @GetMapping("/{id}")
    public Optional<Users> getById(@PathVariable(value = "id") Long id) {
        return usersRepository.findById(id);
    }


    @GetMapping()
    public Page<Users> getAll(@RequestParam(defaultValue = "0") Integer pageNo,
                              @RequestParam(defaultValue = "10") Integer pageSize) {
        Pageable paging = PageRequest.of(pageNo, pageSize);
        return usersRepository.findAllByRoles(paging, rolesRepository.findById(Long.valueOf(2)));
    }
}
