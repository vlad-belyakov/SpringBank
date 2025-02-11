package org.example.service;

import org.example.entities.Role;
import org.example.repository.ClientRepository;
import org.example.repository.RoleRepository;
import org.example.entities.UserClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Lazy
public class ClientService {

        private final ClientRepository clientRepository;
        private UserClient userClient;
        private RoleRepository roleRepository;

        @Autowired
        public ClientService(ClientRepository clientRepository, RoleRepository roleRepository){
            System.out.println("ClientService создан");
            this.clientRepository = clientRepository;
            this.roleRepository = roleRepository;
        }

        public UserClient createClient(UserClient client){
            System.out.println("CS создание клиента");
            clientRepository.save(client);
            this.userClient = client;
            return client;
        }

        public void deleteAll(){
            System.out.println("CS удаляем всех");
            clientRepository.deleteAll();
        }

        public List<UserClient> findAll(){
            System.out.println("CS поиск всех клиентов");
            List<UserClient> u = new ArrayList<>();
            if (u.size() > 0) {
                for (UserClient c : clientRepository.findAll()) {
                    u.add(c);
                }
            } else {
                System.out.println("CS пользователей нет");
            }
            return u;
        }

    public List<UserClient> findAllClients(){
        System.out.println("CS поиск всех клиентов");
        List<UserClient> u = new ArrayList<>();
        if (u.size() > 0) {
            for (UserClient c : clientRepository.findAllClients()) {
                u.add(c);
            }
        } else {
            System.out.println("CS пользователей нет");
        }
        return u;
    }

        public UserClient findById(Long id){
            System.out.println("CS поиск по айд");
            if (clientRepository.existsById(id)) {
                return clientRepository.findById(id).get();
            } else {
                throw new RuntimeException("CS не удалось найти пользователя с таким айди");
            }
        }

        public boolean isExists(String phoneNumber){
            System.out.println("CS проверка на существование по телефону");
            return clientRepository.existsByPhoneNumber(phoneNumber);
        }

        public UserClient updateClient(UserClient client, Long id){
            System.out.println("CS обновление клиента");
            if (clientRepository.existsById(id)) {
                clientRepository.save(client);
                return client;
            } else {
                throw new RuntimeException("CS нет пользователя с таким айди для обновления данных");
            }
        }

        public void addRolesToClient(){
            if (roleRepository.findRolesByClientId(userClient.getId()).isPresent()){
                List<Role> roleList = roleRepository.findRolesByClientId(userClient.getId()).get();
                for (Role r: roleList){
                    userClient.addRole(String.valueOf(r));
                }
            } else {
                System.out.println("CS | ошибка: ролей нет у пользователя: " + userClient.getPhoneNumber());
            }
        }

        public UserClient getUserClient(){
            System.out.println("CS получение клиента");
            return userClient;
        }

        public boolean isExists(Long id){
            System.out.println("CS проверка существования по айди");
            return clientRepository.existsById(id);
        }

        public UserClient findByPhoneNumber(String phoneNumber){
            System.out.println("CS поиск по номеру телефона");
            return clientRepository.findByPhoneNumber(phoneNumber)
                    .orElseThrow(() -> new RuntimeException("CS User not found with phone number: " + phoneNumber));
        }

        public void deleteClient(Long id){
            System.out.println("CS удаление по айди");
            if(clientRepository.existsById(id)){
                clientRepository.deleteById(id);
            } else {
                throw new RuntimeException("CS удаление пользователя невозможно из-за его отсутствия");
            }
        }

    public void assignRoleToClient(Long clientId, String roleName) {
        UserClient client = clientRepository.findById(clientId)
                .orElseThrow(() -> new RuntimeException("Клиент не найден"));

        Role role = roleRepository.findByRole(roleName)
                .orElseThrow(() -> new RuntimeException("Роль не найдена"));

        client.getRoles().add(role);
        clientRepository.save(client);
    }

}
