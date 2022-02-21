package com.intrum.creditmanagementservice.core.domains;

import com.intrum.creditmanagementservice.core.CoreException;
import com.intrum.creditmanagementservice.core.utils.StringUtility;

import java.util.Objects;

import static com.intrum.creditmanagementservice.core.domains.enums.ErrorMessage.*;

public class Surname {
    private String value;

    public Surname(String value) {
        this.setValue(value);
    }

    public String getValue() {
        return value;
    }

    private void setValue(String value) {
        if (StringUtility.isBlank(value))
            throw CoreException.createException(BLANK_SURNAME, Surname.class);
        if (value.length() > StringUtility.MAX_TEXT_LENGTH_50)
            throw CoreException.createException(MAX_LENGTH_50, Surname.class);
        if (StringUtility.isNotAlphaOnly(value.trim()))
            throw CoreException.createException(ONLY_ALPHA_CHARACTERS, Surname.class);

        this.value = value.toUpperCase();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Surname surname = (Surname) o;
        return Objects.equals(value, surname.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public String toString() {
        return "Surname{" +
                "value='" + value + '\'' +
                '}';
    }
}
