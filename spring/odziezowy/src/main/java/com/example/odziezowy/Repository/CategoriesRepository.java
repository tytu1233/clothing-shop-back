package com.example.odziezowy.Repository;

import com.example.odziezowy.Model.Categories;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoriesRepository extends JpaRepository<Categories, Long> {
    Page<Categories> findAll(Pageable pageable);
}
