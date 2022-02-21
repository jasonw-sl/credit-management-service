package com.intrum.creditmanagementservice.adapters.inputs.rest.services;

import com.intrum.creditmanagementservice.adapters.inputs.rest.modules.*;
import com.intrum.creditmanagementservice.core.CoreException;
import com.intrum.creditmanagementservice.core.domains.*;
import com.intrum.creditmanagementservice.core.ports.inputs.AuthenticateUserUseCase;
import com.intrum.creditmanagementservice.core.ports.inputs.RegisterUserUseCase;
import com.intrum.creditmanagementservice.core.ports.inputs.RemoveUserUseCase;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

import java.util.Objects;

import static com.intrum.creditmanagementservice.core.domains.enums.ErrorMessage.*;
import static com.intrum.creditmanagementservice.core.domains.enums.SuccessMessage.USER_REGISTERED;
import static com.intrum.creditmanagementservice.core.domains.enums.SuccessMessage.USER_REMOVED;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {
    @NonNull private final RegisterUserUseCase registerUserUseCase;
    @NonNull private final AuthenticateUserUseCase authenticateUserUseCase;
    @NonNull private final RemoveUserUseCase removeUserUseCase;

    @Override
    @Transactional
    public BasicResponse registerUser(RegisterUserRequest request) {
        if (Objects.isNull(request))
            throw CoreException.createException(NO_REQUEST_DATA);
        if (Objects.isNull(request.getUser()))
            throw CoreException.createException(NO_USER_DATA, User.class);

        UserDto user = request.getUser();

        this.registerUserUseCase.registerUser(
                new User(
                        new CustomerId(user.getCustomerId()),
                        new Username(user.getUsername()),
                        new Password(user.getPassword())));
        return new BasicResponse()
                .setCode(USER_REGISTERED.getCode())
                .setMessage(USER_REGISTERED.getText());
    }

    @Override
    public AuthenticateUserResponse authenticateUser(String username, AuthenticateUserRequest request) {
        boolean isAuthenticated = this.authenticateUserUseCase.authenticateUser(
                new User(
                        new Username(username),
                        new Password(request.getPassword())));
        return new AuthenticateUserResponse().setIsAuthenticated(isAuthenticated);
    }

    @Override
    @Transactional
    public BasicResponse removeUser(String username) {
        this.removeUserUseCase.removeUser(new Username(username));
        return new BasicResponse()
                .setCode(USER_REMOVED.getCode())
                .setMessage(USER_REMOVED.getText());
    }
}
