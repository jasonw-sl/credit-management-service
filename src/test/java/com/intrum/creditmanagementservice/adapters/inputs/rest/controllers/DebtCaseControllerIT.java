package com.intrum.creditmanagementservice.adapters.inputs.rest.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.intrum.creditmanagementservice.adapters.inputs.rest.modules.*;
import com.intrum.creditmanagementservice.adapters.inputs.rest.services.DebtCaseService;
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

@WebMvcTest(DebtCaseController.class)
@DisplayName("Debt case controller test")
class DebtCaseControllerIT {

    @MockBean
    private DebtCaseService debtCaseService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("Verifying GET /customers/{customerId}/debt-cases HTTP request matching")
    void obtainDebtCases() throws Exception {
        //given
        given(debtCaseService.obtainDebtCases(2148L))
                .willReturn(new ObtainDebCasesResponse());

        //when
        mockMvc.perform(get("/customers/{customerId}/debt-cases", 2148L)
                        .with(httpBasic("client", "password"))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        //then
        then(debtCaseService).should().obtainDebtCases(2148L);
    }

    @Test
    @DisplayName("Verifying POST /customers/{customerId}/debt-cases HTTP request matching")
    void addDebtCase() throws Exception {
        //given
        given(debtCaseService.addDebtCase(2150L, new AddDebtCaseRequest()))
                .willReturn(new AddDebtCaseResponse());

        //when
        mockMvc.perform(post("/customers/{customerId}/debt-cases", 2150L)
                        .with(httpBasic("client", "password"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new AddDebtCaseRequest())))
                .andExpect(status().isCreated());

        //then
        then(debtCaseService).should().addDebtCase(2150L, new AddDebtCaseRequest());
    }

    @Test
    @DisplayName("Verifying PUT /customers/{customerId}/debt-cases/{debtCaseId} HTTP request matching")
    void editDebtCase() throws Exception {
        //given
        given(debtCaseService.editDebtCase(2152L, 220L, new EditDebtCaseRequest()))
                .willReturn(new EditDebtCaseResponse());

        //when
        mockMvc.perform(put("/customers/{customerId}/debt-cases/{debtCaseId}", 2152L, 220L)
                        .with(httpBasic("client", "password"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new EditDebtCaseRequest())))
                .andExpect(status().isOk());

        //then
        then(debtCaseService).should().editDebtCase(2152L, 220L, new EditDebtCaseRequest());
    }

    @Test
    @DisplayName("Verifying GET /debt-cases/{debtCaseId} HTTP request matching")
    void findDebtCase() throws Exception {
        //given
        given(debtCaseService.findDebtCase(2154L))
                .willReturn(new FindDebCaseResponse());

        //when
        mockMvc.perform(get("/debt-cases/{debtCaseId}", 2154L)
                        .with(httpBasic("client", "password"))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        //then
        then(debtCaseService).should().findDebtCase(2154L);
    }

    @Test
    @DisplayName("Verifying DELETE /debt-cases/{debtCaseId} HTTP request matching")
    void removeDebtCase() throws Exception {
        //given
        given(debtCaseService.removeDebtCase(2155L))
                .willReturn(new BasicResponse());

        //when
        mockMvc.perform(delete("/debt-cases/{debtCaseId}", 2155L)
                        .with(httpBasic("client", "password"))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        //then
        then(debtCaseService).should().removeDebtCase(2155L);
    }
}