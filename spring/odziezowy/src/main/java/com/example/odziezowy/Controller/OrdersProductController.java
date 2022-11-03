package com.example.odziezowy.Controller;

import com.example.odziezowy.DTOS.ProductsDto;
import com.example.odziezowy.Model.Orders;
import com.example.odziezowy.Model.OrdersProducts;
import com.example.odziezowy.Model.Products;
import com.example.odziezowy.Repository.OrdersProductRepository;
import com.example.odziezowy.Repository.OrdersRepository;
import com.example.odziezowy.Repository.ProductsRepository;
import com.example.odziezowy.Service.OrdersProductsService;
import org.aspectj.weaver.ast.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

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
