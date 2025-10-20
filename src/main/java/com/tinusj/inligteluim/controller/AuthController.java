package com.tinusj.inligteluim.controller;

import com.tinusj.inligteluim.domain.dto.JwtAuthenticationResponse;
import com.tinusj.inligteluim.domain.dto.SignInRequest;
import com.tinusj.inligteluim.domain.dto.SignUpRequest;
import com.tinusj.inligteluim.service.AuthenticationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Tag(name = "Authentication", description = "Authentication endpoints for user signup and signin")
public class AuthController {
    
    private final AuthenticationService authenticationService;
    
    @PostMapping("/signup")
    @Operation(
        summary = "Register a new user",
        description = "Creates a new user account and returns a JWT token for authentication"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "User successfully registered and JWT token returned"),
        @ApiResponse(responseCode = "400", description = "Invalid input data or user already exists"),
        @ApiResponse(responseCode = "401", description = "Unauthorized")
    })
    public ResponseEntity<JwtAuthenticationResponse> signup(@Valid @RequestBody SignUpRequest request) {
        return ResponseEntity.ok(authenticationService.signup(request));
    }
    
    @PostMapping("/signin")
    @Operation(
        summary = "Authenticate a user",
        description = "Authenticates user credentials and returns a JWT token"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "User successfully authenticated and JWT token returned"),
        @ApiResponse(responseCode = "400", description = "Invalid input data"),
        @ApiResponse(responseCode = "401", description = "Invalid credentials")
    })
    public ResponseEntity<JwtAuthenticationResponse> signin(@Valid @RequestBody SignInRequest request) {
        return ResponseEntity.ok(authenticationService.signin(request));
    }
}
