package com.example.odziezowy.Repository;

import com.example.odziezowy.Model.Products;
import com.example.odziezowy.Model.Sizes;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
public interface ProductsRepository extends JpaRepository<Products, Long> {
    Page<Products> findAll(Pageable pageable);
    List<Products> findAllByNameContains(String name);
    Page<Products> findDistinctByNameInAndSizesesInAndPriceBetween(Pageable pageable, List<String> brands, List<Sizes> sizes, Double min, Double max);
    Page<Products> findDistinctBySizesesInAndPriceBetween(Pageable pageable, List<Sizes> sizes, Double min, Double max);
    Page<Products> findAllByNameInAndPriceBetween(Pageable pageable, List<String> brands, Double min, Double max);
    Page<Products> findAllByPriceBetween(Pageable pageable, Double min, Double max);
    Page<Products> findAllByNameIn(Pageable pageable, List<String> brands);
    Page<Products> findDistinctBySizesesIn(Pageable pageable, List<Sizes> sizes);

}
