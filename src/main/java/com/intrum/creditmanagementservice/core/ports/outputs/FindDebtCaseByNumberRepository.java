package com.intrum.creditmanagementservice.core.ports.outputs;

import com.intrum.creditmanagementservice.core.domains.CaseNumber;
import com.intrum.creditmanagementservice.core.domains.DebtCase;

import java.util.Optional;

public interface FindDebtCaseByNumberRepository {
    Optional<DebtCase> findDebtCase(CaseNumber caseNumber);
}
