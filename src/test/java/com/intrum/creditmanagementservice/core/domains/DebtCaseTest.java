package com.intrum.creditmanagementservice.core.domains;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Debt case domain object test")
class DebtCaseTest {

    @Test
    @DisplayName("When valid debt case object is given without ID")
    void test_1() {
        //when
        DebtCase debtCase = assertDoesNotThrow(
                () -> new DebtCase(
                        new CustomerId(1541L),
                        new CaseNumber("case-number"),
                        new Money(BigDecimal.TEN, "eur"),
                        new DueDate(LocalDate.now().plusDays(3))));

        //then
        assertAll(
                () -> assertThat(debtCase).isNotNull(),
                () -> assertThat(debtCase.getId()).isNull(),
                () -> assertThat(debtCase.getCustomerId().getValue()).isEqualTo(1541L),
                () -> assertThat(debtCase.getNumber().getValue()).isEqualTo("CASE-NUMBER"),
                () -> assertThat(debtCase.getMoney().getValue()).isEqualTo(BigDecimal.TEN),
                () -> assertThat(debtCase.getMoney().getCurrencyCode()).isEqualTo("EUR"),
                () -> assertThat(debtCase.getDueDate().getValue()).isEqualTo(LocalDate.now().plusDays(3))
        );
    }

    @Test
    @DisplayName("When valid debt case object is given with ID")
    void test_2() {
        //when
        DebtCase debtCase = assertDoesNotThrow(
                () -> new DebtCase(
                        new DebtCaseId(13L),
                        new CustomerId(1541L),
                        new CaseNumber("case-number"),
                        new Money(BigDecimal.TEN, "eur"),
                        new DueDate(LocalDate.now().plusDays(3))));

        //then
        assertAll(
                () -> assertThat(debtCase).isNotNull(),
                () -> assertThat(debtCase.getId().getValue()).isEqualTo(13L),
                () -> assertThat(debtCase.getCustomerId().getValue()).isEqualTo(1541L),
                () -> assertThat(debtCase.getNumber().getValue()).isEqualTo("CASE-NUMBER"),
                () -> assertThat(debtCase.getMoney().getValue()).isEqualTo(BigDecimal.TEN),
                () -> assertThat(debtCase.getMoney().getCurrencyCode()).isEqualTo("EUR"),
                () -> assertThat(debtCase.getDueDate().getValue()).isEqualTo(LocalDate.now().plusDays(3))
        );
    }
}