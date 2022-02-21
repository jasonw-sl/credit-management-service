package com.intrum.creditmanagementservice.core.domains;

import com.intrum.creditmanagementservice.core.CoreException;
import com.intrum.creditmanagementservice.core.domains.enums.Status;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import static com.intrum.creditmanagementservice.core.domains.enums.ErrorMessage.BLANK_COUNTRY_CODE;
import static com.intrum.creditmanagementservice.core.domains.enums.ErrorMessage.WRONG_COUNTRY_CODE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Country value object test")
class CountryTest {

    @ParameterizedTest
    @NullAndEmptySource
    @DisplayName("When blank value is given")
    void test_1(String country) {
        //when
        CoreException coreException = assertThrows(CoreException.class, () -> new Country(country));

        //then
        assertAll(
                () -> assertThat(coreException).isNotNull(),
                () -> assertThat(coreException.getStatus()).isEqualTo(Status.VALIDATION_FAIL),
                () -> assertThat(coreException.getObject()).isEqualTo("Country"),
                () -> assertThat(coreException.getErrorMessage()).isEqualTo(BLANK_COUNTRY_CODE)
        );
    }

    @ParameterizedTest
    @DisplayName("When country code is not ISO 3166-1 alpha-2 code")
    @ValueSource(strings = {"RR", "12", "!@#$", "LVL"})
    void test_2(String country) {
        //when
        CoreException coreException = assertThrows(CoreException.class, () -> new Country(country));

        //then
        assertAll(
                () -> assertThat(coreException).isNotNull(),
                () -> assertThat(coreException.getStatus()).isEqualTo(Status.VALIDATION_FAIL),
                () -> assertThat(coreException.getObject()).isEqualTo("Country"),
                () -> assertThat(coreException.getErrorMessage()).isEqualTo(WRONG_COUNTRY_CODE)
        );
    }

    @ParameterizedTest
    @DisplayName("When country code code is valid ISO 3166-1 alpha-2 code")
    @ValueSource(strings = {"eE", "Lv", "lt", "PL"})
    void test_3(String countryCode) {
        //when
        Country country = assertDoesNotThrow(() -> new Country(countryCode));

        //then
        assertThat(country).isNotNull();
        assertThat(country.getValue()).isEqualTo(countryCode.toUpperCase());
    }
}