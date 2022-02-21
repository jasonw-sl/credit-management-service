package com.intrum.creditmanagementservice.core.usecases;

import com.intrum.creditmanagementservice.core.CoreException;
import com.intrum.creditmanagementservice.core.domains.Customer;
import com.intrum.creditmanagementservice.core.domains.DebtCase;
import com.intrum.creditmanagementservice.core.ports.inputs.AddDebtCaseUseCase;
import com.intrum.creditmanagementservice.core.ports.outputs.AddDebtCaseRepository;
import com.intrum.creditmanagementservice.core.ports.outputs.FindCustomerByIdRepository;
import com.intrum.creditmanagementservice.core.ports.outputs.FindDebtCaseByNumberRepository;

import java.util.Objects;
import java.util.Optional;

import static com.intrum.creditmanagementservice.core.domains.enums.ErrorMessage.CUSTOMER_NOT_FOUND;
import static com.intrum.creditmanagementservice.core.domains.enums.ErrorMessage.DEBT_CASE_EXIST;
import static com.intrum.creditmanagementservice.core.domains.enums.Status.CONFLICT;
import static com.intrum.creditmanagementservice.core.domains.enums.Status.NO_DATA;

public class AddDebtCaseUseCaseImpl implements AddDebtCaseUseCase {
    private final AddDebtCaseRepository addDebtCaseRepository;
    private final FindCustomerByIdRepository findCustomerByIdRepository;
    private final FindDebtCaseByNumberRepository findDebtCaseByNumberRepository;

    public AddDebtCaseUseCaseImpl(AddDebtCaseRepository addDebtCaseRepository,
                                  FindCustomerByIdRepository findCustomerByIdRepository,
                                  FindDebtCaseByNumberRepository findDebtCaseByNumberRepository) {
        this.addDebtCaseRepository = Objects.requireNonNull(addDebtCaseRepository);
        this.findCustomerByIdRepository = Objects.requireNonNull(findCustomerByIdRepository);
        this.findDebtCaseByNumberRepository = Objects.requireNonNull(findDebtCaseByNumberRepository);
    }

    @Override
    public DebtCase addDebtCase(DebtCase debtCase) {
        Optional<Customer> customerFound = findCustomerByIdRepository.findCustomer(debtCase.getCustomerId());

        if (customerFound.isPresent()) {
            Optional<DebtCase> debtCaseFound = findDebtCaseByNumberRepository.findDebtCase(debtCase.getNumber());
            if (debtCaseFound.isPresent()) {
                throw CoreException.createException(
                        DEBT_CASE_EXIST,
                        String.format("In DB there is already a debt case with case number [%s]!", debtCase.getNumber().getValue()),
                        CONFLICT);
            }
            return this.addDebtCaseRepository.addDebtCase(debtCase);
        }
        throw CoreException.createException(
                CUSTOMER_NOT_FOUND,
                String.format("There is no customer with id [%s] in DB to whom assign debt case!", debtCase.getCustomerId().getValue()),
                NO_DATA);
    }
}
