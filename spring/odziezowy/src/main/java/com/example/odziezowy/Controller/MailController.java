package com.example.odziezowy.Controller;

import com.example.odziezowy.Service.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.mail.MessagingException;

@RestController
public class MailController {

    private final MailService mailService;

    @Autowired
    public MailController(MailService mailService) {
        this.mailService = mailService;
    }

    @GetMapping("/sendMail")
    public String sendMail() throws MessagingException {
        mailService.sendMail();
        return "wysłano";
    }

}
