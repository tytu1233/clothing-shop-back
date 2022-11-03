package com.example.odziezowy.Controller;

import com.example.odziezowy.Model.Orders;
import com.example.odziezowy.Service.OrdersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/orders")
public class OrdersController {

    private OrdersService ordersService;

    @Autowired
    public OrdersController(OrdersService ordersService) {
        this.ordersService = ordersService;
    }

    @GetMapping
    public Page<Orders> getAll(@RequestParam(defaultValue = "0") Integer pageNo,
                               @RequestParam(defaultValue = "10") Integer pageSize) {

        return ordersService.getAllSerivce(pageNo, pageSize);
    }

    @GetMapping("/{id}")
    public List<Orders> getOrdersByUserId(@PathVariable(value="id") Long id) {
        return ordersService.getOrdersByUserIdService(id);
    }

    @PostMapping("/{id}")
    public ResponseEntity<Long> createOrder(@PathVariable(value = "id") Long id) {
        return ordersService.createOrderService(id);
    }

    @PutMapping("/{id}/{price}")
    public ResponseEntity<Long> updateOrder(@PathVariable long id, @PathVariable String price) {
        return ordersService.updateOrderService(id, price);
    }
}
