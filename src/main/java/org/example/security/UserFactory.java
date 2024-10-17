package org.example.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserFactory {

    private final PasswordEncoder passwordEncoder;

    /*@Autowired*/
    public UserFactory() {
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    public UserDetails createUser(String phoneNumber, String rawPassword, String role) {
        return User.builder()
                .username(phoneNumber)
                .password(passwordEncoder.encode(rawPassword))  // Шифруем пароль
                .roles(role)                                    // Присваиваем роль
                .build();
    }

}
