package com.intrum.creditmanagementservice.core.domains.enums;

public enum SuccessMessage {
    CUSTOMER_REMOVED(1L, "Customer has been removed successfully!"),
    DEBT_CASE_REMOVED(2L, "Debt case has been removed successfully!"),
    USER_REGISTERED(3L, "User has been registered successfully!"),
    USER_REMOVED(4L, "User has been removed successfully!");

    private final Long code;
    private final String text;

    SuccessMessage(Long code, String text) {
        this.code = code;
        this.text = text;
    }

    public Long getCode() {
        return code;
    }

    public String getText() {
        return text;
    }
}
