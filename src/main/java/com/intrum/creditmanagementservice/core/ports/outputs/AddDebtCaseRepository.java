package com.intrum.creditmanagementservice.core.ports.outputs;

import com.intrum.creditmanagementservice.core.domains.DebtCase;

public interface AddDebtCaseRepository {
    DebtCase addDebtCase(DebtCase debtCase);
}
