package com.example.odziezowy.Repository;

import com.example.odziezowy.Model.Orders;
import com.example.odziezowy.Model.OrdersProducts;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface OrdersProductRepository extends JpaRepository<OrdersProducts, Long> {

    Page<OrdersProducts> findAll(Pageable pageable);
    List<OrdersProducts> findAllByOrders(Optional<Orders> orders);
}
