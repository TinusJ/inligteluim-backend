package com.tinusj.inligteluim.service;

import com.tinusj.inligteluim.domain.dto.PersonRequestDto;
import com.tinusj.inligteluim.domain.dto.PersonResponseDto;

import java.util.List;

public interface PersonService {
    PersonResponseDto createPerson(PersonRequestDto requestDto);
    
    PersonResponseDto getPersonById(Long id);
    
    List<PersonResponseDto> getAllPersons();
    
    PersonResponseDto updatePerson(Long id, PersonRequestDto requestDto);
    
    void deletePerson(Long id);
}
