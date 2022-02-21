package com.intrum.creditmanagementservice.core.ports.outputs;

import com.intrum.creditmanagementservice.core.domains.CustomerId;
import com.intrum.creditmanagementservice.core.domains.DebtCase;

import java.util.Set;

public interface ObtainDebtCasesRepository {
    Set<DebtCase> obtainDebtCases(CustomerId customerId);
}
