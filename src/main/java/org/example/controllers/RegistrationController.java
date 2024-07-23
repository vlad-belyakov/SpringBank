package org.example.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class RegistrationController {

    public RegistrationController(){

    }

    @GetMapping("/registration")
    public String handleRegistartionButton(Model model){
        return "registration";
    }

}
