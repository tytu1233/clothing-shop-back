package com.example.odziezowy.Controller;

import com.example.odziezowy.DTOS.UserGetDto;
import com.example.odziezowy.Mappers.MapStructMapper;
import com.example.odziezowy.Model.Users;
import com.example.odziezowy.Repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UsersController {
    private MapStructMapper mapstructMapper;

    private UsersRepository usersRepository;

    @Autowired
    public UsersController(MapStructMapper mapstructMapper, UsersRepository usersRepository) {
        this.mapstructMapper = mapstructMapper;
        this.usersRepository = usersRepository;
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserGetDto> getById(@PathVariable(value = "id") Long id) {
        return new ResponseEntity<>(
                mapstructMapper.userToUserGetDto(
                        usersRepository.findById(id).get()
                ),
                HttpStatus.OK
        );
    }

    @GetMapping
    public ResponseEntity<List<UserGetDto>> getAll() {
        return new ResponseEntity<>(
                mapstructMapper.usersToUsersAllDto(
                        usersRepository.findAll()
                ),
                HttpStatus.OK
        );
    }

}
