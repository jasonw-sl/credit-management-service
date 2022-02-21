package com.intrum.creditmanagementservice.core.domains;

import com.intrum.creditmanagementservice.core.CoreException;
import com.intrum.creditmanagementservice.core.utils.StringUtility;

import java.util.Objects;

import static com.intrum.creditmanagementservice.core.domains.enums.ErrorMessage.*;

public class CaseNumber {
    private String value;

    public CaseNumber(String value) {
        this.setValue(value);
    }

    public String getValue() {
        return value;
    }

    private void setValue(String value) {
        if (StringUtility.isBlank(value))
            throw CoreException.createException(BLANK_NUMBER, CaseNumber.class);
        if (value.length() > StringUtility.MAX_TEXT_LENGTH_50)
            throw CoreException.createException(MAX_LENGTH_50, CaseNumber.class);

        this.value = value.toUpperCase();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CaseNumber that = (CaseNumber) o;
        return Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public String toString() {
        return "CaseNumber{" +
                "value='" + value + '\'' +
                '}';
    }
}
