package com.intrum.creditmanagementservice.adapters.inputs.rest.modules;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Accessors(chain = true)
public class DebtCaseDto {

    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private Long id;

    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private Long customerId;

    @Schema(description = "Debt case number", required = true, example = "DCN-1234", maxLength = 50)
    private String number;

    @Schema(required = true, example = "13.36")
    private BigDecimal amount;

    @Schema(description = "Currency code using ISO-4217 standard", required = true, example = "EUR", maxLength = 3)
    private String currency;

    @Schema(required = true)
    private LocalDate dueDate;
}
