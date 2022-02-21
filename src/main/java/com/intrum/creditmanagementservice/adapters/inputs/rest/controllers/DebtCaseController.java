package com.intrum.creditmanagementservice.adapters.inputs.rest.controllers;

import com.intrum.creditmanagementservice.adapters.inputs.rest.modules.*;
import com.intrum.creditmanagementservice.adapters.inputs.rest.services.DebtCaseService;
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
@Tag(name = "Debt case service")
public class DebtCaseController {
    @NonNull private final DebtCaseService debtCaseService;

    @GetMapping("/customers/{customerId}/debt-cases")
    @Operation(summary = "Obtain customer debt cases")
    public ResponseEntity<ObtainDebCasesResponse> obtainDebtCases(@PathVariable Long customerId) {
        return ResponseUtils.handle(customerId, this.debtCaseService::obtainDebtCases, HttpStatus.OK);
    }

    @PostMapping("/customers/{customerId}/debt-cases")
    @Operation(summary = "Add customer debt case")
    public ResponseEntity<AddDebtCaseResponse> addDebtCase(@PathVariable Long customerId,
                                                           @RequestBody AddDebtCaseRequest request) {
        return ResponseUtils.handle(request, r -> this.debtCaseService.addDebtCase(customerId, request), HttpStatus.CREATED);
    }

    @PutMapping("/customers/{customerId}/debt-cases/{debtCaseId}")
    @Operation(summary = "Edit customer debt case")
    public ResponseEntity<EditDebtCaseResponse> editDebtCase(@PathVariable Long customerId,
                                                             @PathVariable Long debtCaseId,
                                                             @RequestBody EditDebtCaseRequest request) {
        return ResponseUtils.handle(request, r -> this.debtCaseService.editDebtCase(customerId, debtCaseId, request), HttpStatus.OK);
    }

    @GetMapping("/debt-cases/{debtCaseId}")
    @Operation(summary = "Find debt case")
    public ResponseEntity<FindDebCaseResponse> findDebtCase(@PathVariable Long debtCaseId) {
        return ResponseUtils.handle(debtCaseId, this.debtCaseService::findDebtCase, HttpStatus.OK);
    }

    @DeleteMapping("/debt-cases/{debtCaseId}")
    @Operation(summary = "Remove customer debt case")
    public ResponseEntity<BasicResponse> removeDebtCase(@PathVariable Long debtCaseId) {
        return ResponseUtils.handle(debtCaseId, this.debtCaseService::removeDebtCase, HttpStatus.OK);
    }
}
