package org.example.repository;

import org.example.entities.UserClient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClientRepository extends JpaRepository<UserClient, Long> {

    UserClient findByEmail(String email);
    @Transactional
    @Modifying
    @Query(value = "UPDATE clients SET client_balance = :#{#client.balance} WHERE client_id = :#{#client.id}", nativeQuery = true)
    void updateBalanceUser(@Param("client") UserClient client);
    UserClient findByCardNumber(String cardNumber);
    Optional<UserClient> findByPhoneNumber(String phoneNumber);
    @Transactional
    @Query(value = "SELECT * FROM clients", nativeQuery = true)
    List<UserClient> findAllClients();
    boolean existsByPhoneNumber(String phoneNumber);
    void deleteById(Long id);
    Optional<UserClient> findById(Long id);
    boolean existsById(Long id);
    void deleteAll();
}
