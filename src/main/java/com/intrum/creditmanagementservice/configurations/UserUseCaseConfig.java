package com.intrum.creditmanagementservice.configurations;

import com.intrum.creditmanagementservice.core.ports.inputs.AuthenticateUserUseCase;
import com.intrum.creditmanagementservice.core.ports.inputs.RegisterUserUseCase;
import com.intrum.creditmanagementservice.core.ports.inputs.RemoveUserUseCase;
import com.intrum.creditmanagementservice.core.ports.outputs.FindCustomerByIdRepository;
import com.intrum.creditmanagementservice.core.ports.outputs.FindUserByUsernameRepository;
import com.intrum.creditmanagementservice.core.ports.outputs.RegisterUserRepository;
import com.intrum.creditmanagementservice.core.ports.outputs.RemoveUserByUsernameRepository;
import com.intrum.creditmanagementservice.core.usecases.AuthenticateUserUseCaseImpl;
import com.intrum.creditmanagementservice.core.usecases.RegisterUserUseCaseImpl;
import com.intrum.creditmanagementservice.core.usecases.RemoveUserUseCaseImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class UserUseCaseConfig {

    @Bean
    AuthenticateUserUseCase initAuthenticateUserUseCase(final FindUserByUsernameRepository findUserByUsernameRepository) {
        return new AuthenticateUserUseCaseImpl(findUserByUsernameRepository);
    }

    @Bean
    RegisterUserUseCase initRegisterUserUseCase(final RegisterUserRepository registerUserRepository,
                                                final FindCustomerByIdRepository findCustomerByIdRepository,
                                                final FindUserByUsernameRepository findUserByUsernameRepository) {
        return new RegisterUserUseCaseImpl(registerUserRepository, findCustomerByIdRepository, findUserByUsernameRepository);
    }

    @Bean
    RemoveUserUseCase initRemoveUserUseCase(final RemoveUserByUsernameRepository removeUserByUsernameRepository) {
        return new RemoveUserUseCaseImpl(removeUserByUsernameRepository);
    }
}
