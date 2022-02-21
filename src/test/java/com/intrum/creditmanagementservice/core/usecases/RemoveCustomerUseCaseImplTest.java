package com.intrum.creditmanagementservice.core.usecases;

import com.intrum.creditmanagementservice.core.CoreException;
import com.intrum.creditmanagementservice.core.domains.*;
import com.intrum.creditmanagementservice.core.domains.enums.Status;
import com.intrum.creditmanagementservice.core.ports.outputs.FindUserByCustomerIdRepository;
import com.intrum.creditmanagementservice.core.ports.outputs.ObtainDebtCasesRepository;
import com.intrum.creditmanagementservice.core.ports.outputs.RemoveCustomerByIdRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;

import static com.intrum.creditmanagementservice.core.domains.enums.ErrorMessage.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

@ExtendWith(MockitoExtension.class)
@DisplayName("Remove customer user case")
class RemoveCustomerUseCaseImplTest {

    @Mock
    private RemoveCustomerByIdRepository removeCustomerByIdRepository;

    @Mock
    private FindUserByCustomerIdRepository findUserByCustomerIdRepository;

    @Mock
    private ObtainDebtCasesRepository obtainDebtCasesRepository;

    @InjectMocks
    private RemoveCustomerUseCaseImpl removeCustomerUseCase;


    @Test
    @DisplayName("When try to remove non existing customer from DB")
    void test_1() {
        //given
        CustomerId customerId = new CustomerId(1017L);
        given(findUserByCustomerIdRepository.findUser(customerId))
                .willReturn(Optional.empty());
        given(obtainDebtCasesRepository.obtainDebtCases(customerId))
                .willReturn(new HashSet<>());
        given(removeCustomerByIdRepository.removeCustomer(customerId))
                .willReturn(0);

        //when
        CoreException coreException = assertThrows(CoreException.class, () -> removeCustomerUseCase.removeCustomer(customerId));

        //then
        then(findUserByCustomerIdRepository).should().findUser(customerId);
        then(obtainDebtCasesRepository).should().obtainDebtCases(customerId);
        then(removeCustomerByIdRepository).should().removeCustomer(customerId);
        assertAll(
                () -> assertThat(coreException).isNotNull(),
                () -> assertThat(coreException.getStatus()).isEqualTo(Status.NO_DATA),
                () -> assertThat(coreException.getObject()).isEqualTo("There is no a customer by customer id [1017] which could be removed from DB!"),
                () -> assertThat(coreException.getErrorMessage()).isEqualTo(CUSTOMER_NOT_FOUND)
        );
    }

    @Test
    @DisplayName("When try to remove customer who has reference to user")
    void test_2() {
        //given
        CustomerId customerId = new CustomerId(1023L);
        given(findUserByCustomerIdRepository.findUser(customerId))
                .willReturn(Optional.of(
                        new User(
                                new CustomerId(1023L),
                                new Username("oskada"),
                                new Password("qQWERTYU"))
                ));

        //when
        CoreException coreException = assertThrows(CoreException.class, () -> removeCustomerUseCase.removeCustomer(customerId));

        //then
        then(findUserByCustomerIdRepository).should().findUser(customerId);
        then(obtainDebtCasesRepository).shouldHaveNoInteractions();
        then(removeCustomerByIdRepository).shouldHaveNoInteractions();
        assertAll(
                () -> assertThat(coreException).isNotNull(),
                () -> assertThat(coreException.getStatus()).isEqualTo(Status.VALIDATION_FAIL),
                () -> assertThat(coreException.getObject()).isEqualTo("Can not remove customer, there is user [OSKADA] connected to customer!"),
                () -> assertThat(coreException.getErrorMessage()).isEqualTo(USER_EXIST)
        );
    }

    @Test
    @DisplayName("When try to remove customer who has reference to debt cases")
    void test_3() {
        //given
        CustomerId customerId = new CustomerId(1029L);
        given(findUserByCustomerIdRepository.findUser(customerId))
                .willReturn(Optional.empty());
        given(obtainDebtCasesRepository.obtainDebtCases(customerId))
                .willReturn(new HashSet<>(Collections.singletonList(
                        new DebtCase(
                                new DebtCaseId(1L),
                                new CustomerId(1029L),
                                new CaseNumber("GT-22"),
                                new Money(BigDecimal.TEN, "EUR"),
                                new DueDate(LocalDate.now().plusDays(5))))));

        //when
        CoreException coreException = assertThrows(CoreException.class, () -> removeCustomerUseCase.removeCustomer(customerId));

        //then
        then(findUserByCustomerIdRepository).should().findUser(customerId);
        then(obtainDebtCasesRepository).should().obtainDebtCases(customerId);
        then(removeCustomerByIdRepository).shouldHaveNoInteractions();
        assertAll(
                () -> assertThat(coreException).isNotNull(),
                () -> assertThat(coreException.getStatus()).isEqualTo(Status.VALIDATION_FAIL),
                () -> assertThat(coreException.getObject()).isEqualTo("Can not remove customer, there are [1]x debt cases connected to customer!"),
                () -> assertThat(coreException.getErrorMessage()).isEqualTo(DEBT_CASE_EXIST)
        );
    }

    @Test
    @DisplayName("When customer is removed successfully")
    void test_4() {
        //given
        CustomerId customerId = new CustomerId(1033L);
        given(findUserByCustomerIdRepository.findUser(customerId))
                .willReturn(Optional.empty());
        given(obtainDebtCasesRepository.obtainDebtCases(customerId))
                .willReturn(new HashSet<>());
        given(removeCustomerByIdRepository.removeCustomer(customerId))
                .willReturn(1);

        //when
        assertDoesNotThrow(() -> removeCustomerUseCase.removeCustomer(customerId));

        //then
        then(findUserByCustomerIdRepository).should().findUser(customerId);
        then(obtainDebtCasesRepository).should().obtainDebtCases(customerId);
        then(removeCustomerByIdRepository).should().removeCustomer(customerId);
    }
}