package org.example.security;

import org.example.jwt.JWTAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@EnableWebSecurity
@Configuration
public class SecurityConfig{

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
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {

        /*authenticationManagerBuilder
                .inMemoryAuthentication()
                .withUser("us")
                .password(passwordEncoder().encode("us"))
                .authorities("USER")
                .and()
                .withUser("ad")
                .password(passwordEncoder().encode("ad"))
                .authorities("ADMIN");*/

        AuthenticationManagerBuilder authenticationManagerBuilder =
                http.getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder.userDetailsService(cc).passwordEncoder(passwordEncoder());
        return authenticationManagerBuilder.build();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/v1/registration/**", "/v1/login",
                                "/css/**", "/js/**", "/images/**", "/static/**").permitAll()
                        .requestMatchers("/v1/admin/**").hasAuthority("ADMIN")
                        .requestMatchers("/v1/user/**").hasAnyAuthority("USER", "ADMIN")
                        .anyRequest().authenticated()  // Все остальные запросы требуют аутентификации
                )
                .formLogin(form -> form
                        .loginPage("/v1/login")
                        .loginProcessingUrl("/v1/login")
                        .failureUrl("/v1/login?error=true")
                        .defaultSuccessUrl("/v1/main-page", true)
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                        .logoutSuccessUrl("/login?logout")
                        .invalidateHttpSession(true) // Инвалидируем сессию
                        .clearAuthentication(true) // Очищаем контекст аутентификации
                        .deleteCookies("JSESSIONID")  // Удаляем cookie сессии
                        .permitAll()
                ).addFilterBefore(new JWTAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
}
