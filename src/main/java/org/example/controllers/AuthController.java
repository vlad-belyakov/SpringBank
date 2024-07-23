package org.example.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/authentication")
public class AuthController {

    public AuthController(){

    }

    @GetMapping("/main-page")
    public String handleMainPageButton(){
        return "main-page";
    }

}
