package com.intrum.creditmanagementservice.core.usecases;

import com.intrum.creditmanagementservice.core.CoreException;
import com.intrum.creditmanagementservice.core.domains.CustomerId;
import com.intrum.creditmanagementservice.core.domains.DebtCase;
import com.intrum.creditmanagementservice.core.ports.inputs.ObtainDebtCasesUseCase;
import com.intrum.creditmanagementservice.core.ports.outputs.ObtainDebtCasesRepository;

import java.util.Objects;
import java.util.Set;

import static com.intrum.creditmanagementservice.core.domains.enums.ErrorMessage.DEBT_CASES_NOT_FOUND;
import static com.intrum.creditmanagementservice.core.domains.enums.Status.NO_DATA;

public class ObtainDebtCasesUseCaseImpl implements ObtainDebtCasesUseCase {
    private final ObtainDebtCasesRepository obtainDebtCasesRepository;

    public ObtainDebtCasesUseCaseImpl(ObtainDebtCasesRepository obtainDebtCasesRepository) {
        this.obtainDebtCasesRepository = Objects.requireNonNull(obtainDebtCasesRepository);
    }

    @Override
    public Set<DebtCase> obtainDebtCases(CustomerId customerId) {
        Set<DebtCase> debtCases = this.obtainDebtCasesRepository.obtainDebtCases(customerId);
        if (debtCases.isEmpty())
            throw CoreException.createException(
                    DEBT_CASES_NOT_FOUND,
                    String.format("Could not found debt cases by customer ID [%s] in DB!", customerId.getValue()),
                    NO_DATA);

        return debtCases;
    }
}
