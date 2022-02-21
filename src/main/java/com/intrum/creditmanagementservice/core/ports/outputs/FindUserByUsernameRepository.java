package com.intrum.creditmanagementservice.core.ports.outputs;

import com.intrum.creditmanagementservice.core.domains.User;
import com.intrum.creditmanagementservice.core.domains.Username;

import java.util.Optional;

public interface FindUserByUsernameRepository {
    Optional<User> findUser(Username username);
}
