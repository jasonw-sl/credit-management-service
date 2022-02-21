package com.intrum.creditmanagementservice.adapters.inputs.rest.services;

import com.intrum.creditmanagementservice.adapters.inputs.rest.modules.*;
import com.intrum.creditmanagementservice.core.CoreException;
import com.intrum.creditmanagementservice.core.domains.*;
import com.intrum.creditmanagementservice.core.ports.inputs.*;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import static com.intrum.creditmanagementservice.core.domains.enums.ErrorMessage.NO_DEBT_CASE_DATA;
import static com.intrum.creditmanagementservice.core.domains.enums.ErrorMessage.NO_REQUEST_DATA;
import static com.intrum.creditmanagementservice.core.domains.enums.SuccessMessage.DEBT_CASE_REMOVED;

@Service
@AllArgsConstructor
public class DebtCaseServiceImpl implements DebtCaseService {
    @NonNull private final ObtainDebtCasesUseCase obtainDebtCasesUseCase;
    @NonNull private final FindDebtCaseUseCase findDebtCaseUseCase;
    @NonNull private final AddDebtCaseUseCase addDebtCaseUseCase;
    @NonNull private final RemoveDebtCaseUseCase removeDebtCaseUseCase;
    @NonNull private final EditDebtCaseUseCase editDebtCaseUseCase;

    @Override
    public ObtainDebCasesResponse obtainDebtCases(Long customerId) {
        Set<DebtCase> debtCases = this.obtainDebtCasesUseCase.obtainDebtCases(new CustomerId(customerId));
        return new ObtainDebCasesResponse().setDebtCases(debtCases.stream().map(this::toDto).collect(Collectors.toSet()));
    }

    @Override
    public FindDebCaseResponse findDebtCase(Long debtCaseId) {
        DebtCase debtCase = this.findDebtCaseUseCase.findDebtCase(new DebtCaseId(debtCaseId));
        return new FindDebCaseResponse().setDebtCase(this.toDto(debtCase));
    }

    @Override
    @Transactional
    public AddDebtCaseResponse addDebtCase(Long customerId, AddDebtCaseRequest request) {
        if (Objects.isNull(request))
            throw CoreException.createException(NO_REQUEST_DATA);
        if (Objects.isNull(request.getDebtCase()))
            throw CoreException.createException(NO_DEBT_CASE_DATA, DebtCase.class);

        DebtCaseDto debtCaseDto = request.getDebtCase();

        DebtCase debtCase = this.addDebtCaseUseCase.addDebtCase(
                new DebtCase(
                        new CustomerId(customerId),
                        new CaseNumber(debtCaseDto.getNumber()),
                        new Money(debtCaseDto.getAmount(), debtCaseDto.getCurrency()),
                        new DueDate(debtCaseDto.getDueDate())));
        return new AddDebtCaseResponse().setDebtCase(this.toDto(debtCase));
    }

    @Override
    @Transactional
    public BasicResponse removeDebtCase(Long debtCaseId) {
        this.removeDebtCaseUseCase.removeDebtCase(new DebtCaseId(debtCaseId));
        return new BasicResponse()
                .setMessage(DEBT_CASE_REMOVED.getText())
                .setCode(DEBT_CASE_REMOVED.getCode());
    }

    @Override
    @Transactional
    public EditDebtCaseResponse editDebtCase(Long customerId, Long debtCaseId, EditDebtCaseRequest request) {
        if (Objects.isNull(request))
            throw CoreException.createException(NO_REQUEST_DATA);
        if (Objects.isNull(request.getDebtCase()))
            throw CoreException.createException(NO_DEBT_CASE_DATA, DebtCase.class);

        DebtCaseDto debtCaseDto = request.getDebtCase();

        DebtCase debtCase = this.editDebtCaseUseCase.editDebtCase(
                new DebtCase(
                        new DebtCaseId(debtCaseId),
                        new CustomerId(customerId),
                        new CaseNumber(debtCaseDto.getNumber()),
                        new Money(debtCaseDto.getAmount(), debtCaseDto.getCurrency()),
                        new DueDate(debtCaseDto.getDueDate())));
        return new EditDebtCaseResponse().setDebtCase(this.toDto(debtCase));
    }

    private DebtCaseDto toDto(DebtCase debtCase) {
        if (Objects.isNull(debtCase)) return null;
        return new DebtCaseDto()
                .setId(debtCase.getId().getValue())
                .setCustomerId(debtCase.getCustomerId().getValue())
                .setNumber(debtCase.getNumber().getValue())
                .setAmount(debtCase.getMoney().getValue())
                .setCurrency(debtCase.getMoney().getCurrencyCode())
                .setDueDate(debtCase.getDueDate().getValue());
    }
}
