package com.intrum.creditmanagementservice.core.domains;

import com.intrum.creditmanagementservice.core.CoreException;
import com.intrum.creditmanagementservice.core.domains.enums.Status;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static com.intrum.creditmanagementservice.core.domains.enums.ErrorMessage.ID_MUST_BE_POSITIVE;
import static com.intrum.creditmanagementservice.core.domains.enums.ErrorMessage.NO_ID;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Debt case id value object test")
class DebtCaseIdTest {

    @Test
    @DisplayName("When id value is not given")
    void test_1() {
        //given
        Long id = null;

        //when
        CoreException coreException = assertThrows(CoreException.class, () -> new DebtCaseId(id));

        //then
        assertAll(
                () -> assertThat(coreException).isNotNull(),
                () -> assertThat(coreException.getStatus()).isEqualTo(Status.VALIDATION_FAIL),
                () -> assertThat(coreException.getObject()).isNull(),
                () -> assertThat(coreException.getErrorMessage()).isEqualTo(NO_ID)
        );
    }

    @ParameterizedTest
    @DisplayName("When given id value is not positive number")
    @ValueSource(longs = {-1519, 0})
    void test_1(Long id) {
        //when
        CoreException coreException = assertThrows(CoreException.class, () -> new DebtCaseId(id));

        //then
        assertAll(
                () -> assertThat(coreException).isNotNull(),
                () -> assertThat(coreException.getStatus()).isEqualTo(Status.VALIDATION_FAIL),
                () -> assertThat(coreException.getObject()).isEqualTo("DebtCaseId"),
                () -> assertThat(coreException.getErrorMessage()).isEqualTo(ID_MUST_BE_POSITIVE)
        );
    }

    @Test
    @DisplayName("When valid id value is given")
    void test_3() {
        //given
        Long id = 1519L;

        //when
        DebtCaseId debtCaseId = assertDoesNotThrow(() -> new DebtCaseId(id));

        //then
        assertThat(debtCaseId).isEqualTo(new DebtCaseId(id));
    }
}