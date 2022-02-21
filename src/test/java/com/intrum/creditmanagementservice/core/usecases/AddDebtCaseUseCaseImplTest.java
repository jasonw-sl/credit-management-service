package com.intrum.creditmanagementservice.core.usecases;

import com.intrum.creditmanagementservice.core.CoreException;
import com.intrum.creditmanagementservice.core.domains.*;
import com.intrum.creditmanagementservice.core.domains.enums.Status;
import com.intrum.creditmanagementservice.core.ports.outputs.AddDebtCaseRepository;
import com.intrum.creditmanagementservice.core.ports.outputs.FindCustomerByIdRepository;
import com.intrum.creditmanagementservice.core.ports.outputs.FindDebtCaseByNumberRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

import static com.intrum.creditmanagementservice.core.domains.enums.ErrorMessage.CUSTOMER_NOT_FOUND;
import static com.intrum.creditmanagementservice.core.domains.enums.ErrorMessage.DEBT_CASE_EXIST;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

@ExtendWith(MockitoExtension.class)
@DisplayName("Add debt case use case")
class AddDebtCaseUseCaseImplTest {

    @Mock
    private AddDebtCaseRepository addDebtCaseRepository;

    @Mock
    private FindCustomerByIdRepository findCustomerByIdRepository;

    @Mock
    private FindDebtCaseByNumberRepository findDebtCaseByNumberRepository;

    @InjectMocks
    private AddDebtCaseUseCaseImpl addDebtCaseUseCase;

    @Test
    @DisplayName("When try to add debt case to non existing customer")
    void test_1() {
        //given
        DebtCase debtCase = new DebtCase(
                new CustomerId(1124L),
                new CaseNumber("DC-1234"),
                new Money(BigDecimal.ONE, "USD"),
                new DueDate(LocalDate.now().plusDays(4)));
        given(findCustomerByIdRepository.findCustomer(debtCase.getCustomerId()))
                .willReturn(Optional.empty());

        //when
        CoreException coreException = assertThrows(CoreException.class, () -> addDebtCaseUseCase.addDebtCase(debtCase));

        //then
        then(findCustomerByIdRepository).should().findCustomer(debtCase.getCustomerId());
        then(findDebtCaseByNumberRepository).shouldHaveNoInteractions();
        then(addDebtCaseRepository).shouldHaveNoInteractions();
        assertAll(
                () -> assertThat(coreException).isNotNull(),
                () -> assertThat(coreException.getStatus()).isEqualTo(Status.NO_DATA),
                () -> assertThat(coreException.getObject()).isEqualTo("There is no customer with id [1124] in DB to whom assign debt case!"),
                () -> assertThat(coreException.getErrorMessage()).isEqualTo(CUSTOMER_NOT_FOUND)
        );
    }

    @Test
    @DisplayName("When try to add debt case with the same debt case number")
    void test_2() {
        //given
        DebtCase debtCase = new DebtCase(
                new CustomerId(1129L),
                new CaseNumber("DC-1234"),
                new Money(BigDecimal.ONE, "USD"),
                new DueDate(LocalDate.now().plusDays(4)));
        given(findCustomerByIdRepository.findCustomer(debtCase.getCustomerId()))
                .willReturn(Optional.of(
                        new Customer(
                                new CustomerId(1129L),
                                new Name("Oskars"),
                                new Surname("Adamovičs"),
                                new Code("28019112458"),
                                new Country("LV"),
                                new Email("adamovics.oskars@inbox.lv"))));
        given(findDebtCaseByNumberRepository.findDebtCase(debtCase.getNumber()))
                .willReturn(Optional.of(
                        new DebtCase(
                                new DebtCaseId(12L),
                                new CustomerId(65L),
                                new CaseNumber("DC-1234"),
                                new Money(BigDecimal.TEN, "EUR"),
                                new DueDate(LocalDate.now().plusDays(10)))));

        //when
        CoreException coreException = assertThrows(CoreException.class, () -> addDebtCaseUseCase.addDebtCase(debtCase));

        //then
        then(findCustomerByIdRepository).should().findCustomer(debtCase.getCustomerId());
        then(findDebtCaseByNumberRepository).should().findDebtCase(debtCase.getNumber());
        then(addDebtCaseRepository).shouldHaveNoInteractions();
        assertAll(
                () -> assertThat(coreException).isNotNull(),
                () -> assertThat(coreException.getStatus()).isEqualTo(Status.CONFLICT),
                () -> assertThat(coreException.getObject()).isEqualTo("In DB there is already a debt case with case number [DC-1234]!"),
                () -> assertThat(coreException.getErrorMessage()).isEqualTo(DEBT_CASE_EXIST)
        );
    }

    @Test
    @DisplayName("When debt case is added successfully")
    void test_3() {
        //given
        DebtCase debtCase =
                new DebtCase(
                        new CustomerId(1129L),
                        new CaseNumber("DC-1234"),
                        new Money(BigDecimal.ONE, "USD"),
                        new DueDate(LocalDate.now().plusDays(4)));
        given(findCustomerByIdRepository.findCustomer(debtCase.getCustomerId()))
                .willReturn(Optional.of(
                        new Customer(
                                new CustomerId(1129L),
                                new Name("Oskars"),
                                new Surname("Adamovičs"),
                                new Code("28019112458"),
                                new Country("LV"),
                                new Email("adamovics.oskars@inbox.lv"))));
        given(findDebtCaseByNumberRepository.findDebtCase(debtCase.getNumber()))
                .willReturn(Optional.empty());
        given(addDebtCaseRepository.addDebtCase(debtCase))
                .willReturn(
                        new DebtCase(
                                new DebtCaseId(35L),
                                new CustomerId(1129L),
                                new CaseNumber("DC-1234"),
                                new Money(BigDecimal.ONE, "USD"),
                                new DueDate(LocalDate.now().plusDays(4))));

        //when
        DebtCase debtCaseAdded = assertDoesNotThrow(() -> addDebtCaseUseCase.addDebtCase(debtCase));

        //then
        then(findCustomerByIdRepository).should().findCustomer(debtCase.getCustomerId());
        then(findDebtCaseByNumberRepository).should().findDebtCase(debtCase.getNumber());
        then(addDebtCaseRepository).should().addDebtCase(debtCase);
        assertAll(
                () -> assertThat(debtCaseAdded).isNotNull(),
                () -> assertThat(debtCaseAdded.getId()).isNotNull(),
                () -> assertThat(debtCaseAdded.getId().getValue()).isEqualTo(35L)
        );
    }
}