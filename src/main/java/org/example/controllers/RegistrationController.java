package org.example.controllers;

import org.example.entities.UserClient;
import org.example.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/register")
public class RegistrationController {
    private final ClientService clientService;

    @Autowired
    public RegistrationController(ClientService clientService){
        this.clientService = clientService;
    }

    @GetMapping("/main-page")
    public String handleMainPageButton(){
        return "main-page";
    }

    @PostMapping
    public String registerUser(String firstName, String lastName, String email, String phoneNumber, String password, String confirmPassword, Model model) {
        // Логика регистрации
        if (!password.equals(confirmPassword)) {
            model.addAttribute("error", "Пароли не совпадают");
            return "register";
        }

        // Проверка на наличие пользователя в БД и создание нового пользователя
        if (clientService.isExistsByPhoneNumber(phoneNumber)) {
            model.addAttribute("error", "Пользователь с таким номером телефона уже существует");
            return "register";
        }

        // Создание и сохранение нового пользователя
        clientService.createClient(new UserClient()
                .setName(firstName)
                .setSurname(lastName)
                .setEmail(email)
                .setPhoneNumber(phoneNumber)
                .setPassword(password));

        return "redirect:/login";
    }

}
