package org.example;

import org.example.entities.Role;
import org.example.service.ClientService;
import org.example.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "org.example.repository")
@EntityScan(basePackages = "org.example.entities")
public class BankApplication {

    private final ClientService clientService;

    private final RoleService roleService;

    @Autowired
    public BankApplication(ClientService clientService, RoleService roleService){
        System.out.println("BankApplication создан");
        this.roleService = roleService;
        this.clientService = clientService;
        roleService.addRoles(new Role().setNewRole("USER"));
        roleService.addRoles(new Role().setNewRole("ADMIN"));
    }

    public static void main(String[] args) {
        SpringApplication.run(BankApplication.class, args);

    }

}
