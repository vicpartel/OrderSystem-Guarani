package com.store.order_system.config;

import com.store.order_system.model.User;
import com.store.order_system.model.Role;
import com.store.order_system.repository.UserRepository;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.*;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner initUsers(
            UserRepository repo,
            PasswordEncoder encoder) {

        return args -> {

            if(repo.findByEmail("admin@store.com").isEmpty()){
                User admin = new User();
                admin.setEmail("admin@store.com");
                admin.setName("admin");
                admin.setPassword(encoder.encode("admin123"));
                admin.setRole(Role.ADMIN);
                repo.save(admin);
            }

            if(repo.findByEmail("operator@store.com").isEmpty()){
                User operator = new User();
                operator.setEmail("operator@store.com");
                operator.setName("operator");
                operator.setPassword(encoder.encode("operator123"));
                operator.setRole(Role.OPERATOR);
                repo.save(operator);
            }

            if(repo.findByEmail("client@store.com").isEmpty()){
                User client = new User();
                client.setEmail("client@store.com");
                client.setName("client");
                client.setPassword(encoder.encode("client123"));
                client.setRole(Role.CLIENT);
                repo.save(client);
            } 
        };
    }
}