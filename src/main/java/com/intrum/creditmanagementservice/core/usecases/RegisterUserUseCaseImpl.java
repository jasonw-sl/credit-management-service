package com.intrum.creditmanagementservice.core.usecases;

import com.intrum.creditmanagementservice.core.CoreException;
import com.intrum.creditmanagementservice.core.domains.Customer;
import com.intrum.creditmanagementservice.core.domains.User;
import com.intrum.creditmanagementservice.core.ports.inputs.RegisterUserUseCase;
import com.intrum.creditmanagementservice.core.ports.outputs.FindCustomerByIdRepository;
import com.intrum.creditmanagementservice.core.ports.outputs.FindUserByUsernameRepository;
import com.intrum.creditmanagementservice.core.ports.outputs.RegisterUserRepository;

import java.util.Objects;
import java.util.Optional;

import static com.intrum.creditmanagementservice.core.domains.enums.ErrorMessage.CUSTOMER_NOT_FOUND;
import static com.intrum.creditmanagementservice.core.domains.enums.ErrorMessage.USER_EXIST;
import static com.intrum.creditmanagementservice.core.domains.enums.Status.CONFLICT;
import static com.intrum.creditmanagementservice.core.domains.enums.Status.NO_DATA;

public class RegisterUserUseCaseImpl implements RegisterUserUseCase {
    private final RegisterUserRepository registerUserRepository;
    private final FindCustomerByIdRepository findCustomerByIdRepository;
    private final FindUserByUsernameRepository findUserByUsernameRepository;

    public RegisterUserUseCaseImpl(RegisterUserRepository registerUserRepository,
                                   FindCustomerByIdRepository findCustomerByIdRepository,
                                   FindUserByUsernameRepository findUserByUsernameRepository) {
        this.registerUserRepository = Objects.requireNonNull(registerUserRepository);
        this.findCustomerByIdRepository = Objects.requireNonNull(findCustomerByIdRepository);
        this.findUserByUsernameRepository = Objects.requireNonNull(findUserByUsernameRepository);
    }

    @Override
    public void registerUser(User user) {
        Optional<Customer> customerFoundById = findCustomerByIdRepository.findCustomer(user.getCustomerId());
        if (!customerFoundById.isPresent()) {
            throw CoreException.createException(
                    CUSTOMER_NOT_FOUND,
                    String.format("Could not found customer by customer id [%s] in DB!", user.getCustomerId().getValue()),
                    NO_DATA);
        }

        Optional<User> userFoundByUsername = findUserByUsernameRepository.findUser(user.getUsername());
        if (userFoundByUsername.isPresent()) {
            throw CoreException.createException(
                    USER_EXIST,
                    String.format("Could not register a new user, username [%s] is already taken!", user.getUsername().getValue()),
                    CONFLICT);
        }

        user.encryptPassword();
        this.registerUserRepository.registerUser(user);
    }
}
