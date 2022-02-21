package com.intrum.creditmanagementservice.core.ports.inputs;

import com.intrum.creditmanagementservice.core.domains.Customer;

public interface AddCustomerUseCase {
    Customer addCustomer(Customer customer);
}
