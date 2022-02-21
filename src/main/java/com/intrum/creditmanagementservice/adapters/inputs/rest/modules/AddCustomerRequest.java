package com.intrum.creditmanagementservice.adapters.inputs.rest.modules;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class AddCustomerRequest {

    @Schema(required = true)
    private CustomerDto customer;
}
