package com.intrum.creditmanagementservice.adapters.inputs.rest.controllers;

import com.intrum.creditmanagementservice.adapters.inputs.rest.modules.*;
import com.intrum.creditmanagementservice.adapters.inputs.rest.services.CustomerService;
import com.intrum.creditmanagementservice.adapters.inputs.rest.utils.ResponseUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/customers")
@Tag(name = "Customer service")
public class CustomerController {
    @NonNull private final CustomerService customerService;

    @GetMapping("/{customerId}")
    @Operation(summary = "Find customer")
    public ResponseEntity<FindCustomerResponse> findCustomer(@PathVariable Long customerId) {
        return ResponseUtils.handle(customerId, this.customerService::findCustomer, HttpStatus.OK);
    }

    @PostMapping
    @Operation(summary = "Add new customer")
    public ResponseEntity<AddCustomerResponse> addCustomer(@RequestBody AddCustomerRequest request) {
        return ResponseUtils.handle(request, this.customerService::addCustomer, HttpStatus.CREATED);
    }

    @DeleteMapping("/{customerId}")
    @Operation(summary = "Remove customer")
    public ResponseEntity<BasicResponse> removeCustomer(@PathVariable Long customerId) {
        return ResponseUtils.handle(customerId, this.customerService::removeCustomer, HttpStatus.OK);
    }

    @PutMapping("/{customerId}")
    @Operation(summary = "Edit customer")
    public ResponseEntity<EditCustomerResponse> editCustomer(@PathVariable Long customerId, @RequestBody EditCustomerRequest request) {
        return ResponseUtils.handle(request, r -> this.customerService.editCustomer(customerId, request), HttpStatus.OK);
    }
}
