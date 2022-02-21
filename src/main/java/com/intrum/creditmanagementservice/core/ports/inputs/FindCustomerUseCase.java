package com.intrum.creditmanagementservice.core.ports.inputs;

import com.intrum.creditmanagementservice.core.domains.Customer;
import com.intrum.creditmanagementservice.core.domains.CustomerId;

public interface FindCustomerUseCase {
    Customer findCustomer(CustomerId customerId);
}
