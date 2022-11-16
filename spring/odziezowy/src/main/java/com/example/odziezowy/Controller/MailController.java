package com.example.odziezowy.Controller;

import com.example.odziezowy.Service.MailService;
import netscape.javascript.JSObject;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin("*")
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

    @PostMapping("/contact")
    public ResponseEntity<String> sendContact(@RequestBody Map<String, Object> inputData) throws NoSuchFieldException {
        JSONObject jsonObj = new JSONObject(inputData);
        mailService.sendContantService(jsonObj.get("email").toString(), jsonObj.get("message").toString(), jsonObj.get("name").toString());
        return new ResponseEntity<>("wyslano", HttpStatus.OK);
    }

}
