package com.intrum.creditmanagementservice.adapters.inputs.rest.services;

import com.intrum.creditmanagementservice.adapters.inputs.rest.modules.*;

public interface CustomerService {
    AddCustomerResponse addCustomer(AddCustomerRequest request);

    FindCustomerResponse findCustomer(Long customerId);

    BasicResponse removeCustomer(Long customerId);

    EditCustomerResponse editCustomer(Long customerId, EditCustomerRequest request);
}
