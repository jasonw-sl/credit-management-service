package com.intrum.creditmanagementservice.core.ports.outputs;

import com.intrum.creditmanagementservice.core.domains.Customer;
import com.intrum.creditmanagementservice.core.domains.CustomerId;

import java.util.Optional;

public interface FindCustomerByIdRepository {
    Optional<Customer> findCustomer(CustomerId customerId);
}
