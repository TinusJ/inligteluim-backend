package com.tinusj.inligteluim.domain.dto;

import com.tinusj.inligteluim.domain.enums.Gender;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Schema(description = "Response DTO representing a person entity")
public record PersonResponseDto(
        @Schema(description = "Unique identifier of the person", example = "1")
        Long id,
        
        @Schema(description = "First name of the person", example = "John")
        String firstName,
        
        @Schema(description = "Last name of the person", example = "Doe")
        String lastName,
        
        @Schema(description = "Maiden name (if applicable)", example = "Smith")
        String maidenName,
        
        @Schema(description = "Middle name(s) of the person", example = "James")
        String middleName,
        
        @Schema(description = "Gender of the person", example = "MALE")
        Gender gender,
        
        @Schema(description = "URL or path to the person's profile image", example = "https://example.com/images/profile.jpg")
        String profileImage,
        
        @Schema(description = "List of URLs or paths to additional images of the person")
        List<String> images,
        
        @Schema(description = "Date of birth of the person", example = "1990-05-15")
        LocalDate birthDate,
        
        @Schema(description = "Date of death (if deceased)", example = "2020-12-31")
        LocalDate dateOfDeath,
        
        @Schema(description = "Set of parent IDs representing the person's parents")
        Set<Long> parentIds,
        
        @Schema(description = "Set of child IDs representing the person's children")
        Set<Long> childIds
) {
}
