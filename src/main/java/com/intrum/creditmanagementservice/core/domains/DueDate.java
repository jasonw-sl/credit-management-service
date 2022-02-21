package com.intrum.creditmanagementservice.core.domains;

import com.intrum.creditmanagementservice.core.CoreException;

import java.time.LocalDate;
import java.util.Objects;

import static com.intrum.creditmanagementservice.core.domains.enums.ErrorMessage.DUE_DATE_IS_IN_PAST;
import static com.intrum.creditmanagementservice.core.domains.enums.ErrorMessage.NO_DUE_DATE;

public class DueDate {
    private LocalDate value;

    public DueDate(LocalDate value) {
        this.setValue(value);
    }

    public LocalDate getValue() {
        return value;
    }

    private void setValue(LocalDate value) {
        if (Objects.isNull(value))
            throw CoreException.createException(NO_DUE_DATE, DueDate.class);

        if (value.isBefore(LocalDate.now())) {
                throw CoreException.createException(DUE_DATE_IS_IN_PAST, DueDate.class);
        }

        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DueDate dueDate = (DueDate) o;
        return Objects.equals(value, dueDate.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public String toString() {
        return "DueDate{" +
                "value=" + value +
                '}';
    }
}
