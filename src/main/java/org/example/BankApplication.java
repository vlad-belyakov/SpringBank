package org.example;

import org.example.entities.Role;
import org.example.entities.UserClient;
import org.example.service.ClientService;
import org.example.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
/*import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;*/

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
        //clientService.deleteAll();
        roleService.addRoles(new Role().setNewRole("USER"));
        roleService.addRoles(new Role().setNewRole("ADMIN"));
    }

    public static void main(String[] args) {
        SpringApplication.run(BankApplication.class, args);
        System.out.println("MAIN начал работу после запуска спринга");
    }

    @Bean
    public CommandLineRunner demo() {
        return args -> {
            /*UserClient client = new UserClient();
            client.setName("danya");
            clientService.createClient(client);

            System.out.println(clientService.findById(1L).getName());*/

            /*WebClient webClient = WebClient.create("http://localhost:8081");

            // Отправляем GET-запрос и обрабатываем ответ асинхронно
            Mono<String> response = webClient.get()
                    .uri("/v1/login") // Путь запроса
                    .retrieve() // Отправка запроса
                    .bodyToMono(String.class); // Преобразование тела ответа в String

            // Подписываемся на результат
            response.subscribe(body -> System.out.println("Response: " + body));

            System.out.println("robit");*/
        };
    }
}
