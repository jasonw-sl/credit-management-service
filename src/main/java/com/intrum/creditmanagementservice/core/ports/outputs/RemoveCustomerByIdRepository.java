package com.intrum.creditmanagementservice.core.ports.outputs;

import com.intrum.creditmanagementservice.core.domains.CustomerId;

public interface RemoveCustomerByIdRepository {
    int removeCustomer(CustomerId customerId);
}
