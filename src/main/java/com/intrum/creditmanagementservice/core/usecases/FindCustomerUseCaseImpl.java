package com.intrum.creditmanagementservice.core.usecases;

import com.intrum.creditmanagementservice.core.CoreException;
import com.intrum.creditmanagementservice.core.domains.Customer;
import com.intrum.creditmanagementservice.core.domains.CustomerId;
import com.intrum.creditmanagementservice.core.ports.inputs.FindCustomerUseCase;
import com.intrum.creditmanagementservice.core.ports.outputs.FindCustomerByIdRepository;

import java.util.Objects;
import java.util.Optional;

import static com.intrum.creditmanagementservice.core.domains.enums.ErrorMessage.CUSTOMER_NOT_FOUND;
import static com.intrum.creditmanagementservice.core.domains.enums.Status.NO_DATA;

public class FindCustomerUseCaseImpl implements FindCustomerUseCase {
    private final FindCustomerByIdRepository findCustomerByIdRepository;

    public FindCustomerUseCaseImpl(FindCustomerByIdRepository findCustomerByIdRepository) {
        this.findCustomerByIdRepository = Objects.requireNonNull(findCustomerByIdRepository);
    }

    @Override
    public Customer findCustomer(CustomerId customerId) {
        Optional<Customer> customerFound = this.findCustomerByIdRepository.findCustomer(customerId);
        if (customerFound.isPresent()) return customerFound.get();

        throw CoreException.createException(
                CUSTOMER_NOT_FOUND,
                String.format("Could not found customer by customer id [%s] in DB!", customerId.getValue()),
                NO_DATA);
    }
}
