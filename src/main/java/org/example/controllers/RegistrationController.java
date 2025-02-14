package org.example.controllers;

import org.example.entities.UserClient;
import org.example.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Arrays;

@Controller
@RequestMapping("/v1/registration")
public class RegistrationController {
    private final ClientService clientService;

    private final PasswordEncoder passwordEncoder;

    @GetMapping
    public String showPage(){
        return "registration";
    }

    @Autowired
    public RegistrationController(ClientService clientService, PasswordEncoder passwordEncoder){
        this.clientService = clientService;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/main-page")
    public String handleMainPageButton(){
        return "redirect:/v1/main-page";
    }

    @PostMapping
    public String registerUser(String firstName, String lastName, String email, String username, String password, String confirmPassword, Model model) {
        System.out.println("начинается регистрация");

        if (!password.equals(confirmPassword)) {
            model.addAttribute("error", "Пароли не совпадают");
            return "registration";
        }

        if (clientService.isExists(username)) {
            model.addAttribute("error", "Пользователь с таким номером телефона уже существует");
            return "registration";
        }

        System.out.println("регистрация клиента");
        // Создание и сохранение нового пользователя
        clientService.createClient(new UserClient()
                .setName(firstName)
                .setSurname(lastName)
                .setEmail(email)
                .setPhoneNumber(username)
                .setPassword(passwordEncoder.encode(password)))
                .addRole("USER");

        clientService.assignRoleToClient(clientService.getUserClient().getId(), "USER");

        if (clientService.isExists(username)){
            System.out.println(username);
            System.out.println(clientService.getUserClient().getName());
            return "redirect:/v1/login";
        }
        else {
            return "registration";
        }
    }

}
