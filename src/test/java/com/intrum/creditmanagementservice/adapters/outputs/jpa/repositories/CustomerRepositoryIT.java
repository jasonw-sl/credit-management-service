package com.intrum.creditmanagementservice.adapters.outputs.jpa.repositories;

import com.intrum.creditmanagementservice.core.domains.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@Import(CustomerRepository.class)
@DisplayName("Customer repository test")
class CustomerRepositoryIT {

    @Autowired
    private CustomerRepository customerRepository;

    @Test
    @DisplayName("Find customer by id 1 in DB")
    void test_1() {
        //given
        CustomerId customerId = new CustomerId(1L);

        //when
        Optional<Customer> customer = customerRepository.findCustomer(customerId);

        //then
        assertAll(
                () -> assertTrue(customer.isPresent()),
                () -> assertThat(customer.get()).isNotNull(),
                () -> assertThat(customer.get().getCode().getValue()).isEqualTo("32965391046")
        );
    }

    @Test
    @DisplayName("When customer can not be found by ID")
    void test_2() {
        //given
        CustomerId customerId = new CustomerId(999L);

        //when
        Optional<Customer> customer = customerRepository.findCustomer(customerId);

        //then
        assertFalse(customer.isPresent());
    }

    @Test
    @DisplayName("Find customer by code 654734345 in DB")
    void test_3() {
        //given
        Code code = new Code("654734345");

        //when
        Optional<Customer> customer = customerRepository.findCustomer(code);

        //then
        assertAll(
                () -> assertTrue(customer.isPresent()),
                () -> assertThat(customer.get()).isNotNull(),
                () -> assertThat(customer.get().getId().getValue()).isEqualTo(5L)
        );
    }

    @Test
    @DisplayName("When could not found customer by code")
    void test_4() {
        //given
        Code code = new Code("280134765034");

        //when
        Optional<Customer> customer = customerRepository.findCustomer(code);

        //then
        assertFalse(customer.isPresent());
    }

    @Test
    @DisplayName("Add new customer to DB")
    void test_5() {
        //given
        Customer customer =
                new Customer(
                        new Name("Beach"),
                        new Surname("Box"),
                        new Code("12345"),
                        new Country("GB"),
                        new Email("name@domain.com"));

        //when
        Customer customerAdded = customerRepository.addCustomer(customer);

        //then
        Optional<Customer> customerFound = customerRepository.findCustomer(customerAdded.getId());
        assertAll(
                () -> assertThat(customerAdded).isNotNull(),
                () -> assertThat(customerAdded.getId()).isNotNull(),
                () -> assertTrue(customerFound.isPresent()),
                () -> assertThat(customerFound.get().getCode()).isEqualTo(customer.getCode())
        );
    }

    @Test
    @DisplayName("Remove customer from DB")
    void test_6() {
        //given
        Customer customer =
                new Customer(
                        new Name("Beach"),
                        new Surname("Box"),
                        new Code("8438509345"),
                        new Country("GB"),
                        new Email("name@domain.com"));
        //pre
        Customer customerAdded = customerRepository.addCustomer(customer);
        assertThat(customerAdded).isNotNull();

        //when
        int recordsRemoved = customerRepository.removeCustomer(customerAdded.getId());
        assertThat(recordsRemoved).isEqualTo(1);

        //post
        Optional<Customer> customerFound = customerRepository.findCustomer(customer.getCode());
        assertFalse(customerFound.isPresent());
    }

    @Test
    @DisplayName("Edit existing customer data in DB")
    void test_7() {
        //given
        Customer customer =
                new Customer(
                        new Name("Beach"),
                        new Surname("Box"),
                        new Code("1111111111"),
                        new Country("GB"),
                        new Email("name@domain.com"));
        //pre
        Customer customerAdded = customerRepository.addCustomer(customer);
        assertThat(customerAdded).isNotNull();

        //when
        Customer customerEdited = customerRepository.editCustomer(
                new Customer(
                        customerAdded.getId(),
                        new Name("Oskars"),
                        new Surname("Adamovičs"),
                        new Code("1111111111"),
                        new Country("GB"),
                        new Email("name@domain.com")));
        assertThat(customerEdited).isNotNull();

        //post
        Optional<Customer> customerFound = customerRepository.findCustomer(customer.getCode());
        assertTrue(customerFound.isPresent());
        assertThat(customerEdited.getName().getValue()).isEqualTo("OSKARS");
    }
}