package com.intrum.creditmanagementservice.core.domains;

import com.intrum.creditmanagementservice.core.CoreException;
import com.intrum.creditmanagementservice.core.domains.enums.Status;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.math.BigDecimal;

import static com.intrum.creditmanagementservice.core.domains.enums.ErrorMessage.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Money value object test")
class MoneyTest {

    @Test
    @DisplayName("When currency code and value are not given")
    void test_1() {
        //given
        BigDecimal value = null;
        String currencyCode = null;

        //when
        CoreException coreException = assertThrows(CoreException.class, () -> new Money(value, currencyCode));

        //then
        assertAll(
                () -> assertThat(coreException).isNotNull(),
                () -> assertThat(coreException.getStatus()).isEqualTo(Status.VALIDATION_FAIL),
                () -> assertThat(coreException.getObject()).isEqualTo("Money"),
                () -> assertThat(coreException.getErrorMessage()).isEqualTo(NO_AMOUNT)
        );
    }

    @Test
    @DisplayName("When currency code is not given")
    void test_2() {
        //given
        BigDecimal value = BigDecimal.TEN;
        String currencyCode = null;

        //when
        CoreException coreException = assertThrows(CoreException.class, () -> new Money(value, currencyCode));

        //then
        assertAll(
                () -> assertThat(coreException).isNotNull(),
                () -> assertThat(coreException.getStatus()).isEqualTo(Status.VALIDATION_FAIL),
                () -> assertThat(coreException.getObject()).isEqualTo("Money"),
                () -> assertThat(coreException.getErrorMessage()).isEqualTo(BLANK_CURRENCY_CODE)
        );
    }

    @ParameterizedTest
    @DisplayName("When amount is not positive")
    @ValueSource(longs = {-10, 0})
    void test_3(long amount) {
        //given
        BigDecimal value = BigDecimal.valueOf(amount);
        String currencyCode = "EUR";

        //when
        CoreException coreException = assertThrows(CoreException.class, () -> new Money(value, currencyCode));

        //then
        assertAll(
                () -> assertThat(coreException).isNotNull(),
                () -> assertThat(coreException.getStatus()).isEqualTo(Status.VALIDATION_FAIL),
                () -> assertThat(coreException.getObject()).isEqualTo("Money"),
                () -> assertThat(coreException.getErrorMessage()).isEqualTo(AMOUNT_MUST_BE_POSITIVE)
        );
    }

    @Test
    @DisplayName("When amount scale is more than two digits")
    void test_4() {
        //given
        BigDecimal value = BigDecimal.valueOf(15.203);
        String currencyCode = "USD";

        //when
        CoreException coreException = assertThrows(CoreException.class, () -> new Money(value, currencyCode));

        //then
        assertAll(
                () -> assertThat(coreException).isNotNull(),
                () -> assertThat(coreException.getStatus()).isEqualTo(Status.VALIDATION_FAIL),
                () -> assertThat(coreException.getObject()).isEqualTo("Money"),
                () -> assertThat(coreException.getErrorMessage()).isEqualTo(MAX_TWO_DECIMAL_PLACES)
        );
    }

    @Test
    @DisplayName("When currency code is not ISO-4217 standard")
    void test_5() {
        //given
        BigDecimal value = BigDecimal.valueOf(15.33);
        String currencyCode = "LAT";

        //when
        CoreException coreException = assertThrows(CoreException.class, () -> new Money(value, currencyCode));

        //then
        assertAll(
                () -> assertThat(coreException).isNotNull(),
                () -> assertThat(coreException.getStatus()).isEqualTo(Status.VALIDATION_FAIL),
                () -> assertThat(coreException.getObject()).isEqualTo("Money"),
                () -> assertThat(coreException.getErrorMessage()).isEqualTo(WRONG_CURRENCY_CODE)
        );
    }

    @Test
    @DisplayName("When valid amount and currency code is given")
    void test_6() {
        //given
        BigDecimal value = BigDecimal.valueOf(15.33);
        String currencyCode = "EUR";

        //when
        Money output = assertDoesNotThrow(() -> new Money(value, currencyCode));

        //then
        assertThat(output).isEqualTo(new Money(value, currencyCode));
    }
}