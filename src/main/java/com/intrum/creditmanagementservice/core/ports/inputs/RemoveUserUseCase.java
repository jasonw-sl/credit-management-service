package com.intrum.creditmanagementservice.core.ports.inputs;

import com.intrum.creditmanagementservice.core.domains.Username;

public interface RemoveUserUseCase {
    void removeUser(Username username);
}
