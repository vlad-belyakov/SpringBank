package org.example.jwt;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.security.Key;

@Configuration
public class JWTConfig {

    @Bean
    public Key jwtSigningKey() {
        // Генерируем ключ при запуске приложения
        return JWTKeyGenerator.generateKey();
    }
}