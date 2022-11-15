package com.example.odziezowy.Controller;


import com.example.odziezowy.DTOS.ProductsDto;
import com.example.odziezowy.Model.Products;
import com.example.odziezowy.Repository.ProductsRepository;
import com.example.odziezowy.Service.ProductsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin("*")
@RequestMapping("/products")
public class ProductsController {

    private final ProductsRepository productsRepository;
    private final ProductsService productsService;

    @Autowired
    public ProductsController(ProductsRepository productsRepository, ProductsService productsService) {
        this.productsRepository = productsRepository;
        this.productsService = productsService;
    }

    @GetMapping
    public Page<Products> getAll(@RequestParam(defaultValue = "0") Integer pageNo,
                                 @RequestParam(defaultValue = "12") Integer pageSize) {
        Pageable paging = PageRequest.of(pageNo, pageSize);
        return productsRepository.findAll(paging);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProductById(@PathVariable(value = "id") Long id) {
        return productsService.deleteProductService(id);
    }


    @GetMapping("/search/{name}")
    public List<Products> getAllByName(@PathVariable(value = "name") String name) {
        return productsRepository.findAllByNameContains(name);
    }

    @GetMapping("/{id}")
    public Optional<Products> getById(@PathVariable(value = "id") Long id) {
        return productsRepository.findById(id);
    }

    @GetMapping("/filter")
    public Page<Products> filterData(@RequestParam(defaultValue = "0") Integer pageNo,
                                     @RequestParam(defaultValue = "12") Integer pageSize,
                                     @RequestParam List<String> brands,
                                     @RequestParam List<String> sizes,
                                     @RequestParam List<String> categories,
                                     @RequestParam Double minPrice,
                                     @RequestParam Double maxPrice) {
        return productsService.getFilteredData(brands, sizes, categories, minPrice, maxPrice, pageNo, pageSize);
    }

    @GetMapping("/producers")
    public List<Products> findAllProducers() {
        return productsService.findAllProducersService();
    }

    @GetMapping("/recommended")
    public List<Products> getRecommendedProducts() {
        return productsService.getRecommendedProductsService();
    }

    @PostMapping("/{name}")
    public ResponseEntity<String> createProduct(@PathVariable(value = "name") String name, @RequestBody ProductsDto productsDto) {
        return productsService.createProdcutService(name, productsDto);
    }

}
