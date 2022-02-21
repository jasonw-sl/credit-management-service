package com.intrum.creditmanagementservice.adapters.inputs.rest.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.intrum.creditmanagementservice.adapters.inputs.rest.modules.AddCustomerRequest;
import com.intrum.creditmanagementservice.adapters.inputs.rest.modules.CustomerDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.core.Is.is;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@DisplayName("E2E Customer controller test")
class E2ECustomerControllerIT {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("When customer by id 1 is found")
    void findCustomer() throws Exception {
        mockMvc.perform(get("/customers/{customerId}", 1L)
                        .with(httpBasic("client", "password"))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.customer.code", is("32965391046")));
    }

    @Test
    @DisplayName("When new customer is added")
    void addCustomer() throws Exception {
        mockMvc.perform(post("/customers")
                        .with(httpBasic("client", "password"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(
                                new AddCustomerRequest()
                                        .setCustomer(
                                                new CustomerDto()
                                                        .setName("Name")
                                                        .setSurname("Surname")
                                                        .setCode("34545645756")
                                                        .setCountry("DE")
                                                        .setEmail("name@surname.de")))))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.customer.code", is("34545645756")));
    }

    @Test
    @DisplayName("When customer by id 3 is removed")
    void removeCustomer() throws Exception {
        mockMvc.perform(delete("/customers/{customerId}", 3L)
                        .with(httpBasic("client", "password"))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message", is("Customer has been removed successfully!")));
    }

}