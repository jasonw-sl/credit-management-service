package com.intrum.creditmanagementservice.core.usecases;

import com.intrum.creditmanagementservice.core.domains.User;
import com.intrum.creditmanagementservice.core.ports.inputs.AuthenticateUserUseCase;
import com.intrum.creditmanagementservice.core.ports.outputs.FindUserByUsernameRepository;

import java.util.Objects;
import java.util.Optional;

public class AuthenticateUserUseCaseImpl implements AuthenticateUserUseCase {
    private final FindUserByUsernameRepository findUserByUsernameRepository;

    public AuthenticateUserUseCaseImpl(FindUserByUsernameRepository findUserByUsernameRepository) {
        this.findUserByUsernameRepository = Objects.requireNonNull(findUserByUsernameRepository);
    }

    @Override
    public boolean authenticateUser(User user) {
        Optional<User> userFound = this.findUserByUsernameRepository.findUser(user.getUsername());
        if (userFound.isPresent()) {
            user.encryptPassword();
            return user.getPassword().hashMatch(userFound.get().getPassword());
        }

        return false;
    }
}
