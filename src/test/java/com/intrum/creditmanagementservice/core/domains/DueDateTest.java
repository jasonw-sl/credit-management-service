package com.intrum.creditmanagementservice.core.domains;

import com.intrum.creditmanagementservice.core.CoreException;
import com.intrum.creditmanagementservice.core.domains.enums.Status;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static com.intrum.creditmanagementservice.core.domains.enums.ErrorMessage.DUE_DATE_IS_IN_PAST;
import static com.intrum.creditmanagementservice.core.domains.enums.ErrorMessage.NO_DUE_DATE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Due date value object test")
class DueDateTest {

    @Test
    @DisplayName("When due date is not given")
    void test_1() {
        //given
        LocalDate value = null;

        //when
        CoreException coreException = assertThrows(CoreException.class, () -> new DueDate(value));

        //then
        assertAll(
                () -> assertThat(coreException).isNotNull(),
                () -> assertThat(coreException.getStatus()).isEqualTo(Status.VALIDATION_FAIL),
                () -> assertThat(coreException.getObject()).isEqualTo("DueDate"),
                () -> assertThat(coreException.getErrorMessage()).isEqualTo(NO_DUE_DATE)
        );
    }

    @Test
    @DisplayName("When due date is in the past")
    void test_2() {
        //given
        LocalDate value = LocalDate.now().minusDays(1);

        //when
        CoreException coreException = assertThrows(CoreException.class, () -> new DueDate(value));

        //then
        assertAll(
                () -> assertThat(coreException).isNotNull(),
                () -> assertThat(coreException.getStatus()).isEqualTo(Status.VALIDATION_FAIL),
                () -> assertThat(coreException.getObject()).isEqualTo("DueDate"),
                () -> assertThat(coreException.getErrorMessage()).isEqualTo(DUE_DATE_IS_IN_PAST)
        );
    }

    @Test
    @DisplayName("When due date is current system date")
    void test_3() {
        //given
        LocalDate value = LocalDate.now();

        //when
        DueDate dueDate = assertDoesNotThrow(() -> new DueDate(value));

        //then
        assertThat(dueDate).isEqualTo(new DueDate(value));
    }

    @Test
    @DisplayName("When due date is in future")
    void test_4() {
        //given
        LocalDate value = LocalDate.now().plusDays(15);

        //when
        DueDate dueDate = assertDoesNotThrow(() -> new DueDate(value));

        //then
        assertThat(dueDate).isEqualTo(new DueDate(value));
    }
}