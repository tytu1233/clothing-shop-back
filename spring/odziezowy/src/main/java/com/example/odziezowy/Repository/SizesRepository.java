package com.example.odziezowy.Repository;

import com.example.odziezowy.Model.Products;
import com.example.odziezowy.Model.Sizes;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SizesRepository extends JpaRepository<Sizes, Long> {

    List<Sizes> findAllByProductsSizes(Products products);

}
