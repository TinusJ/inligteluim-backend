package com.tinusj.inligteluim.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Response DTO containing JWT authentication token")
public record JwtAuthenticationResponse(
        @Schema(description = "JWT token for authenticating subsequent requests", example = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...")
        String token
) {
}
