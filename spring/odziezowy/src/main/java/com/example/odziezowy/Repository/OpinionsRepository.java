package com.example.odziezowy.Repository;

import com.example.odziezowy.Model.Opinions;
import com.example.odziezowy.Model.Products;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


public interface OpinionsRepository extends JpaRepository<Opinions, Long> {

    Page<Opinions> findAllByProductsId(Pageable pageable, Products products);
}
