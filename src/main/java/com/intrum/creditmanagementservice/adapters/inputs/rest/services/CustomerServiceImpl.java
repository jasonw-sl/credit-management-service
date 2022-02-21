package com.intrum.creditmanagementservice.adapters.inputs.rest.services;

import com.intrum.creditmanagementservice.adapters.inputs.rest.modules.*;
import com.intrum.creditmanagementservice.core.CoreException;
import com.intrum.creditmanagementservice.core.domains.*;
import com.intrum.creditmanagementservice.core.ports.inputs.AddCustomerUseCase;
import com.intrum.creditmanagementservice.core.ports.inputs.EditCustomerUseCase;
import com.intrum.creditmanagementservice.core.ports.inputs.FindCustomerUseCase;
import com.intrum.creditmanagementservice.core.ports.inputs.RemoveCustomerUseCase;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Objects;

import static com.intrum.creditmanagementservice.core.domains.enums.ErrorMessage.NO_CUSTOMER_DATA;
import static com.intrum.creditmanagementservice.core.domains.enums.ErrorMessage.NO_REQUEST_DATA;
import static com.intrum.creditmanagementservice.core.domains.enums.SuccessMessage.CUSTOMER_REMOVED;

@Service
@AllArgsConstructor
public class CustomerServiceImpl implements CustomerService {
    @NonNull private final AddCustomerUseCase addCustomerUseCase;
    @NonNull private final FindCustomerUseCase findCustomerUseCase;
    @NonNull private final RemoveCustomerUseCase removeCustomerUseCase;
    @NonNull private final EditCustomerUseCase editCustomerUseCase;

    @Override
    @Transactional
    public AddCustomerResponse addCustomer(AddCustomerRequest request) {
        if (Objects.isNull(request))
            throw CoreException.createException(NO_REQUEST_DATA);
        if (Objects.isNull(request.getCustomer()))
            throw CoreException.createException(NO_CUSTOMER_DATA, Customer.class);

        CustomerDto customerDto = request.getCustomer();

        Customer customer = this.addCustomerUseCase.addCustomer(
                new Customer(
                        new Name(customerDto.getName()),
                        new Surname(customerDto.getSurname()),
                        new Code(customerDto.getCode()),
                        new Country(customerDto.getCountry()),
                        new Email(customerDto.getEmail())
                ));
        return new AddCustomerResponse().setCustomer(this.toDto(customer));
    }

    @Override
    public FindCustomerResponse findCustomer(Long customerId) {
        Customer customer = this.findCustomerUseCase.findCustomer(new CustomerId(customerId));
        return new FindCustomerResponse().setCustomer(this.toDto(customer));
    }

    @Override
    @Transactional
    public BasicResponse removeCustomer(Long customerId) {
        this.removeCustomerUseCase.removeCustomer(new CustomerId(customerId));
        return new BasicResponse()
                .setMessage(CUSTOMER_REMOVED.getText())
                .setCode(CUSTOMER_REMOVED.getCode());
    }

    @Override
    @Transactional
    public EditCustomerResponse editCustomer(Long customerId, EditCustomerRequest request) {
        if (Objects.isNull(request))
            throw CoreException.createException(NO_REQUEST_DATA);
        if (Objects.isNull(request.getCustomer()))
            throw CoreException.createException(NO_CUSTOMER_DATA, Customer.class);

        CustomerDto customerDto = request.getCustomer();

        Customer customer = this.editCustomerUseCase.editCustomer(
                new Customer(
                        new CustomerId(customerId),
                        new Name(customerDto.getName()),
                        new Surname(customerDto.getSurname()),
                        new Code(customerDto.getCode()),
                        new Country(customerDto.getCountry()),
                        new Email(customerDto.getEmail())
                )
        );
        return new EditCustomerResponse().setCustomer(this.toDto(customer));
    }

    private CustomerDto toDto(Customer customer) {
        if (Objects.isNull(customer)) return null;
        return new CustomerDto()
                .setId(customer.getId().getValue())
                .setName(customer.getName().getValue())
                .setSurname(customer.getSurname().getValue())
                .setCode(customer.getCode().getValue())
                .setCountry(customer.getCountry().getValue())
                .setEmail(customer.getEmail().getValue());
    }
}
