package com.intrum.creditmanagementservice.core.domains;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@DisplayName("User domain object test")
class UserTest {

    @Test
    @DisplayName("When valid user object is given without customer ID")
    void test_1() {
        //when
        User user = assertDoesNotThrow(
                () -> new User(
                        new Username("oskada"),
                        new Password("password")));

        //then
        assertAll(
                () -> assertThat(user).isNotNull(),
                () -> assertThat(user.getCustomerId()).isNull(),
                () -> assertThat(user.getUsername().getValue()).isEqualTo("OSKADA"),
                () -> assertThat(user.getPassword().getValue()).isEqualTo("password")
        );
    }

    @Test
    @DisplayName("When valid user object is given with customer ID")
    void test_2() {
        //when
        User user = assertDoesNotThrow(
                () -> new User(
                        new CustomerId(1603L),
                        new Username("oskada"),
                        new Password("password")));

        //then
        assertAll(
                () -> assertThat(user).isNotNull(),
                () -> assertThat(user.getCustomerId().getValue()).isEqualTo(1603L),
                () -> assertThat(user.getUsername().getValue()).isEqualTo("OSKADA"),
                () -> assertThat(user.getPassword().getValue()).isEqualTo("password")
        );
    }

    @Test
    @DisplayName("When user password is encrypted")
    void test_3() {
        //when
        User user = assertDoesNotThrow(
                () -> new User(
                        new Username("oskada"),
                        new Password("password")));
        user.encryptPassword();

        //then
        assertAll(
                () -> assertThat(user).isNotNull(),
                () -> assertThat(user.getCustomerId()).isNull(),
                () -> assertThat(user.getUsername().getValue()).isEqualTo("OSKADA"),
                () -> assertThat(user.getPassword().getValue()).isEqualTo("cGFzc3dvcmQ=")
        );
    }
}