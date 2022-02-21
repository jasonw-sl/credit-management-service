package com.intrum.creditmanagementservice.adapters.inputs.rest.modules;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class EditDebtCaseRequest {

    @Schema(required = true)
    private DebtCaseDto debtCase;
}
