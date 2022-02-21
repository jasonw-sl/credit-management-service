package com.intrum.creditmanagementservice.core.usecases;

import com.intrum.creditmanagementservice.core.CoreException;
import com.intrum.creditmanagementservice.core.domains.Customer;
import com.intrum.creditmanagementservice.core.ports.inputs.EditCustomerUseCase;
import com.intrum.creditmanagementservice.core.ports.outputs.EditCustomerRepository;
import com.intrum.creditmanagementservice.core.ports.outputs.FindCustomerByCodeRepository;
import com.intrum.creditmanagementservice.core.ports.outputs.FindCustomerByIdRepository;

import java.util.Objects;
import java.util.Optional;

import static com.intrum.creditmanagementservice.core.domains.enums.ErrorMessage.CUSTOMER_EXIST;
import static com.intrum.creditmanagementservice.core.domains.enums.ErrorMessage.CUSTOMER_NOT_FOUND;
import static com.intrum.creditmanagementservice.core.domains.enums.Status.CONFLICT;
import static com.intrum.creditmanagementservice.core.domains.enums.Status.NO_DATA;

public class EditCustomerUseCaseImpl implements EditCustomerUseCase {
    private final EditCustomerRepository editCustomerRepository;
    private final FindCustomerByIdRepository findCustomerByIdRepository;
    private final FindCustomerByCodeRepository findCustomerByCodeRepository;

    public EditCustomerUseCaseImpl(EditCustomerRepository editCustomerRepository,
                                   FindCustomerByIdRepository findCustomerByIdRepository,
                                   FindCustomerByCodeRepository findCustomerByCodeRepository) {
        this.editCustomerRepository = Objects.requireNonNull(editCustomerRepository);
        this.findCustomerByIdRepository = Objects.requireNonNull(findCustomerByIdRepository);
        this.findCustomerByCodeRepository = Objects.requireNonNull(findCustomerByCodeRepository);
    }

    @Override
    public Customer editCustomer(Customer customer) {
        Optional<Customer> customerFoundById = findCustomerByIdRepository.findCustomer(customer.getId());
        if (!customerFoundById.isPresent()) {
            throw CoreException.createException(
                    CUSTOMER_NOT_FOUND,
                    String.format("Could not found customer by customer id [%s] in DB!", customer.getId().getValue()),
                    NO_DATA);
        }

        Optional<Customer> customerFoundByCode = findCustomerByCodeRepository.findCustomer(customer.getCode());
        if (customerFoundByCode.isPresent() && !customerFoundByCode.get().getId().equals(customer.getId())) {
            throw CoreException.createException(
                    CUSTOMER_EXIST,
                    String.format("Customer by code [%s] already exist in DB and belong to customer with id [%s]!",
                            customer.getCode().getValue(), customerFoundByCode.get().getId().getValue()),
                    CONFLICT);
        }

        return this.editCustomerRepository.editCustomer(customer);
    }
}
