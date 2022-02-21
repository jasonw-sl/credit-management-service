package com.intrum.creditmanagementservice.adapters.inputs.rest.modules;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.Set;

@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
public class ObtainDebCasesResponse extends BasicResponse {
    private Set<DebtCaseDto> debtCases;
}
