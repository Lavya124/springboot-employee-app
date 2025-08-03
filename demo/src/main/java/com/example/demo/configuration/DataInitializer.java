package com.example.demo.configuration;

import com.example.demo.entity.Role;
import com.example.demo.repository.RoleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class DataInitializer {

    @Bean
    public CommandLineRunner insertRoles(RoleRepository roleRepository) {
        return args -> {
            List<String> roles = List.of("ADMIN", "USER", "MANAGER", "HR");

            for (String roleName : roles) {
                roleRepository.findByRoleName(roleName)
                        .orElseGet(() -> roleRepository.save(new Role(null, roleName)));
            }

            System.out.println("Default roles inserted successfully.");
        };
    }
}

