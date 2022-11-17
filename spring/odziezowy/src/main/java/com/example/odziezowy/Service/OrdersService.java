package com.example.odziezowy.Service;

import com.example.odziezowy.DTOS.OrdersDto;
import com.example.odziezowy.DTOS.OrdersDtoMonthly;
import com.example.odziezowy.Exception.ResourceNotFoundException;
import com.example.odziezowy.Model.Orders;
import com.example.odziezowy.Model.Products;
import com.example.odziezowy.Model.Sizes;
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

import javax.mail.MessagingException;
import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.Optional;

@Service
public class OrdersService {

    private final OrdersRepository ordersRepository;
    private final OrdersProductRepository ordersProductsRepository;
    private final UsersRepository usersRepository;
    private final ProductsRepository productsRepository;
    private final MailService mailService;
    private final SizesService sizesService;

    @Autowired
    public OrdersService(OrdersRepository ordersRepository, SizesService sizesService, UsersRepository usersRepository, ProductsRepository productsRepository, MailService mailService, OrdersProductRepository ordersProductsRepository) {
        this.ordersRepository = ordersRepository;
        this.usersRepository = usersRepository;
        this.productsRepository = productsRepository;
        this.sizesService = sizesService;
        this.ordersProductsRepository = ordersProductsRepository;
        this.mailService = mailService;
    }

    public List<OrdersDtoMonthly> getMonthlyService() {
        return ordersRepository.findMonthlyPrices();
    }


    public Page<Orders> getAllSerivce(Integer pageNo, Integer pageSize) {
        LocalDate date = LocalDate.now();
        Pageable paging = PageRequest.of(pageNo, pageSize);

        Page<Orders> ordersPage = ordersRepository.findAll(paging);
        ordersPage.forEach((orders -> {
            Period period = Period.between(orders.getDate(), date);
            if(period.getDays() >= 5 && orders.getStatus().equals("W REALIZACJI")) {
                updateOrderStatusService(orders.getIdOrders(), "ANULOWANO");
                ordersProductsRepository.findAllByOrders(Optional.of(orders)).forEach(ordersProducts -> {
                    Products products = productsRepository.findById(ordersProducts.getProducts().getId()).get();
                    Sizes sizes = sizesService.findByProductAndSizeService(ordersProducts.getSize(), products);
                    sizesService.increaseQuantityService((long) ordersProducts.getQuantity(), sizes.getIdSize());
                });
                Users users = orders.getUsers();
                mailService.sendOrderMail(users.getEmail(), orders.getIdOrders(), "ANULOWANO");
            }
        }));
        return ordersPage;
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

    public ResponseEntity<Orders> updateOrdersAdminService(OrdersDto ordersDto) {
        Orders orders = ordersRepository.findByIdOrders(ordersDto.getIdOrders());
        orders.setStatus(ordersDto.getStatus());
        if(ordersDto.getStatus().equals("ANULOWANO")) {
            ordersProductsRepository.findAllByOrders(Optional.of(orders)).forEach(ordersProducts -> {
                Products products = productsRepository.findById(ordersProducts.getProducts().getId()).get();
                Sizes sizes = sizesService.findByProductAndSizeService(ordersProducts.getSize(), products);
                sizesService.increaseQuantityService((long) ordersProducts.getQuantity(), sizes.getIdSize());
            });
        }
        ordersRepository.save(orders);
        return new ResponseEntity<>(orders, HttpStatus.ACCEPTED);
    }

    public ResponseEntity<Long> updateOrderService(long id, String price) {
        Orders updateOrders = ordersRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order not exist with id: " + id));
        updateOrders.setFinalPrice(Double.valueOf(price));

        ordersRepository.save(updateOrders);

        return new ResponseEntity<>(id, HttpStatus.CREATED);
    }


    public void updateOrderStatusService(long id, String status) {
        Orders updateOrders = ordersRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order not exist with id: " + id));
        updateOrders.setStatus(status);

        ordersRepository.save(updateOrders);

    }

    public ResponseEntity<Orders> deleteOrderAdminService(Long id) {
        ordersRepository.deleteById(id);
        return new ResponseEntity<>(new Orders(), HttpStatus.ACCEPTED);
    }


}
