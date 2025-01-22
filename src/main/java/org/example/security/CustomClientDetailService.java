package org.example.security;

import org.example.entities.UserClient;
import org.example.repository.ClientRepository;
import org.example.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomClientDetailService implements UserDetailsService {

    private final ClientRepository clientRepository;
    private final ClientService clientService;

    @Autowired
    public CustomClientDetailService(ClientRepository clientRepository, ClientService clientService){
        this.clientRepository = clientRepository;
        this.clientService = clientService;
    }

    @Override
    public UserDetails loadUserByUsername(String phoneNumber) throws UsernameNotFoundException {
        System.out.println("--------------------------------------------------------------------");
        System.out.println("Ищем пользователя: " + phoneNumber);
        System.out.println("--------------------------------------------------------------------");
        UserClient user;
        if (clientService.isExists(phoneNumber)) {
            user = clientService.findByPhoneNumber(phoneNumber);
        } else {
            throw new UsernameNotFoundException("User not found with phone number: " + phoneNumber);
        }
        return User.builder()
                .username(user.getPhoneNumber())
                .password(user.getPassword())
                .authorities(user.getStringRoles())
                .build();
    }
}
