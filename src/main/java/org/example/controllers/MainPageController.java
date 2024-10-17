package org.example.controllers;

import org.example.service.ClientService;
import org.example.entities.UserClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/main-page")
public class MainPageController {

    private final ClientService clientService;

    @Autowired
    public MainPageController(ClientService clientService){
        System.out.println("MainPageController создан");
        this.clientService = clientService;
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public String getInfo(@RequestParam("name") String client_name){
        return client_name;
    }

    @GetMapping("/all-clients")
    @PreAuthorize("hasRole('ADMIN')")
    @ResponseBody
    public List<UserClient> getAllClients(){
        return clientService.findAllClients();
    }

    @GetMapping("/client/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @ResponseBody
    public UserClient getClientById(@PathVariable Long id){
        return clientService.findById(id);
    }

    @GetMapping("/client/{phoneNumber}")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @ResponseBody
    public UserClient getClientByPhoneNumber(@PathVariable String phoneNumber){
        return clientService.findByPhoneNumber(phoneNumber);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/create-client")
    public UserClient createClient(@RequestBody UserClient client){
        return clientService.createClient(client);
    }

    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @DeleteMapping("/delete-client/{id}")
    public void deleteClient(@PathVariable Long id){
        clientService.deleteClient(id);
    }

    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @PutMapping("/update-client/{id}")
    public UserClient updateClient(@PathVariable Long id, @RequestBody UserClient client){
        return clientService.updateClient(client, id);
    }

    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @PutMapping("/create-card/{id}")
    public ResponseEntity<Void> createCard(@PathVariable Long id, )

}
