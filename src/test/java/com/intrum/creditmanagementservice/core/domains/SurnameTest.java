package com.intrum.creditmanagementservice.core.domains;

import com.intrum.creditmanagementservice.core.CoreException;
import com.intrum.creditmanagementservice.core.domains.enums.Status;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import static com.intrum.creditmanagementservice.core.domains.enums.ErrorMessage.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Surname value object test")
class SurnameTest {

    @ParameterizedTest
    @NullAndEmptySource
    @DisplayName("When blank value is given")
    void test_1(String surname) {
        //when
        CoreException coreException = assertThrows(CoreException.class, () -> new Surname(surname));

        //then
        assertAll(
                () -> assertThat(coreException).isNotNull(),
                () -> assertThat(coreException.getStatus()).isEqualTo(Status.VALIDATION_FAIL),
                () -> assertThat(coreException.getObject()).isEqualTo("Surname"),
                () -> assertThat(coreException.getErrorMessage()).isEqualTo(BLANK_SURNAME)
        );
    }

    @Test
    @DisplayName("When surname length is more than 50 symbols")
    void test_2() {
        //given
        String surname = "surnamesurnamesurnamesurnamesurnamesurnamesurnameQW";

        //when
        CoreException coreException = assertThrows(CoreException.class, () -> new Surname(surname));

        //then
        assertAll(
                () -> assertThat(coreException).isNotNull(),
                () -> assertThat(coreException.getStatus()).isEqualTo(Status.VALIDATION_FAIL),
                () -> assertThat(coreException.getObject()).isEqualTo("Surname"),
                () -> assertThat(coreException.getErrorMessage()).isEqualTo(MAX_LENGTH_50)
        );
    }

    @ParameterizedTest
    @DisplayName("When surname contains non alphabetic characters")
    @ValueSource(strings = {"1234", "Adamovi'cs", "!@#$%^&*("})
    void test_3(String surname) {
        //when
        CoreException coreException = assertThrows(CoreException.class, () -> new Surname(surname));

        //then
        assertAll(
                () -> assertThat(coreException).isNotNull(),
                () -> assertThat(coreException.getStatus()).isEqualTo(Status.VALIDATION_FAIL),
                () -> assertThat(coreException.getObject()).isEqualTo("Surname"),
                () -> assertThat(coreException.getErrorMessage()).isEqualTo(ONLY_ALPHA_CHARACTERS)
        );
    }

    @Test
    @DisplayName("When surname length is equal to 50 symbols")
    void test_3() {
        //given
        String input = "adamovičsadamovičsadamovičsadamovičsadamovičsadamV";

        //when
        Surname output = assertDoesNotThrow(() -> new Surname(input));

        //then
        assertThat(output).isEqualTo(new Surname(input));
    }
}