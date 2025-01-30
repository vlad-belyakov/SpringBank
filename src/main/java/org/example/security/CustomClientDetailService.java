package org.example.security;

import org.example.entities.UserClient;
import org.example.repository.ClientRepository;
import org.example.service.ClientService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private static final Logger logger = LoggerFactory.getLogger(CustomClientDetailService.class);


    @Autowired
    public CustomClientDetailService(ClientRepository clientRepository, ClientService clientService){
        this.clientRepository = clientRepository;
        this.clientService = clientService;
    }

    @Override
    public UserDetails loadUserByUsername(String phoneNumber) throws UsernameNotFoundException {
        logger.info("Попытка найти пользователя с номером телефона: {}", phoneNumber);
        UserClient user = clientService.findByPhoneNumber(phoneNumber);
        if (user == null) {
            logger.warn("Пользователь не найден с номером телефона: {}", phoneNumber);
            throw new RuntimeException("User not found with phone number: " + phoneNumber);
        }
        logger.info("Пользователь найден: {}", user.getPhoneNumber());
        return User.builder()
                .username(user.getPhoneNumber())
                .password(user.getPassword())
                .authorities(user.getStringRoles())
                .build();
    }
}
