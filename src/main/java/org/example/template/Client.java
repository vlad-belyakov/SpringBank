package org.example.template;


public abstract class Client {

    public abstract String getName();

    public abstract Client setName(String name);

    public abstract String getSurname();

    public abstract Client setSurname(String surname);

    public abstract String getMiddlename();

    abstract public Client setMiddlename(String middlename);

    abstract public short getAge();

    abstract public Client setAge(short age);

    abstract public Client setCardNumber(Long id);

    abstract public String getCardNumber();

    abstract public String getBalance();

    abstract public Client setBalance(String balance);

    abstract public String getPhoneNumber();

    abstract public Client setPhoneNumber(String phoneNumber);

    abstract public String getEmail();

    abstract public Client setEmail(String email);

    abstract public String getPassword();

    abstract public Client setPassword(String password);

    abstract public Long getId();

    abstract public Client setId(Long id);
}
