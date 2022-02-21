package com.intrum.creditmanagementservice.configurations;

import com.intrum.creditmanagementservice.core.ports.inputs.*;
import com.intrum.creditmanagementservice.core.ports.outputs.*;
import com.intrum.creditmanagementservice.core.usecases.*;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class DebtCaseUseCaseConfig {

    @Bean
    AddDebtCaseUseCase initAddDebtCaseUseCase(final AddDebtCaseRepository addDebtCaseRepository,
                                              final FindCustomerByIdRepository findCustomerByIdRepository,
                                              final FindDebtCaseByNumberRepository findDebtCaseByNumberRepository) {
        return new AddDebtCaseUseCaseImpl(addDebtCaseRepository, findCustomerByIdRepository, findDebtCaseByNumberRepository);
    }

    @Bean
    EditDebtCaseUseCase initEditDebtCaseUseCase(final EditDebtCaseRepository editDebtCaseRepository,
                                                final FindCustomerByIdRepository findCustomerByIdRepository,
                                                final FindDebtCaseRepository findDebtCaseRepository,
                                                final FindDebtCaseByNumberRepository findDebtCaseByNumberRepository) {
        return new EditDebtCaseUseCaseImpl(editDebtCaseRepository, findCustomerByIdRepository, findDebtCaseRepository, findDebtCaseByNumberRepository);
    }

    @Bean
    FindDebtCaseUseCase initFindDebtCaseUseCase(final FindDebtCaseRepository findDebtCaseRepository) {
        return new FindDebtCaseUseCaseImpl(findDebtCaseRepository);
    }

    @Bean
    ObtainDebtCasesUseCase initObtainDebtCasesUseCase(final ObtainDebtCasesRepository obtainDebtCasesRepository) {
        return new ObtainDebtCasesUseCaseImpl(obtainDebtCasesRepository);
    }

    @Bean
    RemoveDebtCaseUseCase initRemoveDebtCaseUseCase(final RemoveDebtCaseRepository removeDebtCaseRepository) {
        return new RemoveDebtCaseUseCaseImpl(removeDebtCaseRepository);
    }
}
