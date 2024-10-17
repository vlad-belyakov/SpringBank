package org.example.controllers;

import org.example.service.ClientService;
import org.example.entities.UserClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/v1/clients")
public class ClientController {

    private final ClientService clientService;
    @Autowired
    public ClientController(ClientService clientService){
        System.out.println("hiberController создан");
        this.clientService = clientService;
    }

    @GetMapping("/user/profile/get-info")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public String getInfo(@RequestParam("name") String client_name){
        return client_name;
    }

    @GetMapping("/user/all-clients")
    @PreAuthorize("hasAnyRole('ADMIN')")
    @ResponseBody
    public List<UserClient> getAllClients(){
        return clientService.findAllClients();
    }

    @GetMapping("/user/{id}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    @ResponseBody
    public UserClient getClientById(@PathVariable Long id){
        return clientService.findById(id);
    }

    @GetMapping("/user/{phoneNumber}")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @ResponseBody
    public UserClient getClientByPhoneNumber(@PathVariable String phoneNumber){
        return clientService.findByPhoneNumber(phoneNumber);
    }

    @DeleteMapping("/delete-client")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public void deleteClient(){
        clientService.deleteClient(clientService.getUserClient().getId());
    }

    @PutMapping("/update-client/{id}")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public UserClient updateClient(@PathVariable Long id, @RequestBody UserClient client){
        return clientService.updateClient(client, id);
    }


}
