package org.example.repository;

import org.example.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    @Query(value = """
        SELECT r.* FROM roles r 
        JOIN client_roles cr ON r.id = cr.id_role
        WHERE cr.id_client = :clientId
    """, nativeQuery = true)
    Optional<List<Role>> findRolesByClientId(@Param("clientId") Long clientId);

    boolean existsByRole(String role);

    Optional<Role> findByRole(String Role);

}
