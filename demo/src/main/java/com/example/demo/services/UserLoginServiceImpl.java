package com.example.demo.services;

import com.example.demo.entity.Role;
import com.example.demo.entity.UserLogin;
import com.example.demo.repository.RoleRepository;
import com.example.demo.repository.UserLoginRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class UserLoginServiceImpl implements UserLoginService {

    @Autowired
    private UserLoginRepository userLoginRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public UserLogin saveUser(UserLogin user) {
        Set<Role> resolvedRoles = new HashSet<>();

        for (Role role : user.getRoles()) {
            Role existingRole = roleRepository.findByRoleName(role.getRoleName())
                    .orElseThrow(() -> new RuntimeException("Role not found: " + role.getRoleName()));
            resolvedRoles.add(existingRole);
        }

        user.setRoles(resolvedRoles);
        return userLoginRepository.save(user);
    }
}
