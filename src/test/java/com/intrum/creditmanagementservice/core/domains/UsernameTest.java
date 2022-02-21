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

@DisplayName("Username value object test")
class UsernameTest {

    @ParameterizedTest
    @NullAndEmptySource
    @DisplayName("When blank value is given")
    void test_1(String username) {
        //when
        CoreException coreException = assertThrows(CoreException.class, () -> new Username(username));

        //then
        assertAll(
                () -> assertThat(coreException).isNotNull(),
                () -> assertThat(coreException.getStatus()).isEqualTo(Status.VALIDATION_FAIL),
                () -> assertThat(coreException.getObject()).isEqualTo("Username"),
                () -> assertThat(coreException.getErrorMessage()).isEqualTo(BLANK_USERNAME)
        );
    }

    @Test
    @DisplayName("When username length is more than 50 symbols")
    void test_2() {
        //given
        String username = "UsernameUsernameUsernameUsernameUsernameUsernameABC";

        //when
        CoreException coreException = assertThrows(CoreException.class, () -> new Username(username));

        //then
        assertAll(
                () -> assertThat(coreException).isNotNull(),
                () -> assertThat(coreException.getStatus()).isEqualTo(Status.VALIDATION_FAIL),
                () -> assertThat(coreException.getObject()).isEqualTo("Username"),
                () -> assertThat(coreException.getErrorMessage()).isEqualTo(MAX_LENGTH_50)
        );
    }

    @Test
    @DisplayName("When valid username is given")
    void test_3() {
        //given
        String input = "oskada";

        //when
        Username output = assertDoesNotThrow(() -> new Username(input));

        //then
        assertThat(output).isEqualTo(new Username(input));
    }
}