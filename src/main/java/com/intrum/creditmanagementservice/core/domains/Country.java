package com.intrum.creditmanagementservice.core.domains;

import com.intrum.creditmanagementservice.core.CoreException;
import com.intrum.creditmanagementservice.core.utils.IsoUtility;
import com.intrum.creditmanagementservice.core.utils.StringUtility;

import java.util.Objects;

import static com.intrum.creditmanagementservice.core.domains.enums.ErrorMessage.BLANK_COUNTRY_CODE;
import static com.intrum.creditmanagementservice.core.domains.enums.ErrorMessage.WRONG_COUNTRY_CODE;

public class Country {
    private String value;

    public Country(String value) {
        this.setValue(value);
    }

    public String getValue() {
        return value;
    }

    private void setValue(String value) {
        if (StringUtility.isBlank(value))
            throw CoreException.createException(BLANK_COUNTRY_CODE, Country.class);
        if (IsoUtility.isNotValidISOCountryIgnoreCase(value.trim()))
            throw CoreException.createException(WRONG_COUNTRY_CODE, Country.class);

        this.value = value.toUpperCase();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Country country = (Country) o;
        return Objects.equals(value, country.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public String toString() {
        return "Country{" +
                "value='" + value + '\'' +
                '}';
    }
}
