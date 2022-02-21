package com.intrum.creditmanagementservice.core.ports.outputs;

import com.intrum.creditmanagementservice.core.domains.Customer;

public interface EditCustomerRepository {
    Customer editCustomer(Customer customer);
}
