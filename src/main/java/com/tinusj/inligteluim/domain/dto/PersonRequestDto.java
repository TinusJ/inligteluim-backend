package com.tinusj.inligteluim.domain.dto;

import com.tinusj.inligteluim.domain.enums.Gender;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Schema(description = "Request DTO for creating or updating a person entity")
public record PersonRequestDto(
        @NotBlank(message = "First name is required")
        @Schema(description = "First name of the person", example = "John")
        String firstName,
        
        @NotBlank(message = "Last name is required")
        @Schema(description = "Last name of the person", example = "Doe")
        String lastName,
        
        @Schema(description = "Maiden name (if applicable)", example = "Smith")
        String maidenName,
        
        @Schema(description = "Middle name(s) of the person", example = "James")
        String middleName,
        
        @NotNull(message = "Gender is required")
        @Schema(description = "Gender of the person", example = "MALE")
        Gender gender,
        
        @Schema(description = "URL or path to the person's profile image", example = "https://example.com/images/profile.jpg")
        String profileImage,
        
        @Schema(description = "List of URLs or paths to additional images of the person")
        List<String> images,
        
        @NotNull(message = "Birth date is required")
        @Past(message = "Birth date must be in the past")
        @Schema(description = "Date of birth of the person", example = "1990-05-15")
        LocalDate birthDate,
        
        @Past(message = "Date of death must be in the past")
        @Schema(description = "Date of death (if deceased)", example = "2020-12-31")
        LocalDate dateOfDeath,
        
        @Schema(description = "Set of parent IDs representing the person's parents")
        Set<Long> parentIds,
        
        @Schema(description = "Set of child IDs representing the person's children")
        Set<Long> childIds
) {
}
