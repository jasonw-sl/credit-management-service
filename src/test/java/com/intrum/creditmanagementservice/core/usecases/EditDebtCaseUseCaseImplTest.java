package com.intrum.creditmanagementservice.core.usecases;

import com.intrum.creditmanagementservice.core.CoreException;
import com.intrum.creditmanagementservice.core.domains.*;
import com.intrum.creditmanagementservice.core.domains.enums.Status;
import com.intrum.creditmanagementservice.core.ports.outputs.EditDebtCaseRepository;
import com.intrum.creditmanagementservice.core.ports.outputs.FindCustomerByIdRepository;
import com.intrum.creditmanagementservice.core.ports.outputs.FindDebtCaseByNumberRepository;
import com.intrum.creditmanagementservice.core.ports.outputs.FindDebtCaseRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

import static com.intrum.creditmanagementservice.core.domains.enums.ErrorMessage.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

@ExtendWith(MockitoExtension.class)
@DisplayName("Edit debt case use case")
class EditDebtCaseUseCaseImplTest {

    @Mock
    private EditDebtCaseRepository editDebtCaseRepository;

    @Mock
    private FindCustomerByIdRepository findCustomerByIdRepository;

    @Mock
    private FindDebtCaseRepository findDebtCaseRepository;

    @Mock
    private FindDebtCaseByNumberRepository findDebtCaseByNumberRepository;

    @InjectMocks
    private EditDebtCaseUseCaseImpl editDebtCaseUseCase;

    @Test
    @DisplayName("When try to edit debt case, but given customer id does not exist in DB")
    void test_1() {
        //given
        DebtCase debtCase =
                new DebtCase(
                        new DebtCaseId(1L),
                        new CustomerId(1213L),
                        new CaseNumber("DC-1234"),
                        new Money(BigDecimal.ONE, "USD"),
                        new DueDate(LocalDate.now().plusDays(4)));
        given(findCustomerByIdRepository.findCustomer(debtCase.getCustomerId()))
                .willReturn(Optional.empty());

        //when
        CoreException coreException = assertThrows(CoreException.class, () -> editDebtCaseUseCase.editDebtCase(debtCase));

        //then
        then(findCustomerByIdRepository).should().findCustomer(debtCase.getCustomerId());
        then(findDebtCaseRepository).shouldHaveNoInteractions();
        then(findDebtCaseByNumberRepository).shouldHaveNoInteractions();
        then(editDebtCaseRepository).shouldHaveNoInteractions();
        assertAll(
                () -> assertThat(coreException).isNotNull(),
                () -> assertThat(coreException.getStatus()).isEqualTo(Status.NO_DATA),
                () -> assertThat(coreException.getObject()).isEqualTo("Could not found customer by customer id [1213] in DB!"),
                () -> assertThat(coreException.getErrorMessage()).isEqualTo(CUSTOMER_NOT_FOUND)
        );
    }

    @Test
    @DisplayName("When try to edit debt case, but debt case does not exist in DB")
    void test_2() {
        //given
        DebtCase debtCase =
                new DebtCase(
                        new DebtCaseId(1L),
                        new CustomerId(1229L),
                        new CaseNumber("DC-1234"),
                        new Money(BigDecimal.ONE, "USD"),
                        new DueDate(LocalDate.now().plusDays(4)));
        given(findCustomerByIdRepository.findCustomer(debtCase.getCustomerId()))
                .willReturn(Optional.of(
                        new Customer(
                                new CustomerId(1229L),
                                new Name("Oskars"),
                                new Surname("Adamovičs"),
                                new Code("28019112458"),
                                new Country("LV"),
                                new Email("adamovics.oskars@inbox.lv"))));
        given(findDebtCaseRepository.findDebtCase(debtCase.getId()))
                .willReturn(Optional.empty());

        //when
        CoreException coreException = assertThrows(CoreException.class, () -> editDebtCaseUseCase.editDebtCase(debtCase));

        //then
        then(findCustomerByIdRepository).should().findCustomer(debtCase.getCustomerId());
        then(findDebtCaseRepository).should().findDebtCase(debtCase.getId());
        then(findDebtCaseByNumberRepository).shouldHaveNoInteractions();
        then(editDebtCaseRepository).shouldHaveNoInteractions();
        assertAll(
                () -> assertThat(coreException).isNotNull(),
                () -> assertThat(coreException.getStatus()).isEqualTo(Status.NO_DATA),
                () -> assertThat(coreException.getObject()).isEqualTo("Could not found debt case by debt case id [1] in DB!"),
                () -> assertThat(coreException.getErrorMessage()).isEqualTo(DEBT_CASE_NOT_FOUND)
        );
    }

