package com.intrum.creditmanagementservice.core.domains;

import com.intrum.creditmanagementservice.core.CoreException;
import com.intrum.creditmanagementservice.core.utils.IsoUtility;
import com.intrum.creditmanagementservice.core.utils.StringUtility;

import java.math.BigDecimal;
import java.util.Objects;

import static com.intrum.creditmanagementservice.core.domains.enums.ErrorMessage.*;

public class Money {
    private BigDecimal value;
    private String currencyCode;

    public Money(BigDecimal value, String currencyCode) {
        this.setValue(value);
        this.setCurrencyCode(currencyCode);
    }

    private void setValue(BigDecimal value) {
        if (Objects.isNull(value))
            throw CoreException.createException(NO_AMOUNT, Money.class);
        if (value.compareTo(BigDecimal.ZERO) <= 0)
            throw CoreException.createException(AMOUNT_MUST_BE_POSITIVE, Money.class);
        if (value.scale() > 2)
            throw CoreException.createException(MAX_TWO_DECIMAL_PLACES, Money.class);

        this.value = value;
    }

    private void setCurrencyCode(String currencyCode) {
        if (StringUtility.isBlank(currencyCode))
            throw CoreException.createException(BLANK_CURRENCY_CODE, Money.class);

        if (IsoUtility.isNotValidISOCurrencyCodeIgnoreCase(currencyCode))
            throw CoreException.createException(WRONG_CURRENCY_CODE, Money.class);

        this.currencyCode = currencyCode.toUpperCase();
    }

    public BigDecimal getValue() {
        return value;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Money money = (Money) o;
        return Objects.equals(value, money.value) && Objects.equals(currencyCode, money.currencyCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value, currencyCode);
    }

    @Override
    public String toString() {
        return "Money{" +
                "value=" + value +
                ", currencyCode='" + currencyCode + '\'' +
                '}';
    }
}
