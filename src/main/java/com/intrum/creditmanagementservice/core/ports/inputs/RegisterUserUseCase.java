package com.intrum.creditmanagementservice.core.ports.inputs;

import com.intrum.creditmanagementservice.core.domains.User;

public interface RegisterUserUseCase {
    void registerUser(User user);
}
