package org.example.controllers;

import jakarta.servlet.http.HttpServletResponse;
import org.example.entities.UserClient;
import org.example.jwt.JWTKeyGenerator;
import org.example.security.CustomClientDetailService;
import org.example.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Key;

@Controller
@RequestMapping("/v1/login")
public class LoginController {

    private ClientService clientService;
    private final AuthenticationManager authenticationManager;
    private final CustomClientDetailService customClientDetailService;
    private final Key jwtKey;

    @GetMapping
    public String showLoginPage(@RequestParam(value = "error", required = false) String error,
                                @RequestParam(value = "logout", required = false) String logout,
                                Model model) {
        System.out.println("гет запрос логина");
        for (UserClient u: clientService.findAllClients()){
            System.out.println(u.getPhoneNumber());
        }
        if (error != null) {
            model.addAttribute("error", "Неверный номер телефона или пароль.");
        }
        if (logout != null) {
            model.addAttribute("logout", "Вы успешно вышли из системы.");
        }
        return "login";
    }

    @Autowired
    public LoginController(ClientService clientService,
                           AuthenticationManager authenticationManager,
                           CustomClientDetailService customClientDetailService,
                           Key jwtKey){
        this.authenticationManager = authenticationManager;
        this.customClientDetailService = customClientDetailService;
        this.clientService = clientService;
        this.jwtKey = jwtKey;
    }

    @PostMapping
    @ResponseBody
    public String loginUser(@RequestParam("username") String phoneNumber,
                            @RequestParam("password") String password,
                            HttpServletResponse response,
                            Model model) {
        System.out.println("пост запрос логина по номеру: " + phoneNumber);
        /*try {
            // Создаем объект токена для аутентификации
            UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(phoneNumber, password);

            // Аутентификация
            Authentication authentication = authenticationManager.authenticate(authenticationToken);

            // Устанавливаем аутентификацию в контекст безопасности
            SecurityContextHolder.getContext().setAuthentication(authentication);

            // Генерация JWT
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            String token = JWTKeyGenerator.generateToken(userDetails);

            // Устанавливаем JWT в заголовок ответа
            response.setHeader("Authorization", "Bearer " + token);

            // Перенаправляем на главную страницу после успешного входа
            return "redirect:/v1/main-page";
        } catch (UsernameNotFoundException e) {
            model.addAttribute("error", "Пользователь с таким номером телефона не найден.");
        } catch (BadCredentialsException e) {
            model.addAttribute("error", "Неверный пароль.");
        } catch (Exception e) {
            model.addAttribute("error", "Произошла ошибка. Повторите попытку.");
        }
        return "login";*/
        try {
            // Аутентификация пользователя
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(phoneNumber, password)
            );

            SecurityContextHolder.getContext().setAuthentication(authentication);//

            // Генерация JWT токена
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            String token = JWTKeyGenerator.generateToken(userDetails, jwtKey);

            // Устанавливаем JWT в заголовок ответа
            response.setHeader("Authorization", "Bearer " + token);
            System.out.println("JWT токен генерируется: " + token);

            // Перенаправляем на главную страницу
            return "redirect:/v1/main-page";

        } catch (BadCredentialsException e) {
            model.addAttribute("error", "Неверные учетные данные. Повторите попытку.");
        } catch (Exception e) {
            model.addAttribute("error", "Произошла ошибка. Повторите попытку.");
        }
        return "login"; // Возврат на страницу логина с ошибкой
    }

}
