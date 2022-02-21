package com.intrum.creditmanagementservice.core.usecases;

import com.intrum.creditmanagementservice.core.domains.Password;
import com.intrum.creditmanagementservice.core.domains.User;
import com.intrum.creditmanagementservice.core.domains.Username;
import com.intrum.creditmanagementservice.core.ports.outputs.FindUserByUsernameRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

@ExtendWith(MockitoExtension.class)
@DisplayName("Authenticate user use case")
class AuthenticateUserUseCaseImplTest {

    @Mock
    private FindUserByUsernameRepository findUserByUsernameRepository;

    @InjectMocks
    private AuthenticateUserUseCaseImpl authenticateUserUseCase;

    @Test
    @DisplayName("When user does not exist in DB")
    void test_1() {
        //given
        User user = new User(
                new Username("oskada"),
                new Password("password"));
        given(findUserByUsernameRepository.findUser(user.getUsername()))
                .willReturn(Optional.empty());

        //when
        boolean isAuthenticated = authenticateUserUseCase.authenticateUser(user);

        //then
        then(findUserByUsernameRepository).should().findUser(user.getUsername());
        assertFalse(isAuthenticated);
    }

    @Test
    @DisplayName("When user exist in DB, but passwords hash does not match")
    void test_2() {
        //given
        User user = new User(
                new Username("oskada"),
                new Password("password"));
        given(findUserByUsernameRepository.findUser(user.getUsername()))
                .willReturn(Optional.of(
                        new User(
                                new Username("oskada"),
                                new Password("SW50cnVt"))));

        //when
        boolean isAuthenticated = authenticateUserUseCase.authenticateUser(user);

        //then
        then(findUserByUsernameRepository).should().findUser(user.getUsername());
        assertFalse(isAuthenticated);
    }

    @Test
    @DisplayName("When user exist in DB and hash match")
    void test_4() {
        //given
        User user = new User(
                new Username("oskada"),
                new Password("password"));
        given(findUserByUsernameRepository.findUser(user.getUsername()))
                .willReturn(Optional.of(
                        new User(
                                new Username("oskada"),
                                new Password("cGFzc3dvcmQ="))));

        //when
        boolean isAuthenticated = authenticateUserUseCase.authenticateUser(user);

        //then
        then(findUserByUsernameRepository).should().findUser(user.getUsername());
        assertTrue(isAuthenticated);
    }
}