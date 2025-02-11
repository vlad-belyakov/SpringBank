package org.example.service;

import org.example.entities.Role;
import org.example.entities.UserClient;
import org.example.repository.ClientRepository;
import org.example.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Set;

@Service
public class ClientRoleService {

    private final ClientRepository userClientRepository;
    private final RoleRepository roleRepository;

    @Autowired
    public ClientRoleService(ClientRepository userClientRepository, RoleRepository roleRepository) {
        this.userClientRepository = userClientRepository;
        this.roleRepository = roleRepository;
    }


    public Set<Role> getRolesByUserId(Long userId) {
        return userClientRepository.findById(userId)
                .map(UserClient::getRoles)
                .orElse(Collections.emptySet());
    }

    public void addRoleToUser(Long userId, String roleName) {
        UserClient user = userClientRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Пользователь не найден"));
        Role role = roleRepository.findByRole(roleName)
                .orElseThrow(() -> new RuntimeException("Роль не найдена"));

        user.getRoles().add(role);
        userClientRepository.save(user);
    }

    public void removeRoleFromUser(Long userId, String roleName) {
        UserClient user = userClientRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Пользователь не найден"));
        Role role = roleRepository.findByRole(roleName)
                .orElseThrow(() -> new RuntimeException("Роль не найдена"));

        user.getRoles().remove(role);
        userClientRepository.save(user);
    }
}

