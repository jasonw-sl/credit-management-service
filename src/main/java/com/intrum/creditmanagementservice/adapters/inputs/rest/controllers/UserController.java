package com.intrum.creditmanagementservice.adapters.inputs.rest.controllers;

import com.intrum.creditmanagementservice.adapters.inputs.rest.modules.AuthenticateUserRequest;
import com.intrum.creditmanagementservice.adapters.inputs.rest.modules.AuthenticateUserResponse;
import com.intrum.creditmanagementservice.adapters.inputs.rest.modules.BasicResponse;
import com.intrum.creditmanagementservice.adapters.inputs.rest.modules.RegisterUserRequest;
import com.intrum.creditmanagementservice.adapters.inputs.rest.services.UserService;
import com.intrum.creditmanagementservice.adapters.inputs.rest.utils.ResponseUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/users")
@Tag(name = "User service")
public class UserController {
    @NonNull private final UserService userService;

    @PostMapping
    @Operation(summary = "Register a new user")
    public ResponseEntity<BasicResponse> registerUser(@RequestBody RegisterUserRequest request) {
        return ResponseUtils.handle(request, this.userService::registerUser, HttpStatus.CREATED);
    }

    @PostMapping("/{username}/authenticate")
    @Operation(summary = "Authenticate a user")
    public ResponseEntity<AuthenticateUserResponse> authenticateUser(@PathVariable String username, @RequestBody AuthenticateUserRequest request) {
        return ResponseUtils.handle(request, r -> this.userService.authenticateUser(username, request), HttpStatus.OK);
    }

    @DeleteMapping("/{username}")
    @Operation(summary = "Remove user")
    public ResponseEntity<BasicResponse> removeUser(@PathVariable String username) {
        return ResponseUtils.handle(username, this.userService::removeUser, HttpStatus.OK);
    }
}
