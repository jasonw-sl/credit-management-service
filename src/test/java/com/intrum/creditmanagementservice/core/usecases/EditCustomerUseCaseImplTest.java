package com.intrum.creditmanagementservice.core.usecases;

import com.intrum.creditmanagementservice.core.CoreException;
import com.intrum.creditmanagementservice.core.domains.*;
import com.intrum.creditmanagementservice.core.domains.enums.Status;
import com.intrum.creditmanagementservice.core.ports.outputs.EditCustomerRepository;
import com.intrum.creditmanagementservice.core.ports.outputs.FindCustomerByCodeRepository;
import com.intrum.creditmanagementservice.core.ports.outputs.FindCustomerByIdRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static com.intrum.creditmanagementservice.core.domains.enums.ErrorMessage.CUSTOMER_EXIST;
import static com.intrum.creditmanagementservice.core.domains.enums.ErrorMessage.CUSTOMER_NOT_FOUND;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

@ExtendWith(MockitoExtension.class)
@DisplayName("Edit customer use case")
class EditCustomerUseCaseImplTest {

    @Mock
    private EditCustomerRepository editCustomerRepository;

    @Mock
    private FindCustomerByIdRepository findCustomerByIdRepository;

    @Mock
    private FindCustomerByCodeRepository findCustomerByCodeRepository;

    @InjectMocks
    private EditCustomerUseCaseImpl editCustomerUseCase;

    @Test
    @DisplayName("When try to edit non existing customer from DB")
    void test_1() {
        //given
        Customer customer = buildCustomer();
        given(findCustomerByIdRepository.findCustomer(customer.getId()))
                .willReturn(Optional.empty());

        //when
        CoreException coreException = assertThrows(CoreException.class, () -> editCustomerUseCase.editCustomer(customer));

        //then
        then(findCustomerByIdRepository).should().findCustomer(customer.getId());
        then(findCustomerByCodeRepository).shouldHaveNoInteractions();
        then(editCustomerRepository).shouldHaveNoInteractions();
        assertAll(
                () -> assertThat(coreException).isNotNull(),
                () -> assertThat(coreException.getStatus()).isEqualTo(Status.NO_DATA),
                () -> assertThat(coreException.getObject()).isEqualTo("Could not found customer by customer id [1039] in DB!"),
                () -> assertThat(coreException.getErrorMessage()).isEqualTo(CUSTOMER_NOT_FOUND)
        );
    }

    @Test
    @DisplayName("When try to change customer code which already belong to other customer")
    void test_2() {
        //given
        Customer customer = buildCustomer();
        given(findCustomerByIdRepository.findCustomer(customer.getId()))
                .willReturn(Optional.of(
                        new Customer(
                                new CustomerId(1039L),
                                new Name("Visvalds"),
                                new Surname("Viedais"),
                                new Code("28019112555"),
                                new Country("LV"),
                                new Email("viedais@java.lv"))));
        given(findCustomerByCodeRepository.findCustomer(customer.getCode()))
                .willReturn(Optional.of(
                        new Customer(
                                new CustomerId(1051L),
                                new Name("Oskars"),
                                new Surname("Adamovičs"),
                                new Code("32964097569"),
                                new Country("LV"),
                                new Email("adamovics.oskars@java.lv"))));

        //when
        CoreException coreException = assertThrows(CoreException.class, () -> editCustomerUseCase.editCustomer(customer));

        //then
        then(findCustomerByIdRepository).should().findCustomer(customer.getId());
        then(findCustomerByCodeRepository).should().findCustomer(customer.getCode());
        then(editCustomerRepository).shouldHaveNoInteractions();
        assertAll(
                () -> assertThat(coreException).isNotNull(),
                () -> assertThat(coreException.getStatus()).isEqualTo(Status.CONFLICT),
                () -> assertThat(coreException.getObject()).isEqualTo("Customer by code [32964097569] already exist in DB and belong to customer with id [1051]!"),
                () -> assertThat(coreException.getErrorMessage()).isEqualTo(CUSTOMER_EXIST)
        );
    }

    @Test
    @DisplayName("When customer is updated successfully")
    void test_3() {
        //given
        Customer customer = buildCustomer();
        given(findCustomerByIdRepository.findCustomer(customer.getId()))
                .willReturn(Optional.of(
                        new Customer(
                                new CustomerId(1039L),
                                new Name("Visvalds"),
                                new Surname("Viedais"),
                                new Code("28019112555"),
                                new Country("LV"),
                                new Email("viedais@java.lv"))));
        given(findCustomerByCodeRepository.findCustomer(customer.getCode()))
                .willReturn(Optional.of(
                        new Customer(
                                new CustomerId(1039L),
                                new Name("Visvalds"),
                                new Surname("Viedais"),
                                new Code("28019112555"),
                                new Country("LV"),
                                new Email("viedais@java.lv"))));
        given(editCustomerRepository.editCustomer(customer))
                .willReturn(buildCustomer());

        //when
        Customer updatedCustomer = assertDoesNotThrow(() -> editCustomerUseCase.editCustomer(customer));

        //then
        then(findCustomerByIdRepository).should().findCustomer(customer.getId());
        then(findCustomerByCodeRepository).should().findCustomer(customer.getCode());
        then(editCustomerRepository).should().editCustomer(buildCustomer());
        assertAll(
                () -> assertThat(updatedCustomer).isNotNull(),
                () -> assertThat(updatedCustomer.getCode()).isNotNull(),
                () -> assertThat(updatedCustomer.getCode().getValue()).isEqualTo("32964097569")
        );
    }

    private Customer buildCustomer() {
        return new Customer(
                new CustomerId(1039L),
                new Name("Visvalds"),
                new Surname("Viedais"),
                new Code("32964097569"),
                new Country("LV"),
                new Email("viedais@java.lv"));
    }

}