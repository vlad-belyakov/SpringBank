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

    public UserClient findByEmail(String email);
    @Transactional
    @Modifying
    @Query(value = "UPDATE clients SET client_balance = :#{#client.balance} WHERE client_id = :#{#client.id}", nativeQuery = true)
    void updateBalanceUser(@Param("client") UserClient client);
    public UserClient findByCardNumber(String cardNumber);
    public Optional<UserClient> findByPhoneNumber(String phoneNumber);
    @Transactional
    @Modifying
    @Query(value = "SELECT * FROM clients", nativeQuery = true)
    public List<UserClient> findAllClients();
    public boolean existsByPhoneNumber(String phoneNumber);
    @Transactional
    public void deleteById(Long id);
    public Optional<UserClient> findById(Long id);
    public boolean existsById(Long id);
}
