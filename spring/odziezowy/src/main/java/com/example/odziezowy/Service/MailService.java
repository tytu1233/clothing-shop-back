package com.example.odziezowy.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;

@Service
public class MailService {
    private final JavaMailSender javaMailSender;
    private final SimpleMailMessage message;

    @Autowired
    public MailService(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
        this.message = new SimpleMailMessage();
    }

    public void sendMail() throws MessagingException {
        String from = "sklepodziezowynaix@gmail.com";
        String to = "letsgetitingirona@gmail.com";

        message.setFrom(from);
        message.setTo(to);
        message.setSubject("This is a plain text email");
        message.setText("Hello guys! This is a plain text email.");

        javaMailSender.send(message);
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
