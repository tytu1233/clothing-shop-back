package com.example.odziezowy.Service;

import com.example.odziezowy.DTOS.ProductsDto;
import com.example.odziezowy.Exception.ResourceNotFoundException;
import com.example.odziezowy.Model.Categories;
import com.example.odziezowy.Model.Products;
import com.example.odziezowy.Model.Users;
import com.example.odziezowy.Repository.CategoriesRepository;
import com.example.odziezowy.Repository.ProductsRepository;
import com.example.odziezowy.Repository.SizesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;

@Service
public class ProductsService {

    private final ProductsRepository productsRepository;
    private final SizesRepository sizesRepository;
    private final CategoriesRepository categoriesRepository;

    @Autowired
    public ProductsService(ProductsRepository productsRepository, SizesRepository sizesRepository, CategoriesRepository categoriesRepository) {
        this.productsRepository = productsRepository;
        this.sizesRepository = sizesRepository;
        this.categoriesRepository = categoriesRepository;
    }


    public Page<Products> getFilteredData(List<String> brands, List<String> sizes, List<String> categories, Double min, Double max, Integer pageNo, Integer pageSize) {
        System.out.println(brands);
        Pageable paging = PageRequest.of(pageNo, pageSize);
        if(!(categories.isEmpty()) && !(brands.isEmpty()) && !(sizes.isEmpty()) && !(min<0) && !(max<0))
            return productsRepository.findDistinctByBrandInAndSizesesInAndCategoriesInAndPriceBetween(paging, brands, sizesRepository.findAllBySizeNameIn(sizes), categoriesRepository.findAllByCategoryNameIn(categories), min, max);
        if(!(brands.isEmpty()) && !(categories.isEmpty()) && !(min<0) && !(max<0))
            return productsRepository.findDistinctByBrandInAndCategoriesInAndPriceBetween(paging, brands, categoriesRepository.findAllByCategoryNameIn(categories), min, max);
        if(!(brands.isEmpty()) && !(sizes.isEmpty()) && !(min<0) && !(max<0))
            return productsRepository.findDistinctByBrandInAndSizesesInAndPriceBetween(paging, brands, sizesRepository.findAllBySizeNameIn(sizes), min, max);
        if(!(sizes.isEmpty()) && !(categories.isEmpty()) && !(min<0) && !(max<0))
            return productsRepository.findDistinctBySizesesInAndCategoriesInAndPriceBetween(paging, sizesRepository.findAllBySizeNameIn(sizes), categoriesRepository.findAllByCategoryNameIn(categories), min, max);
        if(!(brands.isEmpty()) && !(min<0) && !(max<0))
            return productsRepository.findAllByBrandInAndPriceBetween(paging, brands, min, max);
        if(!(sizes.isEmpty()) && !(min<0) && !(max<0))
            return productsRepository.findDistinctBySizesesInAndPriceBetween(paging, sizesRepository.findAllBySizeNameIn(sizes), min, max);
        if(!(categories.isEmpty()) && !(min<0) && !(max<0))
            return productsRepository.findAllByCategoriesInAndPriceBetween(paging, categoriesRepository.findAllByCategoryNameIn(categories), min, max);
        if(min>0 && max>0)
            return productsRepository.findAllByPriceBetween(paging, min, max);
        if(!brands.isEmpty())
            return productsRepository.findAllByBrandIn(paging, brands);
        if(!sizes.isEmpty()) {
            return productsRepository.findDistinctBySizesesIn(paging, sizesRepository.findAllBySizeNameIn(sizes));
        }
        if(!categories.isEmpty()) {
            return productsRepository.findAllByCategoriesIn(paging, categoriesRepository.findAllByCategoryNameIn(categories));
        }
        return productsRepository.findAll(paging);
    }

    public List<Products> findAllProducersService() {
        return productsRepository.findDistinctByName();
    }
    public List<Products> getRecommendedProductsService() {
        List<Products> randomCategories = new ArrayList<>();
        List<Products> copy = new ArrayList<>(productsRepository.findAll());

        SecureRandom rand = new SecureRandom();
        for (int i = 0; i < Math.min(10, productsRepository.findAll().size()); i++) {
            randomCategories.add( copy.remove( rand.nextInt( copy.size() ) ));
        }

        return randomCategories;
    }

    public ResponseEntity<String> deleteProductService(Long id) {
        Products products = productsRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Can not find user" + id));

        productsRepository.delete(products);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    public ResponseEntity<String> createProdcutService(String name, ProductsDto productsDto) {
        Categories categories = categoriesRepository.findByCategoryName(name);
        Products products = new Products();
        products.setCategories(categories);
        products.setName(productsDto.getName());
        products.setBrand(productsDto.getBrand());
        products.setImage(productsDto.getImage());
        products.setPrice(productsDto.getPrice());
        products.setDescription(productsDto.getDescription());
        productsRepository.save(products);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
