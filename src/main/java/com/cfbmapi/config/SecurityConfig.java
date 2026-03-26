package com.cfbmapi.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // ============ CONFIGURE URL SECURITY ============
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http){
        // Disable CSRF for API testing in Postman
        http.csrf(AbstractHttpConfigurer::disable); // method reference of csrf -> csrf.disable()

        http.authorizeHttpRequests(auth -> auth
                // Public endpoints - No login needed
                .requestMatchers("/api/auth/**").permitAll()
                .requestMatchers("/api/users/register").permitAll()

                // Protected endpoints - Login required
                .requestMatchers("/api/users/**").authenticated()
                .requestMatchers("/api/facilities/**").authenticated()
                .requestMatchers("/api/bookings/**").authenticated()
                .requestMatchers("/api/tickets/**").authenticated()

                .anyRequest().authenticated()
        );

//        http.authorizeHttpRequests(auth -> auth.anyRequest().permitAll());

        //Enables the standard browser popup for username/password.
        http.httpBasic(Customizer.withDefaults());
        return http.build();
    }
}