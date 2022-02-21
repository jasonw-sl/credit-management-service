package com.intrum.creditmanagementservice.core.usecases;

import com.intrum.creditmanagementservice.core.CoreException;
import com.intrum.creditmanagementservice.core.domains.DebtCaseId;
import com.intrum.creditmanagementservice.core.ports.inputs.RemoveDebtCaseUseCase;
import com.intrum.creditmanagementservice.core.ports.outputs.RemoveDebtCaseRepository;

import java.util.Objects;

import static com.intrum.creditmanagementservice.core.domains.enums.ErrorMessage.DEBT_CASE_NOT_FOUND;
import static com.intrum.creditmanagementservice.core.domains.enums.Status.NO_DATA;

public class RemoveDebtCaseUseCaseImpl implements RemoveDebtCaseUseCase {
    private final RemoveDebtCaseRepository removeDebtCaseRepository;

    public RemoveDebtCaseUseCaseImpl(RemoveDebtCaseRepository removeDebtCaseRepository) {
        this.removeDebtCaseRepository = Objects.requireNonNull(removeDebtCaseRepository);
    }

    @Override
    public void removeDebtCase(DebtCaseId debtCaseId) {
        int removedDebtCases = this.removeDebtCaseRepository.removeDebtCase(debtCaseId);
        if (removedDebtCases != 1)
            throw CoreException.createException(
                    DEBT_CASE_NOT_FOUND,
                    String.format("Could not found debt case by debt case id [%s] in DB!", debtCaseId.getValue()),
                    NO_DATA);
    }
}
