package com.example.odziezowy.Repository;

import com.example.odziezowy.Model.Products;
import com.example.odziezowy.Model.Sizes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface SizesRepository extends JpaRepository<Sizes, Long> {

    List<Sizes> findAllByProductsSizes(Products products);

    List<Sizes> findAllBySizeNameIn(List<String> size);
    @Transactional
    @Modifying
    @Query(value = "UPDATE Sizes s set s.amount = s.amount - :quantity where s.idSize = :id")
    void updateQunatity(Long quantity, Long id);

    @Transactional
    @Modifying
    @Query(value = "UPDATE Sizes s set s.amount = s.amount + :quantity where s.idSize = :id")
    void increaseQunatity(Long quantity, Long id);
    @Query(value = "SELECT * FROM sizes GROUP BY size_name", nativeQuery = true)
    List<Sizes> findNamesDistinct();

    List<Sizes> findAllBySizeNameInAndProductsSizesIn(List<String> sizes, List<Products> products);

    Sizes findBySizeNameAndProductsSizes(String sizeName, Products products);
}
