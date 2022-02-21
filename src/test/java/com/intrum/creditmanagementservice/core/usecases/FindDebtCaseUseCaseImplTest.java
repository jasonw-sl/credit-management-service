package com.intrum.creditmanagementservice.core.usecases;

import com.intrum.creditmanagementservice.core.CoreException;
import com.intrum.creditmanagementservice.core.domains.*;
import com.intrum.creditmanagementservice.core.domains.enums.Status;
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

import static com.intrum.creditmanagementservice.core.domains.enums.ErrorMessage.DEBT_CASE_NOT_FOUND;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

@ExtendWith(MockitoExtension.class)
@DisplayName("Find debt case use case")
class FindDebtCaseUseCaseImplTest {

    @Mock
    private FindDebtCaseRepository findDebtCaseRepository;

    @InjectMocks
    private FindDebtCaseUseCaseImpl findDebtCaseUseCase;

    @Test
    @DisplayName("When debt case can not be found in DB")
    void test_1() {
        //given
        DebtCaseId debtCaseId = new DebtCaseId(2254L);
        given(findDebtCaseRepository.findDebtCase(debtCaseId))
                .willReturn(Optional.empty());

        //when
        CoreException coreException = assertThrows(CoreException.class, () -> findDebtCaseUseCase.findDebtCase(debtCaseId));

        //then
        then(findDebtCaseRepository).should().findDebtCase(debtCaseId);
        assertAll(
                () -> assertThat(coreException).isNotNull(),
                () -> assertThat(coreException.getStatus()).isEqualTo(Status.NO_DATA),
                () -> assertThat(coreException.getObject()).isEqualTo("Could not found debt case by debt case ID [2254] in DB!"),
                () -> assertThat(coreException.getErrorMessage()).isEqualTo(DEBT_CASE_NOT_FOUND)
        );
    }

    @Test
    @DisplayName("When debt case is found in DB")
    void test_2() {
        //given
        DebtCaseId debtCaseId = new DebtCaseId(2256L);
        given(findDebtCaseRepository.findDebtCase(debtCaseId))
                .willReturn(Optional.of(
                        new DebtCase(
                                new DebtCaseId(2256L),
                                new CustomerId(123L),
                                new CaseNumber("GT-22"),
                                new Money(BigDecimal.TEN, "EUR"),
                                new DueDate(LocalDate.now().plusDays(5)))));

        //when
        DebtCase response = findDebtCaseUseCase.findDebtCase(debtCaseId);

        //then
        then(findDebtCaseRepository).should().findDebtCase(debtCaseId);
        assertAll(
                () -> assertThat(response).isNotNull(),
                () -> assertThat(response.getId()).isNotNull(),
                () -> assertThat(response.getId().getValue()).isEqualTo(2256L),
                () -> assertThat(response.getNumber().getValue()).isEqualTo("GT-22")
        );
    }
}