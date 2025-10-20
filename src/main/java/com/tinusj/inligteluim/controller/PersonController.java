package com.tinusj.inligteluim.controller;

import com.tinusj.inligteluim.dto.PersonRequestDto;
import com.tinusj.inligteluim.dto.PersonResponseDto;
import com.tinusj.inligteluim.service.PersonService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/persons")
@RequiredArgsConstructor
public class PersonController {
    
    private final PersonService personService;
    
    @PostMapping
    public ResponseEntity<PersonResponseDto> createPerson(@Valid @RequestBody PersonRequestDto requestDto) {
        PersonResponseDto response = personService.createPerson(requestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<PersonResponseDto> getPersonById(@PathVariable Long id) {
        PersonResponseDto response = personService.getPersonById(id);
        return ResponseEntity.ok(response);
    }
    
    @GetMapping
    public ResponseEntity<List<PersonResponseDto>> getAllPersons() {
        List<PersonResponseDto> responses = personService.getAllPersons();
        return ResponseEntity.ok(responses);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<PersonResponseDto> updatePerson(
            @PathVariable Long id,
            @Valid @RequestBody PersonRequestDto requestDto) {
        PersonResponseDto response = personService.updatePerson(id, requestDto);
        return ResponseEntity.ok(response);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePerson(@PathVariable Long id) {
        personService.deletePerson(id);
        return ResponseEntity.noContent().build();
    }
}
