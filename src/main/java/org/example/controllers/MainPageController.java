package org.example.controllers;

import org.example.service.ClientService;
import org.example.entities.UserClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Collection;
import java.util.List;

@Controller
@PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
@RequestMapping("/v1/user/main-page")
public class MainPageController {

    private final ClientService clientService;

    @Autowired
    public MainPageController(ClientService clientService){
        System.out.println("MainPageController создан");
        this.clientService = clientService;
    }

    @GetMapping("/all-clients")
    @PreAuthorize("hasAuthority('ADMIN')")
    @ResponseBody
    public List<UserClient> getAllClients(){
        return clientService.findAll();
    }

    @GetMapping
    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
    public String showMainPage(Authentication authentication){
        System.out.println("гет main page");

        if (authentication == null) {
            System.out.println("Authentication объект null!");
            return "main";
        }

        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();

        List<String> listt = authorities.stream()
                .map(GrantedAuthority::getAuthority)
                .toList();
        return "main";
    }

    @GetMapping("/profile")
    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
    public String getInfo(){
        return "user-test-page";
    }

    @GetMapping("/client/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    @ResponseBody
    public UserClient getClientById(@PathVariable Long id){
        return clientService.findById(id);
    }

    @GetMapping("/client/{phoneNumber}")
    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
    @ResponseBody
    public UserClient getClientByPhoneNumber(@PathVariable String phoneNumber){
        return clientService.findByPhoneNumber(phoneNumber);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/client")
    public ResponseEntity<String> createClient(@RequestBody UserClient client){
        clientService.createClient(client);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(client.getId())
                .toUri();

        return ResponseEntity.created(location).build();
    }

    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
    @DeleteMapping("/delete-client/{id}&{clientid}")
    public ResponseEntity<Void> deleteClient(@PathVariable Long id, @PathVariable Long clientid){
        if (clientid == id) {
            clientService.deleteClient(id);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
    @PutMapping("/update-client/{id}")
    public ResponseEntity<Void> updateClient(@PathVariable Long id, @RequestBody UserClient client){
        if (!clientService.isExists(id)) {
            return ResponseEntity.notFound().build();
        } else {
            clientService.updateClient(client, id);
            return ResponseEntity.ok().build();
        }
    }
}
