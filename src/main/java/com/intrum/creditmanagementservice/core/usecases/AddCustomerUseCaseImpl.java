package com.intrum.creditmanagementservice.core.usecases;

import com.intrum.creditmanagementservice.core.CoreException;
import com.intrum.creditmanagementservice.core.domains.enums.Status;
import com.intrum.creditmanagementservice.core.domains.Customer;
import com.intrum.creditmanagementservice.core.ports.inputs.AddCustomerUseCase;
import com.intrum.creditmanagementservice.core.ports.outputs.AddCustomerRepository;
import com.intrum.creditmanagementservice.core.ports.outputs.FindCustomerByCodeRepository;

import java.util.Objects;
import java.util.Optional;

import static com.intrum.creditmanagementservice.core.domains.enums.ErrorMessage.CUSTOMER_EXIST;

public class AddCustomerUseCaseImpl implements AddCustomerUseCase {
    private final AddCustomerRepository addCustomerRepository;
    private final FindCustomerByCodeRepository findCustomerByCodeRepository;

    public AddCustomerUseCaseImpl(AddCustomerRepository addCustomerRepository, FindCustomerByCodeRepository findCustomerByCodeRepository) {
        this.addCustomerRepository = Objects.requireNonNull(addCustomerRepository);
        this.findCustomerByCodeRepository = Objects.requireNonNull(findCustomerByCodeRepository);
    }

    @Override
    public Customer addCustomer(Customer customer) {
        Optional<Customer> customerFound = this.findCustomerByCodeRepository.findCustomer(customer.getCode());
        if (customerFound.isPresent())
            throw CoreException.createException(
                    CUSTOMER_EXIST,
                    String.format("Customer with code [%s] already exist DB with customer id [%s]!",
                            customer.getCode().getValue(), customerFound.get().getId().getValue()),
                    Status.CONFLICT);

        return this.addCustomerRepository.addCustomer(customer);
    }
}
