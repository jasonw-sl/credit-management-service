package com.intrum.creditmanagementservice.core.ports.outputs;

import com.intrum.creditmanagementservice.core.domains.DebtCaseId;

public interface RemoveDebtCaseRepository {
    int removeDebtCase(DebtCaseId debtCaseId);
}
