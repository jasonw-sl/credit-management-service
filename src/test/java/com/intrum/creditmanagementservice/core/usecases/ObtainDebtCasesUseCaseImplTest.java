package com.intrum.creditmanagementservice.core.usecases;

import com.intrum.creditmanagementservice.core.CoreException;
import com.intrum.creditmanagementservice.core.domains.*;
import com.intrum.creditmanagementservice.core.domains.enums.Status;
import com.intrum.creditmanagementservice.core.ports.outputs.ObtainDebtCasesRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static com.intrum.creditmanagementservice.core.domains.enums.ErrorMessage.DEBT_CASES_NOT_FOUND;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

@ExtendWith(MockitoExtension.class)
@DisplayName("Obtain debt cases use case")
class ObtainDebtCasesUseCaseImplTest {

    @Mock
    private ObtainDebtCasesRepository obtainDebtCasesRepository;

    @InjectMocks
    private ObtainDebtCasesUseCaseImpl obtainDebtCasesUseCase;

    @Test
    @DisplayName("When debt cases can not be found in DB")
    void test_1() {
        //given
        CustomerId customerId = new CustomerId(2347L);
        given(obtainDebtCasesRepository.obtainDebtCases(customerId))
                .willReturn(new HashSet<>());

        //when
        CoreException coreException = assertThrows(CoreException.class, () -> obtainDebtCasesUseCase.obtainDebtCases(customerId));

        //then
        then(obtainDebtCasesRepository).should().obtainDebtCases(customerId);
        assertAll(
                () -> assertThat(coreException).isNotNull(),
                () -> assertThat(coreException.getStatus()).isEqualTo(Status.NO_DATA),
                () -> assertThat(coreException.getObject()).isEqualTo("Could not found debt cases by customer ID [2347] in DB!"),
                () -> assertThat(coreException.getErrorMessage()).isEqualTo(DEBT_CASES_NOT_FOUND)
        );
    }

    @Test
    @DisplayName("When two debt cases are found in DB")
    void test_2() {
        //given
        CustomerId customerId = new CustomerId(2350L);
        given(obtainDebtCasesRepository.obtainDebtCases(customerId))
                .willReturn(new HashSet<>(Arrays.asList(
                        new DebtCase(
                                new DebtCaseId(1L),
                                new CustomerId(2350L),
                                new CaseNumber("GT-22"),
                                new Money(BigDecimal.TEN, "EUR"),
                                new DueDate(LocalDate.now().plusDays(5))),
                        new DebtCase(
                                new DebtCaseId(2L),
                                new CustomerId(2350L),
                                new CaseNumber("RTX-3060"),
                                new Money(BigDecimal.valueOf(23.52), "EUR"),
                                new DueDate(LocalDate.now().plusDays(5))))));

        //when
        Set<DebtCase> debtCases = obtainDebtCasesUseCase.obtainDebtCases(customerId);

        //then
        then(obtainDebtCasesRepository).should().obtainDebtCases(customerId);
        assertThat(debtCases).isNotEmpty().hasSize(2);
    }
}