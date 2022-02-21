package com.intrum.creditmanagementservice.adapters.inputs.rest.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.intrum.creditmanagementservice.adapters.inputs.rest.modules.AuthenticateUserRequest;
import com.intrum.creditmanagementservice.adapters.inputs.rest.modules.AuthenticateUserResponse;
import com.intrum.creditmanagementservice.adapters.inputs.rest.modules.BasicResponse;
import com.intrum.creditmanagementservice.adapters.inputs.rest.modules.RegisterUserRequest;
import com.intrum.creditmanagementservice.adapters.inputs.rest.services.UserService;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
@DisplayName("User controller test")
class UserControllerIT {

    @MockBean
    private UserService userService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("Verifying POST /users HTTP request matching")
    void registerUser() throws Exception {
        //given
        given(userService.registerUser(new RegisterUserRequest()))
                .willReturn(new BasicResponse());

        //when
        mockMvc.perform(post("/users")
                        .with(httpBasic("client", "password"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new RegisterUserRequest())))
                .andExpect(status().isCreated());

        //then
        then(userService).should().registerUser(new RegisterUserRequest());
    }

    @Test
    @DisplayName("Verifying POST /users/{username}/authenticate HTTP request matching")
    void authenticateUser() throws Exception {
        //given
        given(userService.authenticateUser("oskada", new AuthenticateUserRequest()))
                .willReturn(new AuthenticateUserResponse());

        //when
        mockMvc.perform(post("/users/{username}/authenticate", "oskada")
                        .with(httpBasic("client", "password"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new AuthenticateUserRequest())))
                .andExpect(status().isOk());

        //then
        then(userService).should().authenticateUser("oskada", new AuthenticateUserRequest());
    }

    @Test
    @DisplayName("Verifying DELETE /users/{username} HTTP request matching")
    void removeUser() throws Exception {
        //given
        given(userService.removeUser("oskada"))
                .willReturn(new BasicResponse());

        //when
        mockMvc.perform(delete("/users/{username}", "oskada")
                        .with(httpBasic("client", "password"))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        //then
        then(userService).should().removeUser("oskada");
    }
}