package com.intrum.creditmanagementservice.core.ports.outputs;

import com.intrum.creditmanagementservice.core.domains.Username;

public interface RemoveUserByUsernameRepository {
    int removeUser(Username username);
}
