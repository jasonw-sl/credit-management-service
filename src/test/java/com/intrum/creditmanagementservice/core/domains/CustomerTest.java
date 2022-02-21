package com.intrum.creditmanagementservice.core.domains;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@DisplayName("Customer domain object test")
class CustomerTest {

    @Test
    @DisplayName("When valid customer object is given without ID")
    void test_1() {
        //when
        Customer customer = assertDoesNotThrow(
                () -> new Customer(
                        new Name("Oskars"),
                        new Surname("Adamovičs"),
                        new Code("32184750389"),
                        new Country("lv"),
                        new Email("adamovics.oskars@inbox.lv")));

        //then
        assertAll(
                () -> assertThat(customer).isNotNull(),
                () -> assertThat(customer.getId()).isNull(),
                () -> assertThat(customer.getName().getValue()).isEqualTo("OSKARS"),
                () -> assertThat(customer.getSurname().getValue()).isEqualTo("ADAMOVIČS"),
                () -> assertThat(customer.getCode().getValue()).isEqualTo("32184750389"),
                () -> assertThat(customer.getCountry().getValue()).isEqualTo("LV"),
                () -> assertThat(customer.getEmail().getValue()).isEqualTo("adamovics.oskars@inbox.lv")
        );
    }

    @Test
    @DisplayName("When valid customer object is given with ID")
    void test_2() {
        //when
        Customer customer = assertDoesNotThrow(
                () -> new Customer(
                        new CustomerId(1516L),
                        new Name("Oskars"),
                        new Surname("Adamovičs"),
                        new Code("32184750389"),
                        new Country("lv"),
                        new Email("adamovics.oskars@inbox.lv")));

        //then
        assertAll(
                () -> assertThat(customer).isNotNull(),
                () -> assertThat(customer.getId().getValue()).isEqualTo(1516L),
                () -> assertThat(customer.getName().getValue()).isEqualTo("OSKARS"),
                () -> assertThat(customer.getSurname().getValue()).isEqualTo("ADAMOVIČS"),
                () -> assertThat(customer.getCode().getValue()).isEqualTo("32184750389"),
                () -> assertThat(customer.getCountry().getValue()).isEqualTo("LV"),
                () -> assertThat(customer.getEmail().getValue()).isEqualTo("adamovics.oskars@inbox.lv")
        );
    }
}