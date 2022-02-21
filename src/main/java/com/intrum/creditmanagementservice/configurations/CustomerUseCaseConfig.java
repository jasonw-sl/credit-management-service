package com.intrum.creditmanagementservice.configurations;

import com.intrum.creditmanagementservice.core.ports.inputs.AddCustomerUseCase;
import com.intrum.creditmanagementservice.core.ports.inputs.EditCustomerUseCase;
import com.intrum.creditmanagementservice.core.ports.inputs.FindCustomerUseCase;
import com.intrum.creditmanagementservice.core.ports.inputs.RemoveCustomerUseCase;
import com.intrum.creditmanagementservice.core.ports.outputs.*;
import com.intrum.creditmanagementservice.core.usecases.AddCustomerUseCaseImpl;
import com.intrum.creditmanagementservice.core.usecases.EditCustomerUseCaseImpl;
import com.intrum.creditmanagementservice.core.usecases.FindCustomerUseCaseImpl;
import com.intrum.creditmanagementservice.core.usecases.RemoveCustomerUseCaseImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class CustomerUseCaseConfig {

    @Bean
    AddCustomerUseCase initAddCustomerUseCase(final AddCustomerRepository addCustomerRepository,
                                              final FindCustomerByCodeRepository findCustomerByCodeRepository) {
        return new AddCustomerUseCaseImpl(addCustomerRepository, findCustomerByCodeRepository);
    }

    @Bean
    FindCustomerUseCase initFindCustomerUseCase(final FindCustomerByIdRepository findCustomerByIdRepository) {
        return new FindCustomerUseCaseImpl(findCustomerByIdRepository);
    }

    @Bean
    RemoveCustomerUseCase initRemoveCustomerUseCase(final RemoveCustomerByIdRepository removeCustomerByIdRepository,
                                                    final FindUserByCustomerIdRepository findUserByCustomerIdRepository,
                                                    final ObtainDebtCasesRepository obtainDebtCasesRepository) {
        return new RemoveCustomerUseCaseImpl(removeCustomerByIdRepository, findUserByCustomerIdRepository, obtainDebtCasesRepository);
    }

    @Bean
    EditCustomerUseCase initEditCustomerUseCase(final EditCustomerRepository editCustomerRepository,
                                                final FindCustomerByIdRepository findCustomerByIdRepository,
                                                final FindCustomerByCodeRepository findCustomerByCodeRepository) {
        return new EditCustomerUseCaseImpl(editCustomerRepository, findCustomerByIdRepository, findCustomerByCodeRepository);
    }
}
