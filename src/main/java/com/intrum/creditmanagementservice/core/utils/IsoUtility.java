package com.intrum.creditmanagementservice.core.utils;

import org.apache.commons.lang3.StringUtils;

import java.util.*;

public final class IsoUtility {
    private static final Set<String> ISO_COUNTRIES = new HashSet<>(Arrays.asList(Locale.getISOCountries()));
    private static final Set<Currency> ISO_CURRENCIES = Currency.getAvailableCurrencies();

    public static boolean isValidISOCountryIgnoreCase(final String value) {
        if (StringUtils.isBlank(value)) return false;
        if (value.length() != 2) return false;
        return ISO_COUNTRIES.contains(value.toUpperCase());
    }

    public static boolean isNotValidISOCountryIgnoreCase(final String value) {
        return !IsoUtility.isValidISOCountryIgnoreCase(value);
    }

    public static boolean isNotValidISOCurrencyCodeIgnoreCase(final String value) {
        if (StringUtils.isBlank(value)) return true;
        if (value.length() != 3) return true;
        return ISO_CURRENCIES.stream().noneMatch(c -> c.getCurrencyCode().equalsIgnoreCase(value));
    }

    private IsoUtility() throws IllegalAccessException {
        throw new IllegalAccessException();
    }

}