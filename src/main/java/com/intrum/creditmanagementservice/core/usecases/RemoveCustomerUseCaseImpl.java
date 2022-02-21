package com.intrum.creditmanagementservice.core.usecases;

import com.intrum.creditmanagementservice.core.CoreException;
import com.intrum.creditmanagementservice.core.domains.CustomerId;
import com.intrum.creditmanagementservice.core.domains.DebtCase;
import com.intrum.creditmanagementservice.core.domains.User;
import com.intrum.creditmanagementservice.core.ports.inputs.RemoveCustomerUseCase;
import com.intrum.creditmanagementservice.core.ports.outputs.FindUserByCustomerIdRepository;
import com.intrum.creditmanagementservice.core.ports.outputs.ObtainDebtCasesRepository;
import com.intrum.creditmanagementservice.core.ports.outputs.RemoveCustomerByIdRepository;

import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import static com.intrum.creditmanagementservice.core.domains.enums.ErrorMessage.*;
import static com.intrum.creditmanagementservice.core.domains.enums.Status.NO_DATA;

public class RemoveCustomerUseCaseImpl implements RemoveCustomerUseCase {
    private final RemoveCustomerByIdRepository removeCustomerByIdRepository;
    private final FindUserByCustomerIdRepository findUserByCustomerIdRepository;
    private final ObtainDebtCasesRepository obtainDebtCasesRepository;

    public RemoveCustomerUseCaseImpl(RemoveCustomerByIdRepository removeCustomerByIdRepository,
                                     FindUserByCustomerIdRepository findUserByCustomerIdRepository,
                                     ObtainDebtCasesRepository obtainDebtCasesRepository) {
        this.removeCustomerByIdRepository = Objects.requireNonNull(removeCustomerByIdRepository);
        this.findUserByCustomerIdRepository = Objects.requireNonNull(findUserByCustomerIdRepository);
        this.obtainDebtCasesRepository = Objects.requireNonNull(obtainDebtCasesRepository);
    }

    @Override
    public void removeCustomer(CustomerId customerId) {
        Optional<User> userFound = findUserByCustomerIdRepository.findUser(customerId);
        if (userFound.isPresent()) {
            throw CoreException.createException(
                    USER_EXIST,
                    String.format("Can not remove customer, there is user [%s] connected to customer!", userFound.get().getUsername().getValue()));
        }

        Set<DebtCase> debtCasesFound = obtainDebtCasesRepository.obtainDebtCases(customerId);
        if (debtCasesFound.isEmpty()) {
            int removedRecords = this.removeCustomerByIdRepository.removeCustomer(customerId);
            if (removedRecords == 1) return;

            throw CoreException.createException(
                    CUSTOMER_NOT_FOUND,
                    String.format("There is no a customer by customer id [%s] which could be removed from DB!", customerId.getValue()),
                    NO_DATA);
        }

        throw CoreException.createException(
                DEBT_CASE_EXIST,
                String.format("Can not remove customer, there are [%s]x debt cases connected to customer!", debtCasesFound.size()));
    }
}
