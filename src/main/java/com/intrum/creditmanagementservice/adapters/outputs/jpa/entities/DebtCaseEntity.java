package com.intrum.creditmanagementservice.adapters.outputs.jpa.entities;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Entity
@Accessors(chain = true)
@Table(name = "DEBT_CASES_T")
public class DebtCaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "CUSTOMER_ID", length = 50, nullable = false)
    private Long customerId;

    @Column(name = "NUMBER", length = 50, nullable = false, unique = true)
    private String number;

    @Column(name = "AMOUNT", scale = 2, nullable = false)
    private BigDecimal amount;

    @Column(name = "CURRENCY_CODE", length = 3, nullable = false)
    private String currencyCode;

    @Column(name = "DUE_DATE", nullable = false)
    private LocalDate dueDate;
}
