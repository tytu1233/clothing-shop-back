package com.example.odziezowy.Controller;

import com.example.odziezowy.DTOS.ProductsDto;
import com.example.odziezowy.Model.OrdersProducts;
import com.example.odziezowy.Service.OrdersProductsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/ordersproduct")
public class OrdersProductController {

    private final OrdersProductsService ordersProductsService;
    @Autowired
    public OrdersProductController(OrdersProductsService ordersProductsService) {
        this.ordersProductsService = ordersProductsService;
    }

    @GetMapping()
    public Page<OrdersProducts> getAllOrdersProducts(@RequestParam(defaultValue = "0") Integer pageNo,
                                       @RequestParam(defaultValue = "10") Integer pageSize) {
        return ordersProductsService.getAllOrdersProductsService(pageNo, pageSize);
    }

    @GetMapping("/{id}")
    public List<OrdersProducts> getAllByOrderId(@PathVariable(value = "id") Long id) {
        return ordersProductsService.getAllByOrderIdService(id);
    }

    @GetMapping("/users/{id}")
    public List<OrdersProducts> getAllByUsersId(@PathVariable(value = "id") Long id) {

        return ordersProductsService.getAllByUsersAndOrders(id);
    }

    @PostMapping("/{order_id}")
    public ResponseEntity<OrdersProducts> createOrderProduct(@PathVariable(value="order_id") Long order_id, @RequestBody ProductsDto productsDto) {
        return ordersProductsService.createOrderProductService(order_id, productsDto);
    }

}
