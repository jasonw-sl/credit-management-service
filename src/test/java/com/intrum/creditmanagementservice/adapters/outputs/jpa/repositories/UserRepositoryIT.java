package com.intrum.creditmanagementservice.adapters.outputs.jpa.repositories;

import com.intrum.creditmanagementservice.core.domains.CustomerId;
import com.intrum.creditmanagementservice.core.domains.Password;
import com.intrum.creditmanagementservice.core.domains.User;
import com.intrum.creditmanagementservice.core.domains.Username;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@Import(UserRepository.class)
@DisplayName("User repository")
class UserRepositoryIT {

    @Autowired
    private UserRepository userRepository;

    @Test
    @DisplayName("When user is found by username")
    void test_1() {
        //given
        Username username = new Username("OSKADA");

        //when
        Optional<User> userFound = userRepository.findUser(username);

        //then
        assertTrue(userFound.isPresent());
    }

    @Test
    @DisplayName("When user can not be found by username")
    void test_2() {
        //given
        Username username = new Username("wrong-username");

        //when
        Optional<User> userFound = userRepository.findUser(username);

        //then
        assertFalse(userFound.isPresent());
    }

    @Test
    @DisplayName("When user is found by customer Id")
    void test_3() {
        //given
        CustomerId customerId = new CustomerId(1L);

        //when
        Optional<User> userFound = userRepository.findUser(customerId);

        //then
        assertTrue(userFound.isPresent());
        assertThat(userFound.get().getUsername().getValue()).isEqualTo("OSKADA");
    }

    @Test
    @DisplayName("When user could not be found by customer Id")
    void test_4() {
        //given
        CustomerId customerId = new CustomerId(123435L);

        //when
        Optional<User> userFound = userRepository.findUser(customerId);

        //then
        assertFalse(userFound.isPresent());
    }

    @Test
    @DisplayName("When new user is successfully registered")
    void test_5() {
        //given
        User user = new User(
                new CustomerId(3L),
                new Username("radio"),
                new Password("tev"));

        //when
        userRepository.registerUser(user);

        //then
        Optional<User> userFound = userRepository.findUser(user.getCustomerId());
        assertTrue(userFound.isPresent());
        assertThat(userFound.get().getUsername()).isEqualTo(user.getUsername());
    }

    @Test
    @DisplayName("When user is removed successfully")
    void test_6() {
        //given
        User user = new User(
                new CustomerId(3L),
                new Username("radio"),
                new Password("tev"));
        userRepository.registerUser(user);
        Optional<User> userFound = userRepository.findUser(user.getCustomerId());
        assertTrue(userFound.isPresent());

        //when
        userRepository.removeUser(user.getUsername());

        //then
        Optional<User> userFoundAgain = userRepository.findUser(user.getCustomerId());
        assertFalse(userFoundAgain.isPresent());
    }
}
