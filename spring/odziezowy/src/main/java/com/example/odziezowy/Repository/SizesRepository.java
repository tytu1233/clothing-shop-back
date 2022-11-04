package com.example.odziezowy.Repository;

import com.example.odziezowy.Model.Products;
import com.example.odziezowy.Model.Sizes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SizesRepository extends JpaRepository<Sizes, Long> {

    List<Sizes> findAllByProductsSizes(Products products);

    List<Sizes> findAllBySizeNameIn(List<String> size);
    @Query(value = "SELECT * FROM sizes GROUP BY size_name", nativeQuery = true)
    List<Sizes> findNamesDistinct();
}
