package com.intrum.creditmanagementservice.core.ports.inputs;

import com.intrum.creditmanagementservice.core.domains.DebtCase;

public interface AddDebtCaseUseCase {
    DebtCase addDebtCase(DebtCase debtCase);
}