    @Test
    @DisplayName("When try to edit debt case case number, but debt case with such debt case number already exist")
    void test_3() {
        //given
        DebtCase debtCase =
                new DebtCase(
                        new DebtCaseId(1L),
                        new CustomerId(1229L),
                        new CaseNumber("DC-1234"),
                        new Money(BigDecimal.ONE, "USD"),
                        new DueDate(LocalDate.now().plusDays(4)));
        given(findCustomerByIdRepository.findCustomer(debtCase.getCustomerId()))
                .willReturn(Optional.of(
                        new Customer(
                                new CustomerId(1229L),
                                new Name("Oskars"),
                                new Surname("Adamovičs"),
                                new Code("28019112458"),
                                new Country("LV"),
                                new Email("adamovics.oskars@inbox.lv"))));
        given(findDebtCaseRepository.findDebtCase(debtCase.getId()))
                .willReturn(Optional.of(
                        new DebtCase(
                                new DebtCaseId(1L),
                                new CustomerId(1229L),
                                new CaseNumber("DC-6547345"),
                                new Money(BigDecimal.TEN, "EUR"),
                                new DueDate(LocalDate.now().plusDays(4)))));
        given(findDebtCaseByNumberRepository.findDebtCase(debtCase.getNumber()))
                .willReturn(Optional.of(
                        new DebtCase(
                                new DebtCaseId(134L),
                                new CustomerId(1545L),
                                new CaseNumber("DC-1234"),
                                new Money(BigDecimal.valueOf(12.33), "CHF"),
                                new DueDate(LocalDate.now().plusDays(4)))));

        //when
        CoreException coreException = assertThrows(CoreException.class, () -> editDebtCaseUseCase.editDebtCase(debtCase));

        //then
        then(findCustomerByIdRepository).should().findCustomer(debtCase.getCustomerId());
        then(findDebtCaseRepository).should().findDebtCase(debtCase.getId());
        then(findDebtCaseByNumberRepository).should().findDebtCase(debtCase.getNumber());
        then(editDebtCaseRepository).shouldHaveNoInteractions();
        assertAll(
                () -> assertThat(coreException).isNotNull(),
                () -> assertThat(coreException.getStatus()).isEqualTo(Status.CONFLICT),
                () -> assertThat(coreException.getObject()).isEqualTo("Debt case by case number [DC-1234] already exist in DB and belong to debt case with debt case id [134]!"),
                () -> assertThat(coreException.getErrorMessage()).isEqualTo(DEBT_CASE_EXIST)
        );
    }

    @Test
    @DisplayName("When debt case is updated successfully")
    void test_4() {
        //given
        DebtCase debtCase =
                new DebtCase(
                        new DebtCaseId(1L),
                        new CustomerId(1229L),
                        new CaseNumber("DC-1234"),
                        new Money(BigDecimal.valueOf(12.40), "CHF"),
                        new DueDate(LocalDate.now().plusDays(4)));
        given(findCustomerByIdRepository.findCustomer(debtCase.getCustomerId()))
                .willReturn(Optional.of(
                        new Customer(
                                new CustomerId(1229L),
                                new Name("Oskars"),
                                new Surname("Adamovičs"),
                                new Code("28019112458"),
                                new Country("LV"),
                                new Email("adamovics.oskars@inbox.lv"))));
        given(findDebtCaseRepository.findDebtCase(debtCase.getId()))
                .willReturn(Optional.of(
                        new DebtCase(
                                new DebtCaseId(1L),
                                new CustomerId(1229L),
                                new CaseNumber("DC-1234-old"),
                                new Money(BigDecimal.valueOf(12.40), "CHF"),
                                new DueDate(LocalDate.now().plusDays(4)))));
        given(findDebtCaseByNumberRepository.findDebtCase(debtCase.getNumber()))
                .willReturn(Optional.empty());
        given(editDebtCaseRepository.editDebtCase(debtCase))
                .willReturn(debtCase);

        //when
        DebtCase debtCaseUpdated = assertDoesNotThrow(() -> editDebtCaseUseCase.editDebtCase(debtCase));

        //then
        then(findCustomerByIdRepository).should().findCustomer(debtCase.getCustomerId());
        then(findDebtCaseRepository).should().findDebtCase(debtCase.getId());
        then(findDebtCaseByNumberRepository).should().findDebtCase(debtCase.getNumber());
        then(editDebtCaseRepository).should().editDebtCase(debtCase);
        assertAll(
                () -> assertThat(debtCaseUpdated).isNotNull(),
                () -> assertThat(debtCaseUpdated.getNumber()).isNotNull(),
                () -> assertThat(debtCaseUpdated.getNumber().getValue()).isEqualTo("DC-1234")
        );
    }
}