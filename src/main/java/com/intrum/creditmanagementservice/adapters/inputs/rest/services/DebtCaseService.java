package com.intrum.creditmanagementservice.adapters.inputs.rest.services;

import com.intrum.creditmanagementservice.adapters.inputs.rest.modules.*;

public interface DebtCaseService {
    ObtainDebCasesResponse obtainDebtCases(Long customerId);

    FindDebCaseResponse findDebtCase(Long debtCaseId);

    AddDebtCaseResponse addDebtCase(Long customerId, AddDebtCaseRequest request);

    BasicResponse removeDebtCase(Long debtCaseId);

    EditDebtCaseResponse editDebtCase(Long customerId, Long debtCaseId, EditDebtCaseRequest request);
}
