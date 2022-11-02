package com.example.odziezowy.Service;



import com.example.odziezowy.Exception.ResourceNotFoundException;
import com.example.odziezowy.Model.Orders;
import com.example.odziezowy.Model.OrdersProducts;
import com.example.odziezowy.Model.Users;
import com.example.odziezowy.Repository.OrdersProductRepository;
import com.example.odziezowy.Repository.OrdersRepository;
import com.example.odziezowy.Repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
public class OrdersProductsService {

    private final OrdersRepository ordersRepository;
    private final UsersRepository usersRepository;
    private final OrdersProductRepository ordersProductRepository;

    @Autowired
    public OrdersProductsService(OrdersRepository ordersRepository, UsersRepository usersRepository, OrdersProductRepository ordersProductRepository) {
        this.ordersRepository = ordersRepository;
        this.usersRepository = usersRepository;
        this.ordersProductRepository = ordersProductRepository;
    }


    public List<OrdersProducts> getAllByUsersAndOrders(Long id) {
        Users users = usersRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not exist with id: " + id));
       return ordersProductRepository.findAllByOrdersIn(ordersRepository.findAllByUsers(users));
    }
}
