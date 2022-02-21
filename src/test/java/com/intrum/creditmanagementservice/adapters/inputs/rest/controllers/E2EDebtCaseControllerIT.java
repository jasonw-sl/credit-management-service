package com.intrum.creditmanagementservice.adapters.inputs.rest.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.intrum.creditmanagementservice.adapters.inputs.rest.modules.AddDebtCaseRequest;
import com.intrum.creditmanagementservice.adapters.inputs.rest.modules.DebtCaseDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.hamcrest.core.Is.is;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@DisplayName("E2E debt case controller")
class E2EDebtCaseControllerIT {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("Add new debt case")
    void addDebtCase() throws Exception {
        mockMvc.perform(post("/customers/{customerId}/debt-cases", 1L)
                        .with(httpBasic("client", "password"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(
                                new AddDebtCaseRequest()
                                        .setDebtCase(
                                                new DebtCaseDto()
                                                        .setNumber("PR-2394")
                                                        .setAmount(BigDecimal.valueOf(17.06))
                                                        .setCurrency("NOK")
                                                        .setCustomerId(1L)
                                                        .setDueDate(LocalDate.now().plusDays(7))))))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.debtCase.number", is("PR-2394")));
    }

    @Test
    @DisplayName("When debt case is found by id 31")
    void findDebtCase() throws Exception {
        mockMvc.perform(get("/debt-cases/{debtCaseId}", 31L)
                        .with(httpBasic("client", "password"))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.debtCase.number", is("DCN-LV-1234")));
    }

    @Test
    @DisplayName("When debt case by id 34 is removed")
    void removeDebtCase() throws Exception {
        mockMvc.perform(delete("/debt-cases/{debtCaseId}", 34L)
                        .with(httpBasic("client", "password"))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message", is("Debt case has been removed successfully!")));
    }
}