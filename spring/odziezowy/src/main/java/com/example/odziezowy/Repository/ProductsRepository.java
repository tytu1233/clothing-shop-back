package com.example.odziezowy.Repository;

import com.example.odziezowy.Model.Categories;
import com.example.odziezowy.Model.Products;
import com.example.odziezowy.Model.Sizes;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
public interface ProductsRepository extends JpaRepository<Products, Long> {
    Page<Products> findAll(Pageable pageable);
    List<Products> findAllByNameContains(String name);
    @Query(value = "SELECT DISTINCT * FROM products GROUP BY brand" , nativeQuery = true)
    List<Products> findDistinctByName();
    Page<Products> findDistinctByBrandInAndSizesesInAndPriceBetween(Pageable pageable, List<String> brands, List<Sizes> sizes, Double min, Double max);
    Page<Products> findDistinctBySizesesInAndCategoriesInAndPriceBetween(Pageable pageable, List<Sizes> sizes, List<Categories> categories, Double min, Double max);
    Page<Products> findDistinctByBrandInAndCategoriesInAndPriceBetween(Pageable pageable, List<String> brands, List<Categories> categories, Double min, Double max);
    Page<Products> findDistinctByBrandInAndSizesesInAndCategoriesInAndPriceBetween(Pageable pageable, List<String> brands, List<Sizes> sizes, List<Categories> categories, Double min, Double max);
    Page<Products> findDistinctBySizesesInAndPriceBetween(Pageable pageable, List<Sizes> sizes, Double min, Double max);
    Page<Products> findAllByBrandInAndPriceBetween(Pageable pageable, List<String> brands, Double min, Double max);
    Page<Products> findAllByPriceBetween(Pageable pageable, Double min, Double max);
    Page<Products> findAllByBrandIn(Pageable pageable, List<String> brands);
    Page<Products> findDistinctBySizesesIn(Pageable pageable, List<Sizes> sizes);
    Page<Products> findAllByCategoriesIn(Pageable pageable, List<Categories> categories);
    Page<Products> findAllByCategoriesInAndPriceBetween(Pageable pageable, List<Categories> categories, Double min, Double max);

}
