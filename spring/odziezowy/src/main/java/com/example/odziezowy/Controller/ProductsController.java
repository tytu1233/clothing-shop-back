package com.example.odziezowy.Controller;


import com.example.odziezowy.Model.Products;
import com.example.odziezowy.Repository.ProductsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/products")
public class ProductsController {

    private ProductsRepository productsRepository;

    @Autowired
    public ProductsController(ProductsRepository productsRepository) {
        this.productsRepository = productsRepository;
    }

    @GetMapping
    public Page<Products> getAll(@RequestParam(defaultValue = "0") Integer pageNo,
                                 @RequestParam(defaultValue = "12") Integer pageSize) {
        Pageable paging = PageRequest.of(pageNo, pageSize);
        return productsRepository.findAll(paging);
    }

    @GetMapping("/{name}")
    public List<Products> getAllByName(@PathVariable(value = "name") String name) {
        return productsRepository.findAllByNameContains(name);
    }

}
