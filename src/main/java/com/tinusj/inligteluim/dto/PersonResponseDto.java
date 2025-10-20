package com.tinusj.inligteluim.dto;

import com.tinusj.inligteluim.entity.Gender;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

public record PersonResponseDto(
        Long id,
        String firstName,
        String lastName,
        String maidenName,
        String middleName,
        Gender gender,
        String profileImage,
        List<String> images,
        LocalDate birthDate,
        LocalDate dateOfDeath,
        Set<Long> parentIds,
        Set<Long> childIds
) {
}
