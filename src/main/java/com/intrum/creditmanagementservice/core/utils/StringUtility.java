package com.intrum.creditmanagementservice.core.utils;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.validator.routines.EmailValidator;

public final class StringUtility {
    public static final int MAX_TEXT_LENGTH_50 = 50;
    public static final int MAX_TEXT_LENGTH_20 = 20;

    public static boolean isBlank(final String value) {
        return StringUtils.isBlank(value);
    }

    private StringUtility() throws IllegalAccessException {
        throw new IllegalAccessException();
    }

    public static boolean isAlphaOnly(final String value) {
        return StringUtils.isAlpha(value);
    }

    public static boolean isNotAlphaOnly(final String value) {
        return !StringUtility.isAlphaOnly(value);
    }

    public static boolean isNotValidEmail(final String value) {
        return !EmailValidator.getInstance().isValid(value);
    }

}
