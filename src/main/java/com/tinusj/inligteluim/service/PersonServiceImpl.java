package com.tinusj.inligteluim.service;

import com.tinusj.inligteluim.domain.dto.PersonRequestDto;
import com.tinusj.inligteluim.domain.dto.PersonResponseDto;
import com.tinusj.inligteluim.domain.entity.Person;
import com.tinusj.inligteluim.repository.PersonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PersonServiceImpl implements PersonService {
    
    private final PersonRepository personRepository;
    
    @Override
    @Transactional
    public PersonResponseDto createPerson(PersonRequestDto requestDto) {
        Person person = mapToEntity(requestDto);
        
        // Set parents if provided
        if (requestDto.parentIds() != null && !requestDto.parentIds().isEmpty()) {
            Set<Person> parents = new HashSet<>(personRepository.findAllById(requestDto.parentIds()));
            person.setParents(parents);
        }
        
        // Set children if provided
        if (requestDto.childIds() != null && !requestDto.childIds().isEmpty()) {
            Set<Person> children = new HashSet<>(personRepository.findAllById(requestDto.childIds()));
            person.setChildren(children);
        }
        
        // Save the person first to generate an ID
        Person savedPerson = personRepository.save(person);
        
        // Set spouse if provided
        if (requestDto.spouseId() != null) {
            setSpouse(savedPerson, requestDto.spouseId());
            savedPerson = personRepository.save(savedPerson);
        }
        
        return mapToResponseDto(savedPerson);
    }
    
    @Override
    @Transactional(readOnly = true)
    public PersonResponseDto getPersonById(Long id) {
        Person person = personRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Person not found with id: " + id));
        return mapToResponseDto(person);
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<PersonResponseDto> getAllPersons() {
        return personRepository.findAll().stream()
                .map(this::mapToResponseDto)
                .collect(Collectors.toList());
    }
    
    @Override
    @Transactional
    public PersonResponseDto updatePerson(Long id, PersonRequestDto requestDto) {
        Person person = personRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Person not found with id: " + id));
        
        // Update basic fields
        updateBasicFields(person, requestDto);
        
        // Update parents if provided
        if (requestDto.parentIds() != null) {
            Set<Person> parents = new HashSet<>(personRepository.findAllById(requestDto.parentIds()));
            person.setParents(parents);
        }
        
        // Update children if provided
        if (requestDto.childIds() != null) {
            Set<Person> children = new HashSet<>(personRepository.findAllById(requestDto.childIds()));
            person.setChildren(children);
        }
        
        // Update spouse if provided
        if (requestDto.spouseId() != null) {
            setSpouse(person, requestDto.spouseId());
        }
        
        Person updatedPerson = personRepository.save(person);
        return mapToResponseDto(updatedPerson);
    }
    
    @Override
    @Transactional
    public void deletePerson(Long id) {
        if (!personRepository.existsById(id)) {
            throw new RuntimeException("Person not found with id: " + id);
        }
        personRepository.deleteById(id);
    }
    
    private void updateBasicFields(Person person, PersonRequestDto requestDto) {
        person.setFirstName(requestDto.firstName());
        person.setLastName(requestDto.lastName());
        person.setMaidenName(requestDto.maidenName());
        person.setMiddleName(requestDto.middleName());
        person.setGender(requestDto.gender());
        person.setProfileImage(requestDto.profileImage());
        person.setImages(requestDto.images());
        person.setBirthDate(requestDto.birthDate());
        person.setDateOfDeath(requestDto.dateOfDeath());
    }
    
    private void setSpouse(Person person, Long spouseId) {
        // Validation: prevent self-spouse assignment
        if (person.getId() != null && person.getId().equals(spouseId)) {
            throw new IllegalArgumentException("A person cannot be their own spouse");
        }
        
        Person spouse = personRepository.findById(spouseId)
                .orElseThrow(() -> new RuntimeException("Spouse not found with id: " + spouseId));
        
        // Clear existing spouse relationships
        if (person.getSpouse() != null) {
            Person oldSpouse = person.getSpouse();
            oldSpouse.setSpouse(null);
            personRepository.save(oldSpouse);
        }
        
        if (spouse.getSpouse() != null && !spouse.getSpouse().equals(person)) {
            Person oldSpouse = spouse.getSpouse();
            oldSpouse.setSpouse(null);
            personRepository.save(oldSpouse);
        }
        
        // Set bidirectional relationship
        person.setSpouse(spouse);
        spouse.setSpouse(person);
        personRepository.save(spouse);
    }
    
    private Person mapToEntity(PersonRequestDto dto) {
        return Person.builder()
                .firstName(dto.firstName())
                .lastName(dto.lastName())
                .maidenName(dto.maidenName())
                .middleName(dto.middleName())
                .gender(dto.gender())
                .profileImage(dto.profileImage())
                .images(dto.images())
                .birthDate(dto.birthDate())
                .dateOfDeath(dto.dateOfDeath())
                .build();
    }
    
    private PersonResponseDto mapToResponseDto(Person person) {
        // Initialize lazy collections within transaction
        List<String> images = person.getImages() != null ? new ArrayList<>(person.getImages()) : new ArrayList<>();
        
        Set<Long> parentIds = person.getParents() != null 
                ? person.getParents().stream().map(Person::getId).collect(Collectors.toSet())
                : new HashSet<>();
        
        Set<Long> childIds = person.getChildren() != null
                ? person.getChildren().stream().map(Person::getId).collect(Collectors.toSet())
                : new HashSet<>();
        
        Long spouseId = person.getSpouse() != null ? person.getSpouse().getId() : null;
        
        return new PersonResponseDto(
                person.getId(),
                person.getFirstName(),
                person.getLastName(),
                person.getMaidenName(),
                person.getMiddleName(),
                person.getGender(),
                person.getProfileImage(),
                images,
                person.getBirthDate(),
                person.getDateOfDeath(),
                parentIds,
                childIds,
                spouseId
        );
    }
}
