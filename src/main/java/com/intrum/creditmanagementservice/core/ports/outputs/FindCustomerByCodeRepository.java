package com.intrum.creditmanagementservice.core.ports.outputs;

import com.intrum.creditmanagementservice.core.domains.Code;
import com.intrum.creditmanagementservice.core.domains.Customer;

import java.util.Optional;

public interface FindCustomerByCodeRepository {
    Optional<Customer> findCustomer(Code code);
}
