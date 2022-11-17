package com.example.odziezowy.Service;

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
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class MailService {
    private final JavaMailSender javaMailSender;
    private final SimpleMailMessage message;
    private final MimeMessage mimeMessage;
    private final SpringTemplateEngine templateEngine;


    @Autowired
    public MailService(JavaMailSender javaMailSender, SpringTemplateEngine templateEngine) {
        this.javaMailSender = javaMailSender;
        this.message = new SimpleMailMessage();
        this.mimeMessage = javaMailSender.createMimeMessage();
        this.templateEngine = templateEngine;

    }

    public void sendNewsletterMail(String to) throws MessagingException {
        String from = "sklepodziezowynaix@gmail.com";

        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "UTF-8");
        Context context = new Context();
        Map<String, Object> properties = new HashMap<>();
        properties.put("name", to);
        context.setVariables(properties);
        helper.setFrom(from);
        helper.setTo(to);
        helper.setSubject("Dziękujemy za zapisanie się do newslettera!");
        String html = templateEngine.process("newsletter.html", context);
        helper.setText(html, true);

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
