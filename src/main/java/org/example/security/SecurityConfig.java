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




@EnableWebSecurity
@Configuration
public class SecurityConfig {

    private final CustomClientDetailService cc;
    private final JWTAuthenticationFilter jwtAuthenticationFilter;

    @Autowired
    public SecurityConfig(CustomClientDetailService cc, JWTAuthenticationFilter jwtAuthenticationFilter) {
        this.cc = cc;
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder =
                http.getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder.userDetailsService(cc).passwordEncoder(passwordEncoder());
        return authenticationManagerBuilder.build();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/v1/registration", "/v1/login", "/css/**", "/js/**", "/images/**", "/static/**", "/favicon.ico")
                        .permitAll()
                        .requestMatchers("/v1/admin/**").hasAuthority("ADMIN")
                        .requestMatchers("/v1/user/**").hasAnyAuthority("USER", "ADMIN")
                        .anyRequest().authenticated()
                )
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling(exceptionHandling -> exceptionHandling
                        .authenticationEntryPoint((request, response, authException) -> {
                            response.sendRedirect("/v1/login?error=true");
                        })
                );
        return http.build();
    }
}
