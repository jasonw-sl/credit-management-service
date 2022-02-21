package com.intrum.creditmanagementservice.core.domains;

import com.intrum.creditmanagementservice.core.CoreException;
import com.intrum.creditmanagementservice.core.utils.StringUtility;

import java.util.Objects;
import java.util.regex.Pattern;

import static com.intrum.creditmanagementservice.core.domains.enums.ErrorMessage.*;

public class Code {
    private final Pattern codePattern = Pattern.compile("[0-9]+");
    private String value;

    public Code(String value) {
        this.setValue(value);
    }

    public String getValue() {
        return value;
    }

    private void setValue(String value) {
        if (StringUtility.isBlank(value))
            throw CoreException.createException(BLANK_CODE, Code.class);
        if (value.length() > StringUtility.MAX_TEXT_LENGTH_20)
            throw CoreException.createException(MAX_LENGTH_20, Code.class);
        if (!this.codePattern.matcher(value.trim()).matches())
            throw CoreException.createException(ONLY_NUMBER_CHARACTERS, Code.class);

        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Code code = (Code) o;
        return Objects.equals(value, code.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public String toString() {
        return "Code{" +
                "value='" + value + '\'' +
                '}';
    }
}
