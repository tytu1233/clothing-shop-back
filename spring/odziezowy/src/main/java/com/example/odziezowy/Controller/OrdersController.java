package com.example.odziezowy.Controller;

import com.example.odziezowy.DTOS.OrdersDto;
import com.example.odziezowy.DTOS.UsersDto;
import com.example.odziezowy.Exception.ResourceNotFoundException;
import com.example.odziezowy.Model.Orders;
import com.example.odziezowy.Model.Roles;
import com.example.odziezowy.Model.Users;
import com.example.odziezowy.Repository.OrdersRepository;
import com.example.odziezowy.Repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/orders")
public class OrdersController {

    private OrdersRepository ordersRepository;
    private UsersRepository usersRepository;

    @Autowired
    public OrdersController(OrdersRepository ordersRepository, UsersRepository usersRepository) {
        this.ordersRepository = ordersRepository;
        this.usersRepository = usersRepository;
    }

    @GetMapping
    public Page<Orders> getAll(@RequestParam(defaultValue = "0") Integer pageNo,
                               @RequestParam(defaultValue = "10") Integer pageSize) {

        Pageable paging = PageRequest.of(pageNo, pageSize);
        return ordersRepository.findAll(paging);

    }


    @GetMapping("/{id}")
    public List<Orders> getOrdersByUserId(@PathVariable(value="id") Long id) {
        Users users = usersRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not exist with id: " + id));;
        return ordersRepository.findAllByUsers(users);
    }

    @PostMapping("/{id}")
    public ResponseEntity<Long> createOrder(@PathVariable(value = "id") Long id) {
        Users users = usersRepository.findById(id).get();
        Orders orders = new Orders();
        orders.setUsers(users);
        orders.setFinal_price(0.00);
        ordersRepository.save(orders);
        return new ResponseEntity<>(orders.getIdOrders(), HttpStatus.CREATED);
    }

    @PutMapping("/{id}/{price}")
    public ResponseEntity<Long> updateOrder(@PathVariable long id, @PathVariable String price) {
        Orders updateOrders = ordersRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order not exist with id: " + id));
        updateOrders.setFinal_price(Double.valueOf(price));

        ordersRepository.save(updateOrders);

        return new ResponseEntity<>(id, HttpStatus.CREATED);
    }

}
