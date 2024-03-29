package com.example.odziezowy.Service;



import com.example.odziezowy.DTOS.ProductsDto;
import com.example.odziezowy.Exception.ResourceNotFoundException;
import com.example.odziezowy.Model.*;
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

import java.util.List;

@Service
public class OrdersProductsService {

    private final OrdersRepository ordersRepository;
    private final UsersRepository usersRepository;
    private final OrdersProductRepository ordersProductRepository;
    private final ProductsRepository productsRepository;

    private final SizesService sizesService;

    @Autowired
    public OrdersProductsService(OrdersRepository ordersRepository, UsersRepository usersRepository, OrdersProductRepository ordersProductRepository, ProductsRepository productsRepository, SizesService sizesService) {
        this.ordersRepository = ordersRepository;
        this.usersRepository = usersRepository;
        this.productsRepository = productsRepository;
        this.sizesService = sizesService;
        this.ordersProductRepository = ordersProductRepository;
    }


    public List<OrdersProducts> getAllByUsersAndOrders(Long id) {
        Users users = usersRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not exist with id: " + id));
       return ordersProductRepository.findAllByOrdersIn(ordersRepository.findAllByUsers(users));
    }

    public ResponseEntity<OrdersProducts> createOrderProductService(Long order_id, ProductsDto productsDto) {
        System.out.println(productsDto.getSize());
        Orders orders = ordersRepository.findByIdOrders(order_id);
        OrdersProducts ordersProducts = new OrdersProducts();
        String id = productsDto.getId().replaceAll("[^0-9.]", "");
        Products products = productsRepository.findById(Long.valueOf(id)).get();
        ordersProducts.setSize(productsDto.getSize());
        ordersProducts.setProducts(products);
        ordersProducts.setOrders(orders);
        ordersProducts.setQuantity(productsDto.getQuantity());
        ordersProductRepository.save(ordersProducts);
        Sizes sizes = sizesService.findByProductAndSizeService(productsDto.getSize(), products);
        sizesService.updateQuantityService((long) productsDto.getQuantity(), sizes.getIdSize());

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
