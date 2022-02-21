package com.intrum.creditmanagementservice.core.domains;

import com.intrum.creditmanagementservice.core.CoreException;

import java.util.Objects;

import static com.intrum.creditmanagementservice.core.domains.enums.ErrorMessage.*;

public class User {
    private CustomerId customerId;
    private Username username;
    private Password password;

    public User(Username username, Password password) {
        this.setUsername(username);
        this.setPassword(password);
    }

    public User(CustomerId customerId, Username username, Password password) {
        this.setCustomerId(customerId);
        this.setUsername(username);
        this.setPassword(password);
    }

    public Username getUsername() {
        return username;
    }

    public Password getPassword() {
        return password;
    }

    public CustomerId getCustomerId() {
        return customerId;
    }

    private void setUsername(Username username) {
        if (Objects.isNull(username))
            throw CoreException.createException(NO_USERNAME);

        this.username = username;
    }

    private void setPassword(Password password) {
        if (Objects.isNull(password))
            throw CoreException.createException(NO_PASSWORD);

        this.password = password;
    }

    private void setCustomerId(CustomerId customerId) {
        if (Objects.isNull(customerId))
            throw CoreException.createException(NO_CUSTOMER_ID);

        this.customerId = customerId;
    }

    public void encryptPassword() {
        this.password.simplePasswordEncryptor();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(customerId, user.customerId) && Objects.equals(username, user.username) && Objects.equals(password, user.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(customerId, username, password);
    }

    @Override
    public String toString() {
        return "User{" +
                "customerId=" + customerId +
                ", username=" + username +
                ", password=" + password +
                '}';
    }
}
