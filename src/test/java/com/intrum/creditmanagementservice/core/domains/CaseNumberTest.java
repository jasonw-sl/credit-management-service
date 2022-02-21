package com.intrum.creditmanagementservice.core.domains;

import com.intrum.creditmanagementservice.core.CoreException;
import com.intrum.creditmanagementservice.core.domains.enums.Status;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;

import static com.intrum.creditmanagementservice.core.domains.enums.ErrorMessage.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Case number value object test")
class CaseNumberTest {

    @ParameterizedTest
    @NullAndEmptySource
    @DisplayName("When blank value is given")
    void test_1(String name) {
        //when
        CoreException coreException = assertThrows(CoreException.class, () -> new CaseNumber(name));

        //then
        assertAll(
                () -> assertThat(coreException).isNotNull(),
                () -> assertThat(coreException.getStatus()).isEqualTo(Status.VALIDATION_FAIL),
                () -> assertThat(coreException.getObject()).isEqualTo("CaseNumber"),
                () -> assertThat(coreException.getErrorMessage()).isEqualTo(BLANK_NUMBER)
        );
    }

    @Test
    @DisplayName("When case number length is more than 50 symbols")
    void test_2() {
        //given
        String number = "case-numbercase-numbercase-numbercase-numbercaseZAB";

        //when
        CoreException coreException = assertThrows(CoreException.class, () -> new CaseNumber(number));

        //then
        assertAll(
                () -> assertThat(coreException).isNotNull(),
                () -> assertThat(coreException.getStatus()).isEqualTo(Status.VALIDATION_FAIL),
                () -> assertThat(coreException.getObject()).isEqualTo("CaseNumber"),
                () -> assertThat(coreException.getErrorMessage()).isEqualTo(MAX_LENGTH_50)
        );
    }

    @Test
    @DisplayName("When case number length is equal to 50 symbols")
    void test_3() {
        //given
        String input = "case-number-1234case-number-1234case-number-1234db";

        //when
        CaseNumber output = assertDoesNotThrow(() -> new CaseNumber(input));

        //then
        assertThat(output).isEqualTo(new CaseNumber(input));
    }
}