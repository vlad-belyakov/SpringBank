package org.example.controllers;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Collection;
import java.util.List;

@Controller
@RequestMapping("/v1/admin/test")
public class AdminTestController {

    @GetMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public String showAdminTestPage() {
        // Получаем объект Authentication из SecurityContextHolder
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // Получаем коллекцию авторитетов (ролей)
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();

        // Преобразуем коллекцию авторитетов в список строковых представлений ролей
        List<String> listt = authorities.stream()
                .map(GrantedAuthority::getAuthority)
                .toList();
        for (String s: listt){
            System.out.println(s);
        }
        return "admin-test-page";
    }

}
