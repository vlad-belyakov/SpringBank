package org.example.service;

import org.example.entities.Role;
import org.example.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleService {

    private final RoleRepository roleRepository;

    @Autowired
    public RoleService(RoleRepository roleRepository){
        this.roleRepository = roleRepository;
    }

    public boolean isExistByRole(String role){
        return roleRepository.existsByRole(role);
    }

    public void addRoles(Role role){
        if (!roleRepository.existsByRole(role.getRole())) {
            roleRepository.save(role);

        } else {
            throw new RuntimeException("такая роль уже существует: " + role.getRole());
        }
    }

}
