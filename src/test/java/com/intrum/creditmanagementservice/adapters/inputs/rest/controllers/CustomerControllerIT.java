package com.intrum.creditmanagementservice.adapters.inputs.rest.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.intrum.creditmanagementservice.adapters.inputs.rest.modules.*;
import com.intrum.creditmanagementservice.adapters.inputs.rest.services.CustomerService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CustomerController.class)
@DisplayName("Customer controller controller test")
class CustomerControllerIT {

    @MockBean
    private CustomerService customerService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("Verifying GET /customers/{customerId} HTTP request matching")
    void findCustomer() throws Exception {
        //given
        given(customerService.findCustomer(2124L))
                .willReturn(new FindCustomerResponse());

        //when
        mockMvc.perform(get("/customers/{customerId}", 2124L)
                        .with(httpBasic("client", "password"))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        //then
        then(customerService).should().findCustomer(2124L);
    }

    @Test
    @DisplayName("Verifying POST /customers HTTP request matching")
    void addCustomer() throws Exception {
        //given
        given(customerService.addCustomer(new AddCustomerRequest()))
                .willReturn(new AddCustomerResponse());

        //when
        mockMvc.perform(post("/customers")
                        .with(httpBasic("client", "password"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new AddCustomerRequest())))
                .andExpect(status().isCreated());

        //then
        then(customerService).should().addCustomer(new AddCustomerRequest());
    }

    @Test
    @DisplayName("Verifying DELETE /customers/{customerId} HTTP request matching")
    void removeCustomer() throws Exception {
        //given
        given(customerService.removeCustomer(2131L))
                .willReturn(new BasicResponse());

        //when
        mockMvc.perform(delete("/customers/{customerId}", 2131L)
                        .with(httpBasic("client", "password"))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        //then
        then(customerService).should().removeCustomer(2131L);
    }

    @Test
    @DisplayName("Verifying PUT /customers/{customerId} HTTP request matching")
    void editCustomer() throws Exception {
        //given
        given(customerService.editCustomer(2136L, new EditCustomerRequest()))
                .willReturn(new EditCustomerResponse());

        //when
        mockMvc.perform(put("/customers/{customerId}", 2136L)
                        .with(httpBasic("client", "password"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new EditCustomerRequest())))
                .andExpect(status().isOk());

        //then
        then(customerService).should().editCustomer(2136L, new EditCustomerRequest());
    }
}