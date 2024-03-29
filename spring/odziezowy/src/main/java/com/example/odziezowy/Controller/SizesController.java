package com.example.odziezowy.Controller;

import com.example.odziezowy.DTOS.ProductsDto;
import com.example.odziezowy.Model.Sizes;
import com.example.odziezowy.Service.SizesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/sizes")
public class SizesController {

    private final SizesService sizesService;

    @Autowired
    public SizesController(SizesService sizesService) {
        this.sizesService = sizesService;
    }

    @GetMapping("/{id}")
    public List<Sizes> getAllSizesForProduct(@PathVariable(value = "id") Long id) {
        return sizesService.getAllSizesForProductService(id);
    }
    @GetMapping("/names")
    public List<Sizes> getAllNames() {
        return sizesService.getAllNamesService();
    }

    @GetMapping("/check")
    public List<Sizes> checkQuantity(@RequestParam List<String> ids, @RequestParam List <String> sizesNames) {
        return sizesService.checkQuantityService(ids, sizesNames);
    }

    @PostMapping("/quantity")
    public ResponseEntity<String> checkQuantityForItem(@RequestBody ProductsDto productsDto) {
        return sizesService.checkQuantityForItemService(productsDto);
    }



}
