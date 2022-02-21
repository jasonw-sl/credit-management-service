package com.intrum.creditmanagementservice.core.domains;

import com.intrum.creditmanagementservice.core.CoreException;

import java.util.Objects;

import static com.intrum.creditmanagementservice.core.domains.enums.ErrorMessage.ID_MUST_BE_POSITIVE;
import static com.intrum.creditmanagementservice.core.domains.enums.ErrorMessage.NO_ID;

public abstract class AbstractId {
    private Long value;
    private Class<?> clazz;

    protected AbstractId(Long value, Class<?> clazz) {
        this.setClazz(clazz);
        this.setValue(value);
    }

    public Long getValue() {
        return value;
    }

    private void setValue(Long value) {
        if (Objects.isNull(value))
            throw CoreException.createException(NO_ID);
        if (value <= 0)
            throw CoreException.createException(ID_MUST_BE_POSITIVE, clazz);

        this.value = value;
    }

    private void setClazz(Class<?> clazz) {
        this.clazz = Objects.requireNonNull(clazz);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AbstractId that = (AbstractId) o;
        return Objects.equals(value, that.value) && Objects.equals(clazz, that.clazz);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value, clazz);
    }

    @Override
    public String toString() {
        return "AbstractId{" +
                "value=" + value + '}';
    }
}
