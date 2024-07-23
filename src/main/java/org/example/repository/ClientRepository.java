package org.example.repository;

import org.example.user.UserClient;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@Qualifier("userClient")
public interface ClientRepository extends JpaRepository<UserClient, Long> {

    public UserClient findByEmail(String email);
    public UserClient findByCardNumber(String cardNumber);
    public List<UserClient> findAllByPhoneNumber(String phoneNumber);
    public List<UserClient> findAllByEmail(String email);
    public List<UserClient> findAll();
    public Optional<UserClient> findByPhoneNumber(String phoneNumber);
    public boolean existsByPhoneNumber(String phoneNumber);
    public void deleteById(Long id);
    public Optional<UserClient> findById(Long id);
    public boolean existsById(Long id);
}
