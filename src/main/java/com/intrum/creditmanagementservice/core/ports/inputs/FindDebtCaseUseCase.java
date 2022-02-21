package com.intrum.creditmanagementservice.core.ports.inputs;

import com.intrum.creditmanagementservice.core.domains.DebtCase;
import com.intrum.creditmanagementservice.core.domains.DebtCaseId;

public interface FindDebtCaseUseCase {
    DebtCase findDebtCase(DebtCaseId debtCaseId);
}
