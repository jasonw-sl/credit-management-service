package com.intrum.creditmanagementservice.core.usecases;

import com.intrum.creditmanagementservice.core.CoreException;
import com.intrum.creditmanagementservice.core.domains.DebtCase;
import com.intrum.creditmanagementservice.core.domains.DebtCaseId;
import com.intrum.creditmanagementservice.core.ports.inputs.FindDebtCaseUseCase;
import com.intrum.creditmanagementservice.core.ports.outputs.FindDebtCaseRepository;

import java.util.Objects;
import java.util.Optional;

import static com.intrum.creditmanagementservice.core.domains.enums.ErrorMessage.DEBT_CASE_NOT_FOUND;
import static com.intrum.creditmanagementservice.core.domains.enums.Status.NO_DATA;

public class FindDebtCaseUseCaseImpl implements FindDebtCaseUseCase {
    private final FindDebtCaseRepository findDebtCaseRepository;

    public FindDebtCaseUseCaseImpl(FindDebtCaseRepository findDebtCaseRepository) {
        this.findDebtCaseRepository = Objects.requireNonNull(findDebtCaseRepository);
    }

    @Override
    public DebtCase findDebtCase(DebtCaseId debtCaseId) {
        Optional<DebtCase> debtCase = this.findDebtCaseRepository.findDebtCase(debtCaseId);
        if (debtCase.isPresent()) return debtCase.get();

        throw CoreException.createException(
                DEBT_CASE_NOT_FOUND,
                String.format("Could not found debt case by debt case ID [%s] in DB!", debtCaseId.getValue()),
                NO_DATA);
    }
}
