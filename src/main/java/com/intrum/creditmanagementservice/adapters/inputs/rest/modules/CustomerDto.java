package com.intrum.creditmanagementservice.adapters.inputs.rest.modules;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class CustomerDto {

    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private Long id;

    @Schema(required = true, example = "Oskars", maxLength = 50)
    private String name;

    @Schema(required = true, example = "Adamovičs", maxLength = 50)
    private String surname;

    @Schema(required = true, example = "32965391041", maxLength = 20)
    private String code;

    @Schema(description = "Country code in ISO 3166-1 standard", required = true, example = "LV", maxLength = 2)
    private String country;

    @Schema(required = true, example = "adamovics.oskars@inbox.lv", maxLength = 50)
    private String email;

}
