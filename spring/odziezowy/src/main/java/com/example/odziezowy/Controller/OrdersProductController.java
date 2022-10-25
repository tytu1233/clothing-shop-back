package com.example.odziezowy.Controller;

import com.example.odziezowy.Model.OrdersProducts;
import com.example.odziezowy.Repository.OrdersProductRepository;
import com.example.odziezowy.Repository.OrdersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ordersproduct")
public class OrdersProductController {

    private OrdersProductRepository ordersProductRepository;
    private OrdersRepository ordersRepository;

    @Autowired
    public OrdersProductController(OrdersProductRepository ordersProductRepository, OrdersRepository ordersRepository) {
        this.ordersProductRepository = ordersProductRepository;
        this.ordersRepository = ordersRepository;
    }

    @GetMapping()
    public Page<OrdersProducts> getAll(@RequestParam(defaultValue = "0") Integer pageNo,
                                       @RequestParam(defaultValue = "10") Integer pageSize) {
        Pageable paging = PageRequest.of(pageNo, pageSize);
        return ordersProductRepository.findAll(paging);
    }

    @GetMapping("/{id}")
    public List<OrdersProducts> getAllByOrderId(@PathVariable(value = "id") Long id) {

        return ordersProductRepository.findAllByOrders(ordersRepository.findById(id));
    }

}
