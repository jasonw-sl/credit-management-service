package com.intrum.creditmanagementservice.core.domains;

import com.intrum.creditmanagementservice.core.CoreException;

import java.util.Objects;

import static com.intrum.creditmanagementservice.core.domains.enums.ErrorMessage.*;

public class Customer {
    private CustomerId id;
    private Name name;
    private Surname surname;
    private Code code;
    private Country country;
    private Email email;

    public Customer(Name name, Surname surname, Code code, Country country, Email email) {
        this.setName(name);
        this.setSurname(surname);
        this.setCode(code);
        this.setCountry(country);
        this.setEmail(email);
    }

    public Customer(CustomerId id, Name name, Surname surname, Code code, Country country, Email email) {
        this.setId(id);
        this.setName(name);
        this.setSurname(surname);
        this.setCode(code);
        this.setCountry(country);
        this.setEmail(email);
    }

    public CustomerId getId() {
        return id;
    }

    public Name getName() {
        return name;
    }

    public Surname getSurname() {
        return surname;
    }

    public Code getCode() {
        return code;
    }

    public Country getCountry() {
        return country;
    }

    public Email getEmail() {
        return email;
    }

    private void setId(CustomerId id) {
        if (Objects.isNull(id))
            throw CoreException.createException(NO_CUSTOMER_ID);

        this.id = id;
    }

    private void setName(Name name) {
        if (Objects.isNull(name))
            throw CoreException.createException(NO_NAME);

        this.name = name;
    }

    private void setSurname(Surname surname) {
        if (Objects.isNull(surname))
            throw CoreException.createException(NO_SURNAME);

        this.surname = surname;
    }

    private void setCode(Code code) {
        if (Objects.isNull(code))
            throw CoreException.createException(NO_CODE);

        this.code = code;
    }

    private void setCountry(Country country) {
        if (Objects.isNull(country))
            throw CoreException.createException(NO_COUNTRY);

        this.country = country;
    }

    private void setEmail(Email email) {
        if (Objects.isNull(email))
            throw CoreException.createException(NO_EMAIL);

        this.email = email;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Customer customer = (Customer) o;
        return Objects.equals(id, customer.id) && Objects.equals(name, customer.name) && Objects.equals(surname, customer.surname) && Objects.equals(code, customer.code) && Objects.equals(country, customer.country) && Objects.equals(email, customer.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, surname, code, country, email);
    }

    @Override
    public String toString() {
        return "Customer{" +
                "id=" + id +
                ", name=" + name +
                ", surname=" + surname +
                ", code=" + code +
                ", country=" + country +
                ", email=" + email +
                '}';
    }
}
