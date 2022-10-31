package com.example.odziezowy.Repository;

import com.example.odziezowy.Model.Products;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
public interface ProductsRepository extends JpaRepository<Products, Long> {
    Page<Products> findAll(Pageable pageable);
    List<Products> findAllByNameContains(String name);
    Page<Products> findAllByNameInAndPriceIn(Pageable pageable, List<String> brands, List<Double> price);
    Page<Products> findAllByNameIn(Pageable pageable, List<String> brands);
}
