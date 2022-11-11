package com.example.odziezowy.Service;

import com.example.odziezowy.Exception.ResourceNotFoundException;
import com.example.odziezowy.Model.Orders;
import com.example.odziezowy.Model.Users;
import com.example.odziezowy.Repository.OrdersRepository;
import com.example.odziezowy.Repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class OrdersService {

    private OrdersRepository ordersRepository;
    private UsersRepository usersRepository;

    @Autowired
    public OrdersService(OrdersRepository ordersRepository, UsersRepository usersRepository) {
        this.ordersRepository = ordersRepository;
        this.usersRepository = usersRepository;
    }

    public Page<Orders> getAllSerivce(Integer pageNo, Integer pageSize) {
        Pageable paging = PageRequest.of(pageNo, pageSize);
        return ordersRepository.findAll(paging);
    }

    public List<Orders> getOrdersByUserIdService(@PathVariable(value="id") Long id) {
        Users users = usersRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not exist with id: " + id));
        return ordersRepository.findAllByUsers(users);
    }

    public ResponseEntity<Long> createOrderService(Long id) {
        LocalDate date = LocalDate.now();
        Users users = usersRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not exist with id: " + id));
        Orders orders = new Orders();
        orders.setUsers(users);
        orders.setFinalPrice(0.00);
        orders.setStatus("W REALIZACJI");
        orders.setDate(date);
        ordersRepository.save(orders);
        return new ResponseEntity<>(orders.getIdOrders(), HttpStatus.CREATED);
    }

    public ResponseEntity<Long> updateOrderService(long id, String price) {
        Orders updateOrders = ordersRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order not exist with id: " + id));
        updateOrders.setFinalPrice(Double.valueOf(price));

        ordersRepository.save(updateOrders);

        return new ResponseEntity<>(id, HttpStatus.CREATED);
    }


}
