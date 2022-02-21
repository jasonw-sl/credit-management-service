package com.intrum.creditmanagementservice.core.domains;

import com.intrum.creditmanagementservice.core.CoreException;
import com.intrum.creditmanagementservice.core.utils.StringUtility;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Objects;

import static com.intrum.creditmanagementservice.core.domains.enums.ErrorMessage.*;

public class Password {
    private String value;

    public Password(String value) {
        this.setValue(value);
    }

    public String getValue() {
        return value;
    }

    private void setValue(String value) {
        if (StringUtility.isBlank(value))
            throw CoreException.createException(BLANK_PASSWORD, Password.class);
        if (value.length() > StringUtility.MAX_TEXT_LENGTH_50)
            throw CoreException.createException(MAX_LENGTH_50, Password.class);

        this.value = value;
    }

    public boolean hashMatch(Password password) {
        return this.value.equals(password.getValue());
    }

    public void simplePasswordEncryptor() {
        this.value = Base64.getEncoder().encodeToString(value.getBytes(StandardCharsets.UTF_8));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Password password = (Password) o;
        return Objects.equals(value, password.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public String toString() {
        return "Password is ***********";
    }
}
