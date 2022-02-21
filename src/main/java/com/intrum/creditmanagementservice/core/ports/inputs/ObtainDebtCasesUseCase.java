package com.intrum.creditmanagementservice.core.ports.inputs;

import com.intrum.creditmanagementservice.core.domains.CustomerId;
import com.intrum.creditmanagementservice.core.domains.DebtCase;

import java.util.Set;

public interface ObtainDebtCasesUseCase {
    Set<DebtCase> obtainDebtCases(CustomerId customerId);
}
