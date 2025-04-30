package org.example.controllers;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.example.entities.UserClient;
import org.example.jwt.JWTKeyGenerator;
import org.example.security.CustomClientDetailService;
import org.example.security.UserFactory;
import org.example.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.security.Key;
import java.util.Arrays;

@Controller
@RequestMapping("/v1/login")
public class LoginController {

    private final ClientService clientService;
    private final AuthenticationManager authenticationManager;
    private final CustomClientDetailService customClientDetailService;
    private final Key jwtKey;
    private final PasswordEncoder passwordEncoder;
    private final UserFactory userFactory;

    @Autowired
    public LoginController(ClientService clientService,
                           AuthenticationManager authenticationManager,
                           CustomClientDetailService customClientDetailService,
                           Key jwtKey,
                           PasswordEncoder passwordEncoder,
                           UserFactory userFactory){
        this.authenticationManager = authenticationManager;
        this.customClientDetailService = customClientDetailService;
        this.clientService = clientService;
        this.passwordEncoder = passwordEncoder;
        this.jwtKey = jwtKey;
        this.userFactory = userFactory;
    }

    @GetMapping
    public String showLoginPage(@RequestParam(value = "error", required = false) String error,
                                @RequestParam(value = "logout", required = false) String logout,
                                Model model) {
        if (error != null) {
            model.addAttribute("error", "Неверный номер телефона или пароль.");
        }
        if (logout != null) {
            model.addAttribute("logout", "Вы успешно вышли из системы.");
        }
        return "login";
    }


    @PostMapping
    public String loginUser(@RequestParam("username") String phoneNumber,
                            @RequestParam("password") String password,
                            HttpServletResponse response,
                            Model model) {
        try {

            UserClient user = clientService.findByPhoneNumber(phoneNumber);


            if (user == null) {
                model.addAttribute("error", "Пользователь с таким номером телефона не найден.");
                return "login";
            }

            if (!passwordEncoder.matches(password, user.getPassword())) {
                model.addAttribute("error", "Неверный пароль.");
                return "login";
            }

            clientService.assignRoleToClient(user.getId(), "USER");

            UserDetails userDetails = userFactory.createUser(user.getPhoneNumber(), user.getPassword(), user.getStringRoles());

            Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);

            String token = JWTKeyGenerator.generateToken(userDetails, jwtKey);

            Cookie jwtCookie = new Cookie("JWT_TOKEN", token);
            jwtCookie.setHttpOnly(true);   // Защита от XSS
            jwtCookie.setSecure(false);     // только по HTTPS
            jwtCookie.setPath("/");        // Доступ для всех страниц
            jwtCookie.setMaxAge(3600);
            jwtCookie.setAttribute("SameSite", "Lax"); // Защита от CSRF
            response.addCookie(jwtCookie);

            return "redirect:/v1/user/main-page";

        } catch (Exception e) {
            model.addAttribute("error", "Произошла ошибка. Повторите попытку.");
            try {
                response.sendRedirect("/v1/login?error=exception");
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }
        return "login";
    }

}
