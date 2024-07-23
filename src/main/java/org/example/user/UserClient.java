package org.example.user;

import org.example.templates.Client;
import jakarta.persistence.*;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component("userClient")
@Entity
@Table(name = "clients")
public class UserClient extends Client {

    @Column(name = "client_name")
    private String name;

    @Column(name = "client_surname")
    private String surname;

    @Column(name = "client_middlename")
    private String middlename;

    @Column(name = "client_age")
    private short age;

    @Column(name = "card_number")
    private String cardNumber;

    @Column(name = "client_balance")
    private BigDecimal balance = new BigDecimal(0);

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "client_email")
    private String email;

    private String password;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "client_id")
    private Long id;

    @Override
    public String getName() {
        return name;
    }

    @Override
    public UserClient setName(String name) {
        this.name = name;
        return this;
    }

    @Override
    public String getSurname() {
        return surname;
    }

    @Override
    public UserClient setSurname(String surname) {
        this.surname = surname;
        return this;
    }

    @Override
    public String getMiddlename() {
        return middlename;
    }

    @Override
    public UserClient setMiddlename(String middlename) {
        this.middlename = middlename;
        return this;
    }

    @Override
    public short getAge() {
        return age;
    }

    @Override
    public UserClient setAge(short age) {
        this.age = age;
        return this;
    }

    @Override
    public UserClient setCardNumber(Long id) {
        cardNumber = id.toString();
        return this;
    }

    @Override
    public String getCardNumber() {
        return null;
    }

    @Override
    public String getBalance() {
        return String.valueOf(balance);
    }

    @Override
    public UserClient setBalance(String balance) {
        this.balance = BigDecimal.valueOf(Long.parseLong(balance));
        return this;
    }

    @Override
    public String getPhoneNumber() {
        return phoneNumber;
    }

    @Override
    public UserClient setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        return this;
    }

    @Override
    public String getEmail() {
        return email;
    }

    @Override
    public UserClient setEmail(String email) {
        this.email = email;
        return this;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public UserClient setPassword(String password) {
        this.password = password;
        return this;
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public UserClient setId(Long id) {
        this.id = id;
        return this;
    }

}
