package com.intrum.creditmanagementservice.adapters.inputs.rest.services;

import com.intrum.creditmanagementservice.adapters.inputs.rest.modules.AuthenticateUserRequest;
import com.intrum.creditmanagementservice.adapters.inputs.rest.modules.AuthenticateUserResponse;
import com.intrum.creditmanagementservice.adapters.inputs.rest.modules.BasicResponse;
import com.intrum.creditmanagementservice.adapters.inputs.rest.modules.RegisterUserRequest;

public interface UserService {
    BasicResponse registerUser(RegisterUserRequest request);

    AuthenticateUserResponse authenticateUser(String username, AuthenticateUserRequest request);

    BasicResponse removeUser(String username);
}
