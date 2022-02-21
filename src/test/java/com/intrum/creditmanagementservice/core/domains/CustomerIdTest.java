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

@DisplayName("Customer ID value object test")
class CustomerIdTest {

    @Test
    @DisplayName("When id value is not given")
    void test_1() {
        //given
        Long id = null;

        //when
        CoreException coreException = assertThrows(CoreException.class, () -> new CustomerId(id));

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
    @ValueSource(longs = {-12, 0})
    void test_1(Long id) {
        //when
        CoreException coreException = assertThrows(CoreException.class, () -> new CustomerId(id));

        //then
        assertAll(
                () -> assertThat(coreException).isNotNull(),
                () -> assertThat(coreException.getStatus()).isEqualTo(Status.VALIDATION_FAIL),
                () -> assertThat(coreException.getObject()).isEqualTo("CustomerId"),
                () -> assertThat(coreException.getErrorMessage()).isEqualTo(ID_MUST_BE_POSITIVE)
        );
    }

    @Test
    @DisplayName("When valid id value is given")
    void test_3() {
        //given
        Long id = 1431L;

        //when
        CustomerId customerId = assertDoesNotThrow(() -> new CustomerId(id));

        //then
        assertThat(customerId).isEqualTo(new CustomerId(id));
    }
}