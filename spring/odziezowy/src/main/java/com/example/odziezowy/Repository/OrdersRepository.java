package com.example.odziezowy.Repository;

import com.example.odziezowy.Model.Orders;
import com.example.odziezowy.Model.OrdersProducts;
import com.example.odziezowy.Model.Users;
import org.aspectj.weaver.ast.Or;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;

public interface OrdersRepository extends JpaRepository<Orders, Long> {

    Page<Orders> findAll(Pageable pageable);
    Orders findByIdOrders(Long id);

    List<Orders> findAllByUsers(Users users);

}
