package com.intrum.creditmanagementservice.core.usecases;

import com.intrum.creditmanagementservice.core.CoreException;
import com.intrum.creditmanagementservice.core.domains.Customer;
import com.intrum.creditmanagementservice.core.domains.DebtCase;
import com.intrum.creditmanagementservice.core.ports.inputs.EditDebtCaseUseCase;
import com.intrum.creditmanagementservice.core.ports.outputs.EditDebtCaseRepository;
import com.intrum.creditmanagementservice.core.ports.outputs.FindCustomerByIdRepository;
import com.intrum.creditmanagementservice.core.ports.outputs.FindDebtCaseByNumberRepository;
import com.intrum.creditmanagementservice.core.ports.outputs.FindDebtCaseRepository;

import java.util.Objects;
import java.util.Optional;

import static com.intrum.creditmanagementservice.core.domains.enums.ErrorMessage.*;
import static com.intrum.creditmanagementservice.core.domains.enums.Status.CONFLICT;
import static com.intrum.creditmanagementservice.core.domains.enums.Status.NO_DATA;

public class EditDebtCaseUseCaseImpl implements EditDebtCaseUseCase {
    private final EditDebtCaseRepository editDebtCaseRepository;
    private final FindCustomerByIdRepository findCustomerByIdRepository;
    private final FindDebtCaseRepository findDebtCaseRepository;
    private final FindDebtCaseByNumberRepository findDebtCaseByNumberRepository;

    public EditDebtCaseUseCaseImpl(EditDebtCaseRepository editDebtCaseRepository,
                                   FindCustomerByIdRepository findCustomerByIdRepository,
                                   FindDebtCaseRepository findDebtCaseRepository,
                                   FindDebtCaseByNumberRepository findDebtCaseByNumberRepository) {
        this.editDebtCaseRepository = Objects.requireNonNull(editDebtCaseRepository);
        this.findCustomerByIdRepository = Objects.requireNonNull(findCustomerByIdRepository);
        this.findDebtCaseRepository = Objects.requireNonNull(findDebtCaseRepository);
        this.findDebtCaseByNumberRepository = Objects.requireNonNull(findDebtCaseByNumberRepository);
    }

    @Override
    public DebtCase editDebtCase(DebtCase debtCase) {
        Optional<Customer> customerFound = findCustomerByIdRepository.findCustomer(debtCase.getCustomerId());
        if (!customerFound.isPresent()) {
            throw CoreException.createException(
                    CUSTOMER_NOT_FOUND,
                    String.format("Could not found customer by customer id [%s] in DB!", debtCase.getCustomerId().getValue()),
                    NO_DATA);
        }

        Optional<DebtCase> debtCaseFoundById = findDebtCaseRepository.findDebtCase(debtCase.getId());
        if (!debtCaseFoundById.isPresent()) {
            throw CoreException.createException(
                    DEBT_CASE_NOT_FOUND,
                    String.format("Could not found debt case by debt case id [%s] in DB!", debtCase.getId().getValue()),
                    NO_DATA);
        }

        Optional<DebtCase> debtCaseFoundByNumber = findDebtCaseByNumberRepository.findDebtCase(debtCase.getNumber());
        if (debtCaseFoundByNumber.isPresent() && !debtCaseFoundByNumber.get().getId().equals(debtCase.getId())) {
            throw CoreException.createException(
                    DEBT_CASE_EXIST,
                    String.format("Debt case by case number [%s] already exist in DB and belong to debt case with debt case id [%s]!",
                            debtCase.getNumber().getValue(), debtCaseFoundByNumber.get().getId().getValue()),
                    CONFLICT);
        }

        return this.editDebtCaseRepository.editDebtCase(debtCase);
    }
}
