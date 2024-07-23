package org.example.service;

import org.example.repository.ClientRepository;
import org.example.user.UserClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Lazy
@DependsOn({"clientRepository"})
public class ClientService {


        private final ClientRepository clientRepository;

        @Autowired
        public ClientService(ClientRepository clientRepository){
            System.out.println("hiberService создан");
            this.clientRepository = clientRepository;
        }

        public UserClient createClient(UserClient client){
            clientRepository.save(client);
            return client;
        }
        public List<UserClient> findAllClients(){
            List<UserClient> u = new ArrayList<>();
            for(UserClient c: clientRepository.findAll()){
                u.add(c);
            }
            return u;
        }


        public UserClient findById(Long id){
            if (clientRepository.existsById(id)) {
                return clientRepository.findById(id).get();
            } else {
                throw new RuntimeException("не удалось найти пользователя с таким айди");
            }
        }

        public boolean isExistsByPhoneNumber(String phoneNumber){
            return clientRepository.existsByPhoneNumber(phoneNumber);
        }

        public UserClient updateClient(UserClient client, Long id){
            if (clientRepository.existsById(id)) {
                clientRepository.save(client);
                return client;
            } else {
                throw new RuntimeException("нет пользователя с таким айди для обновления данных");
            }
        }

        public UserClient findByPhoneNumber(String phoneNumber){
            if (clientRepository.existsByPhoneNumber(phoneNumber)){
                return clientRepository.findByPhoneNumber(phoneNumber).get();
            } else {
                throw new RuntimeException("нет пользователя с таким номером телефона");
            }
        }

        public void deleteClient(Long id){
            if(clientRepository.existsById(id)){
                clientRepository.deleteById(id);
            } else {
                throw new RuntimeException("удаление пользователя невозможно из-за его отсутствия");
            }
        }
}
