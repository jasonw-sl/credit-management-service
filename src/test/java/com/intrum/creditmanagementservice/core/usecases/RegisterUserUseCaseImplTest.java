package com.intrum.creditmanagementservice.core.usecases;

import com.intrum.creditmanagementservice.core.CoreException;
import com.intrum.creditmanagementservice.core.domains.*;
import com.intrum.creditmanagementservice.core.domains.enums.Status;
import com.intrum.creditmanagementservice.core.ports.outputs.FindCustomerByIdRepository;
import com.intrum.creditmanagementservice.core.ports.outputs.FindUserByUsernameRepository;
import com.intrum.creditmanagementservice.core.ports.outputs.RegisterUserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static com.intrum.creditmanagementservice.core.domains.enums.ErrorMessage.CUSTOMER_NOT_FOUND;
import static com.intrum.creditmanagementservice.core.domains.enums.ErrorMessage.USER_EXIST;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

@ExtendWith(MockitoExtension.class)
@DisplayName("Register user use case")
class RegisterUserUseCaseImplTest {

    @Mock
    private RegisterUserRepository registerUserRepository;

    @Mock
    private FindCustomerByIdRepository findCustomerByIdRepository;

    @Mock
    private FindUserByUsernameRepository findUserByUsernameRepository;

    @InjectMocks
    private RegisterUserUseCaseImpl registerUserUseCase;

    @Test
    @DisplayName("When try to register a user with not existing customer id")
    void test_1() {
        //given
        User user = new User(
                new CustomerId(20L),
                new Username("oskada"),
                new Password("password"));
        given(findCustomerByIdRepository.findCustomer(new CustomerId(20L)))
                .willReturn(Optional.empty());

        //when
        CoreException coreException = assertThrows(CoreException.class, () -> registerUserUseCase.registerUser(user));

        //then
        then(findCustomerByIdRepository).should().findCustomer(new CustomerId(20L));
        then(findUserByUsernameRepository).shouldHaveNoInteractions();
        then(registerUserRepository).shouldHaveNoInteractions();
        assertAll(
                () -> assertThat(coreException).isNotNull(),
                () -> assertThat(coreException.getStatus()).isEqualTo(Status.NO_DATA),
                () -> assertThat(coreException.getObject()).isEqualTo("Could not found customer by customer id [20] in DB!"),
                () -> assertThat(coreException.getErrorMessage()).isEqualTo(CUSTOMER_NOT_FOUND)
        );
    }

    @Test
    @DisplayName("When username is already taken by other customer")
    void test_2() {
        //given
        User user = new User(
                new CustomerId(27L),
                new Username("raimoc"),
                new Password("password"));
        given(findCustomerByIdRepository.findCustomer(new CustomerId(27L)))
                .willReturn(Optional.of(
                        new Customer(
                                new CustomerId(27L),
                                new Name("Raitis"),
                                new Surname("Mocēns"),
                                new Code("32057368312"),
                                new Country("LV"),
                                new Email("baze@gmail.com"))));
        given(findUserByUsernameRepository.findUser(user.getUsername()))
                .willReturn(Optional.of(
                        new User(
                                new CustomerId(30L),
                                new Username("raimoc"),
                                new Password("c3VtbWVy"))));

        //when
        CoreException coreException = assertThrows(CoreException.class, () -> registerUserUseCase.registerUser(user));

        //then
        then(findCustomerByIdRepository).should().findCustomer(new CustomerId(27L));
        then(findUserByUsernameRepository).should().findUser(user.getUsername());
        then(registerUserRepository).shouldHaveNoInteractions();
        assertAll(
                () -> assertThat(coreException).isNotNull(),
                () -> assertThat(coreException.getStatus()).isEqualTo(Status.CONFLICT),
                () -> assertThat(coreException.getObject()).isEqualTo("Could not register a new user, username [RAIMOC] is already taken!"),
                () -> assertThat(coreException.getErrorMessage()).isEqualTo(USER_EXIST)
        );
    }

    @Test
    @DisplayName("When user is registered successfully")
    void test_3() {
        //given
        User user = new User(
                new CustomerId(33L),
                new Username("raimoc"),
                new Password("password"));
        given(findCustomerByIdRepository.findCustomer(new CustomerId(33L)))
                .willReturn(Optional.of(
                        new Customer(
                                new CustomerId(33L),
                                new Name("Arvis"),
                                new Surname("Garais"),
                                new Code("324346457834"),
                                new Country("LV"),
                                new Email("garais.arvis@gmail.com"))));
        given(findUserByUsernameRepository.findUser(user.getUsername()))
                .willReturn(Optional.empty());

        //when
        assertDoesNotThrow(() -> registerUserUseCase.registerUser(user));

        //then
        then(findCustomerByIdRepository).should().findCustomer(new CustomerId(33L));
        then(findUserByUsernameRepository).should().findUser(user.getUsername());
        then(registerUserRepository).should().registerUser(user);
    }
}