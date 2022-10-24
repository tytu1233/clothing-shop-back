package com.example.odziezowy.Controller;


import com.example.odziezowy.Model.Roles;
import com.example.odziezowy.Repository.RolesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/roles")
public class RolesController {

    private RolesRepository rolesRepository;

    @Autowired
    public RolesController(RolesRepository rolesRepository) {
        this.rolesRepository = rolesRepository;
    }


    @GetMapping("{id}")
    public Optional<Roles> getById(@RequestParam Long id) {
        return rolesRepository.findById(id);
    }

    @GetMapping()
    public Page<Roles> getAll(@RequestParam(defaultValue = "0") Integer pageNo,
                              @RequestParam(defaultValue = "10") Integer pageSize) {
        Pageable paging = PageRequest.of(pageNo, pageSize);
        return rolesRepository.findAll(paging);
    }

}
