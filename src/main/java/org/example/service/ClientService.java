package org.example.service;

import org.example.repository.ClientRepository;
import org.example.entities.UserClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Lazy
//@DependsOn({"clientRepository"})
public class ClientService {

        private final ClientRepository clientRepository;

        private UserClient userClient;

        @Autowired
        public ClientService(ClientRepository clientRepository){
            System.out.println("ClientService создан");
            this.clientRepository = clientRepository;
        }

        public UserClient createClient(UserClient client){
            System.out.println("создание клиента");
            clientRepository.save(client);
            this.userClient = client;
            return client;
        }

        public List<UserClient> findAll(){
            System.out.println("поиск всех клиентов");
            List<UserClient> u = new ArrayList<>();
            if (u.size() > 0) {
                for (UserClient c : clientRepository.findAll()) {
                    u.add(c);
                }
            } else {
                System.out.println("пользователей нет");
            }
            return u;
        }

    public List<UserClient> findAllClients(){
        System.out.println("поиск всех клиентов");
        List<UserClient> u = new ArrayList<>();
        if (u.size() > 0) {
            for (UserClient c : clientRepository.findAllClients()) {
                u.add(c);
            }
        } else {
            System.out.println("пользователей нет");
        }
        return u;
    }

        public UserClient findById(Long id){
            System.out.println("поиск по айд");
            if (clientRepository.existsById(id)) {
                return clientRepository.findById(id).get();
            } else {
                throw new RuntimeException("не удалось найти пользователя с таким айди");
            }
        }

        public boolean isExists(String phoneNumber){
            System.out.println("проверка на существование по телефу");
            return clientRepository.existsByPhoneNumber(phoneNumber);
        }

        public UserClient updateClient(UserClient client, Long id){
            System.out.println("обновление клиента");
            if (clientRepository.existsById(id)) {
                clientRepository.save(client);
                return client;
            } else {
                throw new RuntimeException("нет пользователя с таким айди для обновления данных");
            }
        }

        public UserClient getUserClient(){
            System.out.println("получение клиента");
            return userClient;
        }

        public boolean isExists(Long id){
            System.out.println("проверка существования по айди");
            return clientRepository.existsById(id);
        }

        public UserClient findByPhoneNumber(String phoneNumber){
            System.out.println("поиск по номеру телефона");
            if (clientRepository.existsByPhoneNumber(phoneNumber)){
                return clientRepository.findByPhoneNumber(phoneNumber).get();
            } else {
                throw new RuntimeException("нет пользователя с таким номером телефона");
            }
        }

        public void deleteClient(Long id){
            System.out.println("удаление по айди");
            if(clientRepository.existsById(id)){
                clientRepository.deleteById(id);
            } else {
                throw new RuntimeException("удаление пользователя невозможно из-за его отсутствия");
            }
        }
}
