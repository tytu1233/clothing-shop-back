package com.example.odziezowy.Service;

import com.example.odziezowy.DTOS.ProductsDto;
import com.example.odziezowy.Model.Products;
import com.example.odziezowy.Model.Sizes;
import com.example.odziezowy.Repository.ProductsRepository;
import com.example.odziezowy.Repository.SizesRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SizesService {

    private final SizesRepository sizesRepository;
    private final ProductsRepository productsRepository;

    public SizesService(SizesRepository sizesRepository, ProductsRepository productsRepository) {
        this.sizesRepository = sizesRepository;
        this.productsRepository = productsRepository;
    }


    public List<Sizes> getAllSizesForProductService(Long id) {
        Products products = productsRepository.findById(id).get();
        return sizesRepository.findAllByProductsSizes(products);
    }

    public List<Sizes> getAllNamesService() {
        return sizesRepository.findNamesDistinct();
    }

    public void updateQuantityService(Long quantity, Long id) {
        sizesRepository.updateQunatity(quantity, id);
    }

    public Sizes findByProductAndSizeService(String sizeName, Products products) {
        return sizesRepository.findBySizeNameAndProductsSizes(sizeName, products);
    }

    public ResponseEntity<String> checkQuantityForItemService(ProductsDto productsDto) {
        String id = productsDto.getId().replaceAll("[^0-9.]", "");
        Products products = productsRepository.findById(Long.valueOf(id)).get();
        Sizes sizes = sizesRepository.findBySizeNameAndProductsSizes(productsDto.getSize(), products);
        if(sizes.getAmount() < productsDto.getQuantity()) {
            return new ResponseEntity<>("zle", HttpStatus.FORBIDDEN);
        }
        return new ResponseEntity<>("dobrze", HttpStatus.OK);
    }

    public List<Sizes> checkQuantityService(List<String> ids, List <String> sizesNames) {
        List<String> result = new ArrayList<>();
        List<Products> resultProducts = new ArrayList<>();
        for (String s : ids) {
            result.add(s.replaceAll("[^0-9.]", ""));
        }

        result.forEach((n) -> {
            resultProducts.add(productsRepository.findById(Long.valueOf(n)).get());
        });

        return sizesRepository.findAllBySizeNameInAndProductsSizesIn(sizesNames, resultProducts);
    }

}
