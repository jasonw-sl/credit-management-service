package com.intrum.creditmanagementservice.core.ports.outputs;

import com.intrum.creditmanagementservice.core.domains.CustomerId;
import com.intrum.creditmanagementservice.core.domains.User;

import java.util.Optional;

public interface FindUserByCustomerIdRepository {
    Optional<User> findUser(CustomerId customerId);
}
