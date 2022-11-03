package com.example.odziezowy.Service;



import com.example.odziezowy.DTOS.ProductsDto;
import com.example.odziezowy.Exception.ResourceNotFoundException;
import com.example.odziezowy.Model.Orders;
import com.example.odziezowy.Model.OrdersProducts;
import com.example.odziezowy.Model.Users;
import com.example.odziezowy.Repository.OrdersProductRepository;
import com.example.odziezowy.Repository.OrdersRepository;
import com.example.odziezowy.Repository.ProductsRepository;
import com.example.odziezowy.Repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;


import java.util.List;

@Service
public class OrdersProductsService {

    private final OrdersRepository ordersRepository;
    private final UsersRepository usersRepository;
    private final OrdersProductRepository ordersProductRepository;
    private final ProductsRepository productsRepository;

    @Autowired
    public OrdersProductsService(OrdersRepository ordersRepository, UsersRepository usersRepository, OrdersProductRepository ordersProductRepository, ProductsRepository productsRepository) {
        this.ordersRepository = ordersRepository;
        this.usersRepository = usersRepository;
        this.productsRepository = productsRepository;
        this.ordersProductRepository = ordersProductRepository;
    }


    public List<OrdersProducts> getAllByUsersAndOrders(Long id) {
        Users users = usersRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not exist with id: " + id));
       return ordersProductRepository.findAllByOrdersIn(ordersRepository.findAllByUsers(users));
    }

    public ResponseEntity<OrdersProducts> createOrderProductService(Long order_id, ProductsDto productsDto) {
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

    public Page<OrdersProducts> getAllOrdersProductsService(Integer pageNo, Integer pageSize) {
        Pageable paging = PageRequest.of(pageNo, pageSize);
        return ordersProductRepository.findAll(paging);
    }

    public List<OrdersProducts> getAllByOrderIdService(Long id) {

        return ordersProductRepository.findAllByOrders(ordersRepository.findById(id));
    }
}
