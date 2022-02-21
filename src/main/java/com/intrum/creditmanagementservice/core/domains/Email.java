package com.intrum.creditmanagementservice.core.domains;

import com.intrum.creditmanagementservice.core.CoreException;
import com.intrum.creditmanagementservice.core.utils.StringUtility;

import java.util.Objects;

import static com.intrum.creditmanagementservice.core.domains.enums.ErrorMessage.*;

public class Email {
    private String value;

    public Email(String value) {
        this.setValue(value);
    }

    public String getValue() {
        return value;
    }

    private void setValue(String value) {
        if (StringUtility.isBlank(value))
            throw CoreException.createException(BLANK_EMAIL, Email.class);
        if (value.length() > StringUtility.MAX_TEXT_LENGTH_50)
            throw CoreException.createException(MAX_LENGTH_50, Email.class);
        if (StringUtility.isNotValidEmail(value.trim()))
            throw CoreException.createException(WRONG_EMAIL_ADDRESS, Email.class);

        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Email email = (Email) o;
        return Objects.equals(value, email.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public String toString() {
        return "Email{" +
                "value='" + value + '\'' +
                '}';
    }
}
