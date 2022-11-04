package com.example.odziezowy.Service;

import com.example.odziezowy.Model.Products;
import com.example.odziezowy.Model.Sizes;
import com.example.odziezowy.Repository.ProductsRepository;
import com.example.odziezowy.Repository.SizesRepository;
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
    private final SizesRepository sizesRepository;

    @Autowired
    public ProductsService(ProductsRepository productsRepository, SizesRepository sizesRepository) {
        this.productsRepository = productsRepository;
        this.sizesRepository = sizesRepository;
    }


    public Page<Products> getFilteredData(List<String> brands, List<String> sizes, Double min, Double max, Integer pageNo, Integer pageSize) {
        System.out.println(sizes);
        Pageable paging = PageRequest.of(pageNo, pageSize);
        if(!(brands.isEmpty()) && !(sizes.isEmpty()) && !(min<0) && !(max<0))
           return productsRepository.findDistinctByNameInAndSizesesInAndPriceBetween(paging, brands, sizesRepository.findAllBySizeNameIn(sizes), min, max);
        if(!(brands.isEmpty()) && !(min<0) && !(max<0))
            return productsRepository.findAllByNameInAndPriceBetween(paging, brands, min, max);
        if(!(sizes.isEmpty()) && !(min<0) && !(max<0))
            return productsRepository.findDistinctBySizesesInAndPriceBetween(paging, sizesRepository.findAllBySizeNameIn(sizes), min, max);
        if(min>0 && max>0)
            return productsRepository.findAllByPriceBetween(paging, min, max);
        if(!brands.isEmpty())
            return productsRepository.findAllByNameIn(paging, brands);
        if(!sizes.isEmpty()) {
            return productsRepository.findDistinctBySizesesIn(paging, sizesRepository.findAllBySizeNameIn(sizes));
        }
        return productsRepository.findAll(paging);
    }


}
