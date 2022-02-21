package com.intrum.creditmanagementservice.core.usecases;

import com.intrum.creditmanagementservice.core.CoreException;
import com.intrum.creditmanagementservice.core.domains.DebtCaseId;
import com.intrum.creditmanagementservice.core.domains.enums.Status;
import com.intrum.creditmanagementservice.core.ports.outputs.RemoveDebtCaseRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.intrum.creditmanagementservice.core.domains.enums.ErrorMessage.DEBT_CASE_NOT_FOUND;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

@ExtendWith(MockitoExtension.class)
@DisplayName("Remove debt case use case")
class RemoveDebtCaseUseCaseImplTest {

    @Mock
    private RemoveDebtCaseRepository removeDebtCaseRepository;

    @InjectMocks
    private RemoveDebtCaseUseCaseImpl removeDebtCaseUseCase;

    @Test
    @DisplayName("When debt case could not be found by debt case id")
    void test_1() {
        //given
        DebtCaseId debtCaseId = new DebtCaseId(2317L);
        given(removeDebtCaseRepository.removeDebtCase(debtCaseId))
                .willReturn(0);

        //when
        CoreException coreException = assertThrows(CoreException.class, () -> removeDebtCaseUseCase.removeDebtCase(debtCaseId));

        //then
        then(removeDebtCaseRepository).should().removeDebtCase(debtCaseId);
        assertAll(
                () -> assertThat(coreException).isNotNull(),
                () -> assertThat(coreException.getStatus()).isEqualTo(Status.NO_DATA),
                () -> assertThat(coreException.getObject()).isEqualTo("Could not found debt case by debt case id [2317] in DB!"),
                () -> assertThat(coreException.getErrorMessage()).isEqualTo(DEBT_CASE_NOT_FOUND)
        );
    }

    @Test
    @DisplayName("When debt case is removed successfully")
    void test_2() {
        //given
        DebtCaseId debtCaseId = new DebtCaseId(2319L);
        given(removeDebtCaseRepository.removeDebtCase(debtCaseId))
                .willReturn(1);

        //when
        assertDoesNotThrow(() -> removeDebtCaseUseCase.removeDebtCase(debtCaseId));

        //then
        then(removeDebtCaseRepository).should().removeDebtCase(debtCaseId);
    }
}