package com.intrum.creditmanagementservice.adapters.outputs.jpa.repositories;

import com.intrum.creditmanagementservice.core.domains.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@Import(DebtCaseRepository.class)
@DisplayName("Debt case repository")
class DebtCaseRepositoryIT {

    @Autowired
    private DebtCaseRepository debtCaseRepository;

    @Test
    @DisplayName("When debt case is found by debt case id")
    void test_1() {
        //given
        DebtCaseId debtCaseId = new DebtCaseId(31L);

        //when
        Optional<DebtCase> debtCaseFound = debtCaseRepository.findDebtCase(debtCaseId);

        //then
        assertTrue(debtCaseFound.isPresent());
        assertThat(debtCaseFound.get().getNumber().getValue()).isEqualTo("DCN-LV-1234");
    }

    @Test
    @DisplayName("When debt case can not be found by debt case id")
    void test_2() {
        //given
        DebtCaseId debtCaseId = new DebtCaseId(1419L);

        //when
        Optional<DebtCase> debtCaseFound = debtCaseRepository.findDebtCase(debtCaseId);

        //then
        assertFalse(debtCaseFound.isPresent());
    }

    @Test
    @DisplayName("When debt case is found by debt case number")
    void test_3() {
        //given
        CaseNumber caseNumber = new CaseNumber("DCN-LV-1234");

        //when
        Optional<DebtCase> debtCaseFound = debtCaseRepository.findDebtCase(caseNumber);

        //then
        assertTrue(debtCaseFound.isPresent());
        assertThat(debtCaseFound.get().getId().getValue()).isEqualTo(31L);
    }

    @Test
    @DisplayName("When debt case can not be found by debt case number")
    void test_4() {
        //given
        CaseNumber caseNumber = new CaseNumber("non-existing-case-number");

        //when
        Optional<DebtCase> debtCaseFound = debtCaseRepository.findDebtCase(caseNumber);

        //then
        assertFalse(debtCaseFound.isPresent());
    }

    @Test
    @DisplayName("When two debt cases are found by customer id")
    void test_5() {
        //given
        CustomerId customerId = new CustomerId(1L);

        //when
        Set<DebtCase> debtCases = debtCaseRepository.obtainDebtCases(customerId);

        //then
        assertThat(debtCases).isNotEmpty().hasSize(2);
    }

    @Test
    @DisplayName("When tried to find debt cases by non existing customer id")
    void test_6() {
        //given
        CustomerId customerId = new CustomerId(1426L);

        //when
        Set<DebtCase> debtCases = debtCaseRepository.obtainDebtCases(customerId);

        //then
        assertThat(debtCases).isEmpty();
    }

    @Test
    @DisplayName("When new debt case is added successfully")
    void test_7() {
        //given
        DebtCase debtCase =
                new DebtCase(
                        new CustomerId(1L),
                        new CaseNumber("one-two-ready"),
                        new Money(BigDecimal.valueOf(14.34), "EUR"),
                        new DueDate(LocalDate.now().plusDays(14)));

        //when
        DebtCase debtCaseAdded = debtCaseRepository.addDebtCase(debtCase);

        //then
        Optional<DebtCase> debtCaseFound = debtCaseRepository.findDebtCase(debtCase.getNumber());
        assertAll(
                () -> assertThat(debtCaseAdded).isNotNull(),
                () -> assertThat(debtCaseAdded.getId()).isNotNull(),
                () -> assertTrue(debtCaseFound.isPresent()),
                () -> assertThat(debtCaseFound.get().getNumber()).isEqualTo(debtCase.getNumber()),
                () -> assertThat(debtCaseFound.get().getMoney()).isEqualTo(debtCase.getMoney())
        );
    }

    @Test
    @DisplayName("When debt case is removed successfully")
    void test_8() {
        //given
        DebtCase debtCase =
                new DebtCase(
                        new CustomerId(1L),
                        new CaseNumber("one-two-ready"),
                        new Money(BigDecimal.valueOf(14.34), "EUR"),
                        new DueDate(LocalDate.now().plusDays(14)));
        DebtCase debtCaseAdded = debtCaseRepository.addDebtCase(debtCase);
        assertThat(debtCaseAdded).isNotNull();

        //when
        int removedRecords = debtCaseRepository.removeDebtCase(debtCaseAdded.getId());

        //then
        Optional<DebtCase> debtCaseFound = debtCaseRepository.findDebtCase(debtCase.getNumber());
        assertAll(
                () -> assertThat(removedRecords).isEqualTo(1),
                () -> assertFalse(debtCaseFound.isPresent())
        );
    }

    @Test
    @DisplayName("When debt case record is updated successfully")
    void test_9() {
        //given
        DebtCase debtCase =
                new DebtCase(
                        new CustomerId(3L),
                        new CaseNumber("PU-1235"),
                        new Money(BigDecimal.valueOf(14.56), "USD"),
                        new DueDate(LocalDate.now().plusDays(10)));
        DebtCase debtCaseAdded = debtCaseRepository.addDebtCase(debtCase);
        assertThat(debtCaseAdded).isNotNull();

        //when
        DebtCase debtCaseUpdated = debtCaseRepository.editDebtCase(
                new DebtCase(
                        debtCaseAdded.getId(),
                        new CustomerId(3L),
                        new CaseNumber("PU-1235"),
                        new Money(BigDecimal.valueOf(21.99), "EUR"),
                        new DueDate(LocalDate.now().plusDays(10))));

        //then
        assertAll(
                () -> assertThat(debtCaseUpdated).isNotNull(),
                () -> assertThat(debtCaseUpdated.getMoney()).isEqualTo(new Money(BigDecimal.valueOf(21.99), "EUR"))
        );
    }
}