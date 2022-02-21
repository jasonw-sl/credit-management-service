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

@DisplayName("Password value object test")
class PasswordTest {

    @ParameterizedTest
    @NullAndEmptySource
    @DisplayName("When blank value is given")
    void test_1(String password) {
        //when
        CoreException coreException = assertThrows(CoreException.class, () -> new Password(password));

        //then
        assertAll(
                () -> assertThat(coreException).isNotNull(),
                () -> assertThat(coreException.getStatus()).isEqualTo(Status.VALIDATION_FAIL),
                () -> assertThat(coreException.getObject()).isEqualTo("Password"),
                () -> assertThat(coreException.getErrorMessage()).isEqualTo(BLANK_PASSWORD)
        );
    }

    @Test
    @DisplayName("When password length is more than 50 symbols")
    void test_2() {
        //given
        String password = "password1234password1234password1234password1234ddd";

        //when
        CoreException coreException = assertThrows(CoreException.class, () -> new Password(password));

        //then
        assertAll(
                () -> assertThat(coreException).isNotNull(),
                () -> assertThat(coreException.getStatus()).isEqualTo(Status.VALIDATION_FAIL),
                () -> assertThat(coreException.getObject()).isEqualTo("Password"),
                () -> assertThat(coreException.getErrorMessage()).isEqualTo(MAX_LENGTH_50)
        );
    }

    @Test
    @DisplayName("When valid password is given")
    void test_3() {
        //given
        String input = "password1234";

        //when
        Password output = assertDoesNotThrow(() -> new Password(input));

        //then
        assertThat(output).isEqualTo(new Password(input));
    }

    @Test
    @DisplayName("When encrypted password match to it hash")
    void test_4() {
        //given
        String input = "password1234";

        //when
        Password output = assertDoesNotThrow(() -> new Password(input));
        output.simplePasswordEncryptor();

        //then
        assertTrue(output.hashMatch(new Password("cGFzc3dvcmQxMjM0")));
    }

    @Test
    @DisplayName("When encrypted password does not match to it hash")
    void test_5() {
        //given
        String input = "password1234";

        //when
        Password output = assertDoesNotThrow(() -> new Password(input));
        output.simplePasswordEncryptor();

        //then
        assertFalse(output.hashMatch(new Password("wrong-hash")));
    }
}