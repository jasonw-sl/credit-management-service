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

@DisplayName("Name value object test")
class NameTest {

    @ParameterizedTest
    @NullAndEmptySource
    @DisplayName("When blank value is given")
    void test_1(String name) {
        //when
        CoreException coreException = assertThrows(CoreException.class, () -> new Name(name));

        //then
        assertAll(
                () -> assertThat(coreException).isNotNull(),
                () -> assertThat(coreException.getStatus()).isEqualTo(Status.VALIDATION_FAIL),
                () -> assertThat(coreException.getObject()).isEqualTo("Name"),
                () -> assertThat(coreException.getErrorMessage()).isEqualTo(BLANK_NAME)
        );
    }

    @Test
    @DisplayName("When name length is more than 50 symbols")
    void test_2() {
        //given
        String name = "name-name-name-name-name-name-name-name-name-name51";

        //when
        CoreException coreException = assertThrows(CoreException.class, () -> new Name(name));

        //then
        assertAll(
                () -> assertThat(coreException).isNotNull(),
                () -> assertThat(coreException.getStatus()).isEqualTo(Status.VALIDATION_FAIL),
                () -> assertThat(coreException.getObject()).isEqualTo("Name"),
                () -> assertThat(coreException.getErrorMessage()).isEqualTo(MAX_LENGTH_50)
        );
    }

    @ParameterizedTest
    @DisplayName("When name contains non alphabetic characters")
    @ValueSource(strings = {"1234", "oskars12", "!@#$%^&*("})
    void test_3(String name) {
        //when
        CoreException coreException = assertThrows(CoreException.class, () -> new Name(name));

        //then
        assertAll(
                () -> assertThat(coreException).isNotNull(),
                () -> assertThat(coreException.getStatus()).isEqualTo(Status.VALIDATION_FAIL),
                () -> assertThat(coreException.getObject()).isEqualTo("Name"),
                () -> assertThat(coreException.getErrorMessage()).isEqualTo(ONLY_ALPHA_CHARACTERS)
        );
    }

    @Test
    @DisplayName("When name length is equal to 50 symbols")
    void test_3() {
        //given
        String input = "oskarsoskarsoskarsoskarsoskarsoskarsoskarsoskarsdd";

        //when
        Name output = assertDoesNotThrow(() -> new Name(input));

        //then
        assertThat(output).isEqualTo(new Name(input));
    }
}