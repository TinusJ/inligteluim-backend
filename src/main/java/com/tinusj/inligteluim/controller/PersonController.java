package com.tinusj.inligteluim.controller;

import com.tinusj.inligteluim.dto.PersonRequestDto;
import com.tinusj.inligteluim.dto.PersonResponseDto;
import com.tinusj.inligteluim.service.PersonService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/persons")
@RequiredArgsConstructor
@Tag(name = "Person Management", description = "Endpoints for managing person entities in the family tree")
public class PersonController {
    
    private final PersonService personService;
    
    @PostMapping
    @Operation(
        summary = "Create a new person",
        description = "Creates a new person entity with the provided details including family relationships"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Person successfully created"),
        @ApiResponse(responseCode = "400", description = "Invalid input data"),
        @ApiResponse(responseCode = "401", description = "Unauthorized - JWT token missing or invalid"),
        @ApiResponse(responseCode = "403", description = "Forbidden - insufficient permissions")
    })
    public ResponseEntity<PersonResponseDto> createPerson(@Valid @RequestBody PersonRequestDto requestDto) {
        PersonResponseDto response = personService.createPerson(requestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
    
    @GetMapping("/{id}")
    @Operation(
        summary = "Get person by ID",
        description = "Retrieves a person entity by their unique identifier"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Person found and returned"),
        @ApiResponse(responseCode = "401", description = "Unauthorized - JWT token missing or invalid"),
        @ApiResponse(responseCode = "403", description = "Forbidden - insufficient permissions"),
        @ApiResponse(responseCode = "404", description = "Person not found")
    })
    public ResponseEntity<PersonResponseDto> getPersonById(@PathVariable Long id) {
        PersonResponseDto response = personService.getPersonById(id);
        return ResponseEntity.ok(response);
    }
    
    @GetMapping
    @Operation(
        summary = "Get all persons",
        description = "Retrieves a list of all person entities in the system"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "List of persons returned successfully"),
        @ApiResponse(responseCode = "401", description = "Unauthorized - JWT token missing or invalid"),
        @ApiResponse(responseCode = "403", description = "Forbidden - insufficient permissions")
    })
    public ResponseEntity<List<PersonResponseDto>> getAllPersons() {
        List<PersonResponseDto> responses = personService.getAllPersons();
        return ResponseEntity.ok(responses);
    }
    
    @PutMapping("/{id}")
    @Operation(
        summary = "Update person",
        description = "Updates an existing person entity with the provided details"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Person successfully updated"),
        @ApiResponse(responseCode = "400", description = "Invalid input data"),
        @ApiResponse(responseCode = "401", description = "Unauthorized - JWT token missing or invalid"),
        @ApiResponse(responseCode = "403", description = "Forbidden - insufficient permissions"),
        @ApiResponse(responseCode = "404", description = "Person not found")
    })
    public ResponseEntity<PersonResponseDto> updatePerson(
            @PathVariable Long id,
            @Valid @RequestBody PersonRequestDto requestDto) {
        PersonResponseDto response = personService.updatePerson(id, requestDto);
        return ResponseEntity.ok(response);
    }
    
    @DeleteMapping("/{id}")
    @Operation(
        summary = "Delete person",
        description = "Deletes a person entity by their unique identifier"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Person successfully deleted"),
        @ApiResponse(responseCode = "401", description = "Unauthorized - JWT token missing or invalid"),
        @ApiResponse(responseCode = "403", description = "Forbidden - insufficient permissions"),
        @ApiResponse(responseCode = "404", description = "Person not found")
    })
    public ResponseEntity<Void> deletePerson(@PathVariable Long id) {
        personService.deletePerson(id);
        return ResponseEntity.noContent().build();
    }
}
