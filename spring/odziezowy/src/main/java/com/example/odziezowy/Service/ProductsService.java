package com.example.odziezowy.Service;

import com.example.odziezowy.Model.Products;
import com.example.odziezowy.Repository.ProductsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductsService {

    private final ProductsRepository productsRepository;

    @Autowired
    public ProductsService(ProductsRepository productsRepository) {
        this.productsRepository = productsRepository;
    }


    public Page<Products> getFilteredData(List<String> brands, List<Double> prices, Integer pageNo, Integer pageSize) {
        System.out.println(brands);
        Pageable paging = PageRequest.of(pageNo, pageSize);
        if(!(brands.isEmpty()) && !(prices.isEmpty()))
           return productsRepository.findAllByNameInAndPriceIn(paging, brands, prices);
        if(!brands.isEmpty())
            return productsRepository.findAllByNameIn(paging, brands);
        return productsRepository.findAll(paging);
    }


}
