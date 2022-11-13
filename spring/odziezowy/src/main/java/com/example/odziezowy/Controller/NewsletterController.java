package com.example.odziezowy.Controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin("*")
@RequestMapping("/newsletter")
public class NewsletterController {

    @GetMapping
    String getNewsletter(Model model) {
        model.addAttribute("newsletter", "this is coming from the controller");
        return "newsletter";
    }

}
