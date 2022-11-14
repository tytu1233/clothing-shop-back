package com.example.odziezowy.Repository;

import com.example.odziezowy.DTOS.OrdersDtoMonthly;
import com.example.odziezowy.Model.Orders;
import com.example.odziezowy.Model.Users;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OrdersRepository extends JpaRepository<Orders, Long> {

    Page<Orders> findAll(Pageable pageable);
    Orders findByIdOrders(Long id);

    List<Orders> findAllByUsers(Users users);
    @Query(value = "SELECT new com.example.odziezowy.DTOS.OrdersDtoMonthly(SUM(o.finalPrice), MONTH(o.date)) FROM Orders o GROUP BY MONTH(o.date)")
    List<OrdersDtoMonthly> findMonthlyPrices();

}
