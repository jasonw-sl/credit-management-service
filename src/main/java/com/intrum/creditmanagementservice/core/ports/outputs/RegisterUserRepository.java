package com.intrum.creditmanagementservice.core.ports.outputs;

import com.intrum.creditmanagementservice.core.domains.User;

public interface RegisterUserRepository {
    void registerUser(User user);
}
