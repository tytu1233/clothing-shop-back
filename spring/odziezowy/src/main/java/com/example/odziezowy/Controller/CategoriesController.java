package com.example.odziezowy.Controller;

import com.example.odziezowy.Model.Categories;
import com.example.odziezowy.Service.CategoriesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/categories")
public class CategoriesController {
    private final CategoriesService categoriesService;

    @Autowired
    public CategoriesController(CategoriesService categoriesService) {
        this.categoriesService = categoriesService;
    }

    @GetMapping
    public Page<Categories> getAllCategories(@RequestParam(defaultValue = "0") Integer pageNo,
                                   @RequestParam(defaultValue = "10") Integer pageSize) {
        return categoriesService.getAllCategoriesService(pageNo, pageSize);
    }

    @GetMapping("/main/{number}")
    public List<Categories> getForMainPage(@PathVariable(value = "number") int number) {
        return categoriesService.getForMainPageService(number);
    }
}
