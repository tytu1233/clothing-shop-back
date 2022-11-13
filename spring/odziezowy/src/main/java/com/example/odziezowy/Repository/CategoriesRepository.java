package com.example.odziezowy.Repository;

import com.example.odziezowy.Model.Categories;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoriesRepository extends JpaRepository<Categories, Long> {
    Page<Categories> findAll(Pageable pageable);

    List<Categories> findAllByCategoryNameIn(List<String> categories);

    Categories findByCategoryName(String name);

}
