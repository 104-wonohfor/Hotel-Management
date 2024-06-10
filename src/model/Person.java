package model;

import types.AccountType;

public class Person extends Account {
    private String name;
    private String Address;
    private String email;
    private String phone;
    private AccountType accountType;
    
    public Person() {
    }

    public Person(String name, String Address, String email, String phone, AccountType accountType) {
        this.name = name;
        this.Address = Address;
        this.email = email;
        this.phone = phone;
        this.accountType = accountType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String Address) {
        this.Address = Address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public AccountType getAccountType() {
        return accountType;
    }

    public void setAccountType(AccountType accountType) {
        this.accountType = accountType;
    }
}
