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

@DisplayName("Code value object test")
class CodeTest {

    @ParameterizedTest
    @NullAndEmptySource
    @DisplayName("When blank value is given")
    void test_1(String code) {
        //when
        CoreException coreException = assertThrows(CoreException.class, () -> new Code(code));

        //then
        assertAll(
                () -> assertThat(coreException).isNotNull(),
                () -> assertThat(coreException.getStatus()).isEqualTo(Status.VALIDATION_FAIL),
                () -> assertThat(coreException.getObject()).isEqualTo("Code"),
                () -> assertThat(coreException.getErrorMessage()).isEqualTo(BLANK_CODE)
        );
    }

    @Test
    @DisplayName("When code length is more than 20 symbols")
    void test_2() {
        //given
        String code = "codecodecodecodecodeA";

        //when
        CoreException coreException = assertThrows(CoreException.class, () -> new Code(code));

        //then
        assertAll(
                () -> assertThat(coreException).isNotNull(),
                () -> assertThat(coreException.getStatus()).isEqualTo(Status.VALIDATION_FAIL),
                () -> assertThat(coreException.getObject()).isEqualTo("Code"),
                () -> assertThat(coreException.getErrorMessage()).isEqualTo(MAX_LENGTH_20)
        );
    }

    @ParameterizedTest
    @DisplayName("When code contains non numeric characters")
    @ValueSource(strings = {"12a34", "oskars12", "!@#$%^&*("})
    void test_3(String code) {
        //when
        CoreException coreException = assertThrows(CoreException.class, () -> new Code(code));

        //then
        assertAll(
                () -> assertThat(coreException).isNotNull(),
                () -> assertThat(coreException.getStatus()).isEqualTo(Status.VALIDATION_FAIL),
                () -> assertThat(coreException.getObject()).isEqualTo("Code"),
                () -> assertThat(coreException.getErrorMessage()).isEqualTo(ONLY_NUMBER_CHARACTERS)
        );
    }

    @Test
    @DisplayName("When code length is equal to 20 symbols")
    void test_4() {
        //given
        String code = "27141505027141505012";

        //when
        Code output = assertDoesNotThrow(() -> new Code(code));

        //then
        assertThat(output).isEqualTo(new Code(code));
    }
}