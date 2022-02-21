package com.intrum.creditmanagementservice.core.ports.outputs;

import com.intrum.creditmanagementservice.core.domains.DebtCase;
import com.intrum.creditmanagementservice.core.domains.DebtCaseId;

import java.util.Optional;

public interface FindDebtCaseRepository {
    Optional<DebtCase> findDebtCase(DebtCaseId debtCaseId);
}
