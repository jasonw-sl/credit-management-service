package com.intrum.creditmanagementservice.adapters.inputs.rest.modules;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class UserDto {

    @Schema(required = true, example = "1")
    private Long customerId;

    @Schema(required = true, maxLength = 50, example = "oskada")
    private String username;

    @Schema(required = true, maxLength = 50, example = "password1234")
    private String password;
}
