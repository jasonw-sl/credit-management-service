package com.intrum.creditmanagementservice.core.domains;

import com.intrum.creditmanagementservice.core.CoreException;

import java.util.Objects;

import static com.intrum.creditmanagementservice.core.domains.enums.ErrorMessage.*;

public class DebtCase {
    private DebtCaseId id;
    private CustomerId customerId;
    private CaseNumber number;
    private Money money;
    private DueDate dueDate;

    public DebtCase(CustomerId customerId, CaseNumber caseNumber, Money money, DueDate dueDate) {
        this.setCustomerId(customerId);
        this.setNumber(caseNumber);
        this.setMoney(money);
        this.setDueDate(dueDate);
    }

    public DebtCase(DebtCaseId id, CustomerId customerId, CaseNumber number, Money money, DueDate dueDate) {
        this.setId(id);
        this.setCustomerId(customerId);
        this.setNumber(number);
        this.setMoney(money);
        this.setDueDate(dueDate);
    }

    public DebtCaseId getId() {
        return id;
    }

    public CustomerId getCustomerId() {
        return customerId;
    }

    public CaseNumber getNumber() {
        return number;
    }

    public Money getMoney() {
        return money;
    }

    public DueDate getDueDate() {
        return dueDate;
    }

    private void setId(DebtCaseId id) {
        if (Objects.isNull(id))
            throw CoreException.createException(NO_DEBT_CASE_ID);

        this.id = id;
    }

    private void setCustomerId(CustomerId customerId) {
        if (Objects.isNull(customerId))
            throw CoreException.createException(NO_CUSTOMER_ID);

        this.customerId = customerId;
    }

    private void setNumber(CaseNumber number) {
        if (Objects.isNull(number))
            throw CoreException.createException(NO_CASE_NUMBER);

        this.number = number;
    }

    private void setMoney(Money money) {
        if (Objects.isNull(money))
            throw CoreException.createException(NO_MONEY);

        this.money = money;
    }

    private void setDueDate(DueDate dueDate) {
        if (Objects.isNull(dueDate))
            throw CoreException.createException(NO_DUE_DATE);

        this.dueDate = dueDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DebtCase debtCase = (DebtCase) o;
        return Objects.equals(id, debtCase.id) && Objects.equals(customerId, debtCase.customerId) && Objects.equals(number, debtCase.number) && Objects.equals(money, debtCase.money) && Objects.equals(dueDate, debtCase.dueDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, customerId, number, money, dueDate);
    }

    @Override
    public String toString() {
        return "DebtCase{" +
                "id=" + id +
                ", customerId=" + customerId +
                ", number=" + number +
                ", money=" + money +
                ", dueDate=" + dueDate +
                '}';
    }
}
