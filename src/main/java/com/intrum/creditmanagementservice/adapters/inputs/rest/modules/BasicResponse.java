package com.intrum.creditmanagementservice.adapters.inputs.rest.modules;

import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

@Data
@Accessors(chain = true)
public class BasicResponse {
    private Long code;
    private String message;
    private String object;
    private LocalDateTime timestamp;
    private String uuid;
}
