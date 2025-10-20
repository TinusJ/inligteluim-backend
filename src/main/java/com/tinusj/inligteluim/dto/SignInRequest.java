package com.tinusj.inligteluim.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

@Schema(description = "Request DTO for user authentication")
public record SignInRequest(
        @NotBlank(message = "Username is required")
        @Schema(description = "Username of the account", example = "johndoe")
        String username,
        
        @NotBlank(message = "Password is required")
        @Schema(description = "Password for the account", example = "password123")
        String password
) {
}
