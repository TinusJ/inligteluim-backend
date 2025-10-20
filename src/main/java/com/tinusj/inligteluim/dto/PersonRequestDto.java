package com.tinusj.inligteluim.dto;

import com.tinusj.inligteluim.entity.Gender;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

public record PersonRequestDto(
        @NotBlank(message = "First name is required")
        String firstName,
        
        @NotBlank(message = "Last name is required")
        String lastName,
        
        String maidenName,
        
        String middleName,
        
        @NotNull(message = "Gender is required")
        Gender gender,
        
        String profileImage,
        
        List<String> images,
        
        @NotNull(message = "Birth date is required")
        @Past(message = "Birth date must be in the past")
        LocalDate birthDate,
        
        @Past(message = "Date of death must be in the past")
        LocalDate dateOfDeath,
        
        Set<Long> parentIds,
        
        Set<Long> childIds
) {
}
