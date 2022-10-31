package com.example.odziezowy.Controller;

import com.example.odziezowy.DTOS.ProductsDto;
import com.example.odziezowy.Model.Orders;
import com.example.odziezowy.Model.OrdersProducts;
import com.example.odziezowy.Model.Products;
import com.example.odziezowy.Repository.OrdersProductRepository;
import com.example.odziezowy.Repository.OrdersRepository;
import com.example.odziezowy.Repository.ProductsRepository;
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

    private OrdersProductRepository ordersProductRepository;
    private OrdersRepository ordersRepository;
    private ProductsRepository productsRepository;

    @Autowired
    public OrdersProductController(OrdersProductRepository ordersProductRepository, OrdersRepository ordersRepository, ProductsRepository productsRepository) {
        this.ordersProductRepository = ordersProductRepository;
        this.ordersRepository = ordersRepository;
        this.productsRepository = productsRepository;
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

    @PostMapping("/{order_id}")
    public ResponseEntity<OrdersProducts> createOrderProduct(@PathVariable(value="order_id") Long order_id, @RequestBody ProductsDto productsDto) {
        Orders orders = ordersRepository.findByIdOrders(order_id);
        OrdersProducts ordersProducts = new OrdersProducts();
        productsRepository.findById(productsDto.getId()).map(products1 -> {
            ordersProducts.setProducts(products1);
            ordersProducts.setOrders(orders);
            ordersProducts.setQuantity(productsDto.getQuantity());
            return ordersProductRepository.save(ordersProducts);
        });

        return new ResponseEntity<>(ordersProducts, HttpStatus.CREATED);
    }

}
