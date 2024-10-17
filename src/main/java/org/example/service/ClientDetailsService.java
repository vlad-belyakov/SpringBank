package org.example.service;

import org.example.entities.Role;
import org.example.entities.UserClient;
import org.example.repository.ClientRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
public class ClientDetailsService implements UserDetailsService {
    private final ClientRepository userRepository;

    public ClientDetailsService(ClientRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Находим пользователя по имени
        UserClient user = userRepository.findByPhoneNumber(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));

        // Преобразуем роли пользователя в массив строк
        String[] roles = user.getRoles().stream()
                .map(Role::getRole)
                .toArray(String[]::new);

        // Возвращаем объект UserDetails для Spring Security
        return org.springframework.security.core.userdetails.User.builder()
                .username(user.getName())
                .password(user.getPassword()) // Пароль уже должен быть зашифрован
                .roles(roles) // Присваиваем роли
                .build();
    }
}
