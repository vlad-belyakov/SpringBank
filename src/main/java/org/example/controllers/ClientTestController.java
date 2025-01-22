package org.example.controllers;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/v1/user/test")
public class ClientTestController {

    @GetMapping
    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
    public String showAdminTestPage() {
        return "user-test-page";
    }


}
