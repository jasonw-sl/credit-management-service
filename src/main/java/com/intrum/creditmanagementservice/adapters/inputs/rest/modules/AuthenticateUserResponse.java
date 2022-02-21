package com.intrum.creditmanagementservice.adapters.inputs.rest.modules;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
public class AuthenticateUserResponse extends BasicResponse {
    private Boolean isAuthenticated;
}
