package com.example.odziezowy.Controller;

import com.example.odziezowy.Model.Products;
import com.example.odziezowy.Model.Sizes;
import com.example.odziezowy.Repository.ProductsRepository;
import com.example.odziezowy.Repository.SizesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/sizes")
public class SizesController {

    private final SizesRepository sizesRepository;
    private final ProductsRepository productsRepository;

    @Autowired
    public SizesController(SizesRepository sizesRepository, ProductsRepository productsRepository) {
        this.sizesRepository = sizesRepository;
        this.productsRepository = productsRepository;
    }

    @GetMapping("/{id}")
    List<Sizes> getAllSizesForProduct(@PathVariable(value = "id") Long id) {
        Products products = productsRepository.findById(id).get();
        return sizesRepository.findAllByProductsSizes(products);
    }

}
