package com.intrum.creditmanagementservice.core.usecases;

import com.intrum.creditmanagementservice.core.CoreException;
import com.intrum.creditmanagementservice.core.domains.Username;
import com.intrum.creditmanagementservice.core.ports.inputs.RemoveUserUseCase;
import com.intrum.creditmanagementservice.core.ports.outputs.RemoveUserByUsernameRepository;

import java.util.Objects;

import static com.intrum.creditmanagementservice.core.domains.enums.ErrorMessage.USER_NOT_FOUND;
import static com.intrum.creditmanagementservice.core.domains.enums.Status.NO_DATA;

public class RemoveUserUseCaseImpl implements RemoveUserUseCase {
    private final RemoveUserByUsernameRepository removeUserByUsernameRepository;

    public RemoveUserUseCaseImpl(RemoveUserByUsernameRepository removeUserByUsernameRepository) {
        this.removeUserByUsernameRepository = Objects.requireNonNull(removeUserByUsernameRepository);
    }

    @Override
    public void removeUser(Username username) {
        int removedUsers = this.removeUserByUsernameRepository.removeUser(username);
        if (removedUsers != 1) {
            throw CoreException.createException(
                    USER_NOT_FOUND,
                    String.format("Could not found user by username [%s] in DB!", username.getValue()),
                    NO_DATA);
        }
    }
}
