package org.example;

import org.example.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "org.example.repository")
@EntityScan(basePackages = "org.example.entities")
public class BankApplication {

    private final ClientService clientService;

    @Autowired
    public BankApplication(ClientService clientService){
        System.out.println("hiberApplication создан");
        this.clientService = clientService;
    }

    public static void main(String[] args) {
        SpringApplication.run(BankApplication.class, args);
        System.out.println("начал работу после запуска спринга");
    }

    @Bean
    public CommandLineRunner demo() {
        return args -> {
            /*UserClient client = new UserClient();
            client.setName("danya");
            clientService.createClient(client);

            System.out.println(clientService.findById(1L).getName());*/
            System.out.println("robit");
        };
    }
}
