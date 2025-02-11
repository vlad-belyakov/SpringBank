package org.example.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "roles")
@Component
@Getter
@Setter
public class Role {

    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @Column(name = "role")
    private String role;

    public Role setNewRole(String role){
        this.role = role;
        return this;
    }

    @ManyToMany(mappedBy = "roles")
    private Set<UserClient> clients = new HashSet<>();
}
