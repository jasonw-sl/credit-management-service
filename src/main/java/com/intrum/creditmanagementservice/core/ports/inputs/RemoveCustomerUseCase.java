package com.intrum.creditmanagementservice.core.ports.inputs;

import com.intrum.creditmanagementservice.core.domains.CustomerId;

public interface RemoveCustomerUseCase {
    void removeCustomer(CustomerId customerId);
}
