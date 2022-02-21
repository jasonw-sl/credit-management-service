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

@DisplayName("Email value object test")
class EmailTest {

    @ParameterizedTest
    @NullAndEmptySource
    @DisplayName("When blank value is given")
    void test_1(String email) {
        //when
        CoreException coreException = assertThrows(CoreException.class, () -> new Email(email));

        //then
        assertAll(
                () -> assertThat(coreException).isNotNull(),
                () -> assertThat(coreException.getStatus()).isEqualTo(Status.VALIDATION_FAIL),
                () -> assertThat(coreException.getObject()).isEqualTo("Email"),
                () -> assertThat(coreException.getErrorMessage()).isEqualTo(BLANK_EMAIL)
        );
    }

    @Test
    @DisplayName("When email length is more than 50 symbols")
    void test_2() {
        //given
        String email = "emailemailemailemailemailemailemailemailemailemailS";

        //when
        CoreException coreException = assertThrows(CoreException.class, () -> new Email(email));

        //then
        assertAll(
                () -> assertThat(coreException).isNotNull(),
                () -> assertThat(coreException.getStatus()).isEqualTo(Status.VALIDATION_FAIL),
                () -> assertThat(coreException.getObject()).isEqualTo("Email"),
                () -> assertThat(coreException.getErrorMessage()).isEqualTo(MAX_LENGTH_50)
        );
    }

    @ParameterizedTest
    @DisplayName("When wrong email structure is given")
    @ValueSource(strings = {"name.surname", "name@", "@domain.lv", "name@domain"})
    void test_3(String email) {
        //when
        CoreException coreException = assertThrows(CoreException.class, () -> new Email(email));

        //then
        assertAll(
                () -> assertThat(coreException).isNotNull(),
                () -> assertThat(coreException.getStatus()).isEqualTo(Status.VALIDATION_FAIL),
                () -> assertThat(coreException.getObject()).isEqualTo("Email"),
                () -> assertThat(coreException.getErrorMessage()).isEqualTo(WRONG_EMAIL_ADDRESS)
        );
    }

    @Test
    @DisplayName("When valid email is given")
    void test_4() {
        //given
        String input = "adamovics.oskars@inbox.lv";

        //when
        Email output = assertDoesNotThrow(() -> new Email(input));

        //then
        assertThat(output).isEqualTo(new Email(input));
    }
}