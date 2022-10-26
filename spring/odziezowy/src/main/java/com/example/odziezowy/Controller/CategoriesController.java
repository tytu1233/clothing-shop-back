package com.example.odziezowy.Controller;

import com.example.odziezowy.Model.Categories;
import com.example.odziezowy.Repository.CategoriesRepository;
import com.example.odziezowy.Service.CategoriesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/categories")
public class CategoriesController {

    private CategoriesRepository categoriesRepository;
    private CategoriesService categoriesService;

    @Autowired
    public CategoriesController(CategoriesRepository categoriesRepository, CategoriesService categoriesService) {
        this.categoriesRepository = categoriesRepository;
        this.categoriesService = categoriesService;
    }


    @GetMapping()
    public Page<Categories> getAll(@RequestParam(defaultValue = "0") Integer pageNo,
                                   @RequestParam(defaultValue = "10") Integer pageSize) {

        Pageable paging = PageRequest.of(pageNo, pageSize);
        return categoriesRepository.findAll(paging);

    }

    @GetMapping("/main/{number}")
    public List<Categories> getForMainPage(@PathVariable(value = "number") int number) {
        return categoriesService.getForMainPageService(number);
    }
}
