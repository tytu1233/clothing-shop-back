package com.example.odziezowy.Controller;

import com.example.odziezowy.DTOS.UserGetDto;
import com.example.odziezowy.Mappers.MapStructMapper;
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
    public ResponseEntity<Page<UserGetDto>> getAll(@RequestParam(defaultValue = "0") Integer pageNo,
                                                   @RequestParam(defaultValue = "10") Integer pageSize) {
        Pageable paging = PageRequest.of(pageNo, pageSize);
        List<UserGetDto> pageResult = mapstructMapper.usersToUsersAllDto(usersRepository.findAll(paging));
        return new ResponseEntity<>(
                new PageImpl<>(pageResult, paging, pageResult.size())
                ,
                HttpStatus.OK
        );
    }

}
