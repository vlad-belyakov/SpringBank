package org.example.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@EnableWebSecurity
@Configuration
public class SecurityConfig {

    private final CustomClientDetailService cc;

    @Autowired
    public SecurityConfig(CustomClientDetailService cc){
        this.cc = cc;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/admin/**").hasRole("ADMIN")  // Доступ для ADMIN
                        .requestMatchers("/user/**").hasAnyRole("USER", "ADMIN")  // Доступ для USER и ADMIN
                        .anyRequest().authenticated()  // Все остальные запросы требуют аутентификации
                )
                .formLogin(form -> form
                        .loginPage("/login")  // Настраиваем пользовательскую страницу логина
                        .defaultSuccessUrl("/main-page", true)  // Перенаправление после успешного входа
                        .permitAll()  // Разрешаем всем доступ к странице логина
                )
                .logout(logout -> logout
                        .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))  // URL для выхода
                        .logoutSuccessUrl("/login?logout")  // Перенаправление после выхода
                        .invalidateHttpSession(true)  // Инвалидируем сессию
                        /*.deleteCookies("JSESSIONID")*/  // Удаляем cookie сессии
                        .permitAll()  // Разрешаем всем доступ к выходу
                );

        return http.build();
    }
}
