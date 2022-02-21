package com.intrum.creditmanagementservice.core.ports.inputs;

import com.intrum.creditmanagementservice.core.domains.DebtCaseId;

public interface RemoveDebtCaseUseCase {
    void removeDebtCase(DebtCaseId debtCaseId);
}
