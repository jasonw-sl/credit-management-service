package com.intrum.creditmanagementservice.core;

import com.intrum.creditmanagementservice.core.domains.enums.ErrorMessage;
import com.intrum.creditmanagementservice.core.domains.enums.Status;

import java.util.Objects;

public class CoreException extends RuntimeException {
    private final Status status;
    private final ErrorMessage errorMessage;
    private final String object;

    private CoreException(Status status, ErrorMessage errorMessage, String object) {
        super(errorMessage.getText());
        this.status = status;
        this.errorMessage = errorMessage;
        this.object = object;
    }

    public static CoreException createException(ErrorMessage errorMessage) {
        return new CoreException(Status.VALIDATION_FAIL, errorMessage, null);
    }

    public static CoreException createException(ErrorMessage errorMessage, Status status) {
        return new CoreException(status, errorMessage, null);
    }

    public static CoreException createException(ErrorMessage errorMessage, Class<?> clazz) {
        return new CoreException(Status.VALIDATION_FAIL, errorMessage, clazz.getSimpleName());
    }

    public static CoreException createException(ErrorMessage errorMessage, String object) {
        return new CoreException(Status.VALIDATION_FAIL, errorMessage, object);
    }

    public static CoreException createException(ErrorMessage errorMessage, String object, Status status) {
        return new CoreException(status, errorMessage, object);
    }

    public static CoreException createException(ErrorMessage errorMessage, Class<?> clazz, Status status) {
        return new CoreException(status, errorMessage, clazz.getSimpleName());
    }

    public Status getStatus() {
        return status;
    }

    public ErrorMessage getErrorMessage() {
        return errorMessage;
    }

    public String getObject() {
        return object;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CoreException that = (CoreException) o;
        return status == that.status && errorMessage == that.errorMessage && Objects.equals(object, that.object);
    }

    @Override
    public int hashCode() {
        return Objects.hash(status, errorMessage, object);
    }

    @Override
    public String toString() {
        return "CoreException{" +
                "status=" + status +
                ", errorMessage=" + errorMessage +
                ", object='" + object + '\'' +
                '}';
    }
}
