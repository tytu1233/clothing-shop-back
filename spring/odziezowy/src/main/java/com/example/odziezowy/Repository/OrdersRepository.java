package com.example.odziezowy.Repository;

import com.example.odziezowy.Model.Orders;
import org.aspectj.weaver.ast.Or;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrdersRepository extends JpaRepository<Orders, Long> {

    Page<Orders> findAll(Pageable pageable);
}
