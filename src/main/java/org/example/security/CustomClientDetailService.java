package org.example.security;

import org.example.entities.UserClient;
import org.example.repository.ClientRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomClientDetailService implements UserDetailsService {

    @Override
    public UserDetails loadUserByUsername(String phoneNumber) throws UsernameNotFoundException {
        UserClient user = ClientRepository.findByPhoneNumber(phoneNumber)
                .orElseThrow(() -> new UsernameNotFoundException("User not found" + phoneNumber));

        return User.builder()
                .username(user.getPhoneNumber())
                .password(user.getPassword())
                .roles(user.getStringRoles())
                .build();
    }
}
