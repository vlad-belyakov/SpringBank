package org.example.controllers;

import org.example.service.ClientService;
import org.example.user.UserClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
/*@DependsOn({"clientRepository"})*/
@RequestMapping("/clients")
public class ClientController {


        @GetMapping
        public String getInfo(@RequestParam("name") String client_name){
            return client_name;
        }

        private final ClientService clientService;

        @Autowired
        public ClientController(ClientService clientService){
            System.out.println("hiberController создан");
            this.clientService = clientService;
        }

        public boolean isExistsByPhoneNumber(String phoneNumber){
            return clientService.isExistsByPhoneNumber(phoneNumber);
        }

        @GetMapping("/v1/all-clients")
        public @ResponseBody List<UserClient> getAllOwners(){
            return clientService.findAllClients();
        }

        @GetMapping("/v1/client/{id}")
        public @ResponseBody UserClient getClientById(@PathVariable Long id){
            return clientService.findById(id);
        }

        @GetMapping("/v1/client/{phoneNumber}")
        public @ResponseBody UserClient getClientByPhoneNumber(@PathVariable String phoneNumber){
            return (UserClient) clientService.findByPhoneNumber(phoneNumber);
        }

        @PostMapping("/v1/create-client")
        public UserClient createClient(@RequestBody UserClient client){
            return clientService.createClient(client);
        }

        @DeleteMapping("/v1/delete-client/{id}")
        public void deleteClient(@PathVariable Long id){
            clientService.deleteClient(id);
        }

        @PutMapping("/v1/update-client/{id}")
        public UserClient updateClient(@PathVariable Long id, @RequestBody UserClient client){
            return clientService.updateClient(client, id);
        }


}
