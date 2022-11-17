package com.example.odziezowy.Service;

import com.example.odziezowy.DTOS.ProductsDto;
import com.example.odziezowy.Model.Orders;
import com.example.odziezowy.Model.OrdersProducts;
import com.example.odziezowy.Model.Products;
import com.example.odziezowy.Model.Users;
import com.example.odziezowy.Repository.OrdersProductRepository;
import com.example.odziezowy.Repository.OrdersRepository;
import com.example.odziezowy.Repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class MailService {
    private final JavaMailSender javaMailSender;
    private final SimpleMailMessage message;
    private final MimeMessage mimeMessage;
    private final UsersRepository usersRepository;
    private final OrdersProductRepository ordersProductRepository;
    private final OrdersRepository ordersRepository;
    private final SpringTemplateEngine templateEngine;


    @Autowired
    public MailService(JavaMailSender javaMailSender, UsersRepository usersRepository, SpringTemplateEngine templateEngine, OrdersRepository ordersRepository, OrdersProductRepository ordersProductRepository) {
        this.javaMailSender = javaMailSender;
        this.message = new SimpleMailMessage();
        this.mimeMessage = javaMailSender.createMimeMessage();
        this.templateEngine = templateEngine;
        this.ordersRepository = ordersRepository;
        this.ordersProductRepository = ordersProductRepository;
        this.usersRepository = usersRepository;

    }

    public void sendNewsletterMail(String to) {
        String from = "sklepodziezowynaix@gmail.com";

        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "UTF-8");
        Context context = new Context();
        Map<String, Object> properties = new HashMap<>();
        properties.put("name", to);
        context.setVariables(properties);
        try {
            helper.setFrom(from);
            helper.setTo(to);
            helper.setSubject("Dziękujemy za zapisanie się do newslettera!");
            String html = templateEngine.process("newsletter.html", context);
        helper.setText(html, true);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
        javaMailSender.send(mimeMessage);
    }

    public void sendOrderMail(String to, Long id_order, String status) {
        String from = "sklepodziezowynaix@gmail.com";

        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "UTF-8");
        Context context = new Context();
        Map<String, Object> properties = new HashMap<>();
        properties.put("status", status);
        context.setVariables(properties);
        try {
            helper.setFrom(from);
            helper.setTo(to);
            helper.setSubject("Twoje zamówienie o id " + id_order + " zmieniło status na " + status);
            String html = templateEngine.process("order_cancel.html", context);
            helper.setText(html, true);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
        javaMailSender.send(mimeMessage);
    }

    public void sendNewOrderMailService(Long order_id) {
        String from = "sklepodziezowynaix@gmail.com";

        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "UTF-8");
        Context context = new Context();

        Orders orders = ordersRepository.findByIdOrders(order_id);
        List<ProductsDto> products = new ArrayList<>();
        ordersProductRepository.findAllByOrders(Optional.ofNullable(orders)).forEach(ordersProducts -> {
            products.add(new ProductsDto(ordersProducts.getProducts().getName(), ordersProducts.getQuantity(), orders.getFinalPrice(), ordersProducts.getSize(), ordersProducts.getProducts().getName(), ordersProducts.getProducts().getPrice(), ordersProducts.getProducts().getDescription(), ordersProducts.getProducts().getBrand(), ordersProducts.getProducts().getImage(), ordersProducts.getProducts().getCategories().getCategoryName()));
        });
        Map<String, List<ProductsDto>> properties = new HashMap<>();
        properties.put("products", products);
        //properties.put("final_price", orders.getFinalPrice());
        context.setVariables(Collections.unmodifiableMap(properties));
        try {
            helper.setFrom(from);
            helper.setTo(orders.getUsers().getEmail());
            helper.setSubject("Twoje zamówienie o id " + order_id + " zostało złożone!");
            String html = templateEngine.process("new_order.html", context);
            helper.setText(html, true);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
        javaMailSender.send(mimeMessage);
    }

    public ResponseEntity<String> sendContantService(String to, String message, String name) {
        String from = "sklepodziezowynaix@gmail.com";

        this.message.setFrom(from);
        this.message.setTo(from);
        this.message.setSubject(name);
        this.message.setText(message + "\n\nAdres e-mail: " + to);

        javaMailSender.send(this.message);
        return new ResponseEntity<>("wyslano", HttpStatus.OK);
    }

}
