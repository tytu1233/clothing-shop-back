package com.example.odziezowy.Service;

import com.example.odziezowy.DTOS.OrdersDto;
import com.example.odziezowy.DTOS.OrdersDtoMonthly;
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
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.mail.MessagingException;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class OrdersService {

    private OrdersRepository ordersRepository;
    private UsersRepository usersRepository;

    private MailService mailService;

    @Autowired
    public OrdersService(OrdersRepository ordersRepository, UsersRepository usersRepository, MailService mailService) {
        this.ordersRepository = ordersRepository;
        this.usersRepository = usersRepository;
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
            if(period.getDays() >= 3 && !orders.getStatus().equals("ANULOWANO")) {
                updateOrderStatusService(orders.getIdOrders(), "ANULOWANO");
                try {
                    mailService.sendMail();
                } catch (MessagingException e) {
                    throw new RuntimeException(e);
                }
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
