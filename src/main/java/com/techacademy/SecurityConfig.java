package com.techacademy;

import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {
    @SuppressWarnings("removal")
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
         http.cors().and().csrf().disable()
             .authorizeHttpRequests(auth -> auth
                .requestMatchers("/", "/login", "/error","/favicon.ico").permitAll()
                .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
                .requestMatchers("/employees/**").hasAnyAuthority("ADMIN")
                .anyRequest().authenticated()
            )
            .formLogin(login -> login
                .loginProcessingUrl("/login")
                .loginPage("/login")
                .defaultSuccessUrl("/employees",true)
                .failureUrl("/login?error")
                .permitAll()
            )
            .logout(logout -> logout
                .logoutSuccessUrl("/login")
                .permitAll()
            )
            .sessionManagement(session -> session
                .invalidSessionUrl("/login")
                .maximumSessions(1).maxSessionsPreventsLogin(true)
            );

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
