package com.intrum.creditmanagementservice.core.domains.enums;

public enum ErrorMessage {
    SOMETHING_WENT_WRONG(-1L, "Something went wrong!"),
    NO_REQUEST_DATA(-2L, "No request data given!"),
    NO_CUSTOMER_DATA(-3L, "No customer data given!"),
    BLANK_NAME(-4L, "Blank name is given!"),
    BLANK_SURNAME(-5L, "Blank surname is given!"),
    BLANK_CODE(-6L, "Blank code is given!"),
    BLANK_COUNTRY_CODE(-7L, "Blank country code is given!"),
    BLANK_EMAIL(-8L, "Blank email is given!"),
    MAX_LENGTH_50(-9L, "Length must not exceed 50 symbols!"),
    ONLY_ALPHA_CHARACTERS(-10L, "Only alpha characters are allowed!"),
    MAX_LENGTH_20(-11L, "Length must not exceed 20 symbols!"),
    ONLY_NUMBER_CHARACTERS(-12L, "Only numbers characters are allowed!"),
    WRONG_COUNTRY_CODE(-13L, "Wrong country code, is not ISO 3166-1 alpha-2 code!"),
    WRONG_EMAIL_ADDRESS(-14L, "Wrong email address is given!"),
    CUSTOMER_EXIST(-15L, "Customer exist in DB!"),
    NO_ID(-16L, "ID is not given!"),
    ID_MUST_BE_POSITIVE(-17L, "ID must be a positive number!"),
    CUSTOMER_NOT_FOUND(-18L, "Could not found customer data in DB!"),
    NO_DEBT_CASE_DATA(-19L, "No debt case data given!"),
    NO_DUE_DATE(-20L, "No due date given!"),
    NO_AMOUNT(-21L, "No amount given!"),
    AMOUNT_MUST_BE_POSITIVE(-22L, "Amount must be a positive number!"),
    MAX_TWO_DECIMAL_PLACES(-23L, "Max two decimal places after dot is supported!"),
    BLANK_CURRENCY_CODE(-24L, "Blank currency code given!"),
    WRONG_CURRENCY_CODE(-25L, "Wrong currency code is given!"),
    DUE_DATE_IS_IN_PAST(-26L, "Wrong due date, the date is in the past!"),
    DEBT_CASE_EXIST(-27L, "Debt case exist in DB!"),
    USER_EXIST(-28L, "User exist in DB!"),
    DEBT_CASES_NOT_FOUND(-29L, "Could not found debt cases data in DB!"),
    DEBT_CASE_NOT_FOUND(-30L, "Could not found debt case data in DB!"),
    USER_NOT_FOUND(-31L, "Could not found user data in DB!"),
    NO_USER_DATA(-32L, "User data is not given!"),
    BLANK_USERNAME(-33L, "Blank username is given!"),
    BLANK_PASSWORD(-34L, "Blank password is given!"),
    NO_CUSTOMER_ID(-35L, "No customer id is given!"),
    NO_NAME(-36L, "No name is given!"),
    NO_SURNAME(-37L, "No surname is given!"),
    NO_CODE(-38L, "No code is given!"),
    NO_COUNTRY(-39L, "No country is given!"),
    NO_EMAIL(-40L, "No email is given!"),
    BLANK_NUMBER(-41L, "Blank number is given!"),
    NO_DEBT_CASE_ID(-42L, "No debt case id is given!"),
    NO_CASE_NUMBER(-43L, "No case number is given!"),
    NO_MONEY(-44L, "No money is given!"),
    NO_USERNAME(-45L, "No username is given!"),
    NO_PASSWORD(-46L, "No password is given!");

    private final Long code;
    private final String text;

    ErrorMessage(Long code, String text) {
        this.code = code;
        this.text = text;
    }

    public Long getCode() {
        return code;
    }

    public String getText() {
        return text;
    }

    @Override
    public String toString() {
        return "ErrorMessage{" +
                "code=" + code +
                ", text='" + text + '\'' +
                '}';
    }
}
