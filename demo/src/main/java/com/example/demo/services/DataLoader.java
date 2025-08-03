package com.example.demo.services;

import com.example.demo.config.SecurityConfig;
import com.example.demo.entity.Role;
import com.example.demo.entity.UserLogin;
import com.example.demo.repository.RoleRepository;
import com.example.demo.repository.UserLoginRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import com.example.demo.config.SecurityConfig.*;

import java.util.List;

@Component
public class DataLoader implements CommandLineRunner {

    @Autowired
    private UserLoginRepository loginRepo;
    @Autowired
    private RoleRepository roleRepository;
    @Override
    public void run(String... args) {

        // Predefined roles
        List<String> roleNames = List.of("ADMIN", "USER", "MANAGER", "HR", "EMPLOYEE");

        for (String roleName : roleNames) {
            roleRepository.findByRoleName(roleName)
                    .orElseGet(() -> roleRepository.save(new Role(roleName)));
        }

        if (!loginRepo.findByUsername("admin").isPresent()) {
            UserLogin user = new UserLogin();
            user.setUsername("admin");
            user.setPassword(SecurityConfig.passwordEncoder().encode("admin123")); // ✅ BCrypt encoded

            // Assign ADMIN role to user
            Role adminRole = roleRepository.findByRoleName("ADMIN").get(); // already ensured to exist
            user.getRoles().add(adminRole);

            loginRepo.save(user); // save with role
        }
    }

}
