package com.cfbmapi.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    // JDBC Authentication - Load users from database
    @Bean
    public UserDetailsManager userDetailsManager(DataSource dataSource) {
        JdbcUserDetailsManager jdbcUserDetailsManager = new JdbcUserDetailsManager(dataSource);

        // Query to fetch user credentials
        jdbcUserDetailsManager.setUsersByUsernameQuery(
                "select email, password, active as enabled from users where email=?"
        );

        // Query to fetch user roles
        jdbcUserDetailsManager.setAuthoritiesByUsernameQuery(
                "select u.email, concat('ROLE_', u.role) " +
                        "from users u where u.email=?"
        );

        return jdbcUserDetailsManager;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http){
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

        http.httpBasic(Customizer.withDefaults());

        // Disable CSRF for REST APIs
        http.csrf(csrf -> csrf.disable());

        return http.build();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}