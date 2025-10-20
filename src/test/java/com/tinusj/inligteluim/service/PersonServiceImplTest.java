package com.tinusj.inligteluim.service;

import com.tinusj.inligteluim.domain.dto.PersonRequestDto;
import com.tinusj.inligteluim.domain.dto.PersonResponseDto;
import com.tinusj.inligteluim.domain.entity.Person;
import com.tinusj.inligteluim.domain.enums.Gender;
import com.tinusj.inligteluim.repository.PersonRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PersonServiceImplTest {

    @Mock
    private PersonRepository personRepository;

    @InjectMocks
    private PersonServiceImpl personService;

    private Person person1;
    private Person person2;
    private PersonRequestDto requestDto;

    @BeforeEach
    void setUp() {
        person1 = Person.builder()
                .id(1L)
                .firstName("John")
                .lastName("Doe")
                .gender(Gender.MALE)
                .birthDate(LocalDate.of(1990, 1, 1))
                .parents(new HashSet<>())
                .children(new HashSet<>())
                .images(new ArrayList<>())
                .build();

        person2 = Person.builder()
                .id(2L)
                .firstName("Jane")
                .lastName("Smith")
                .gender(Gender.FEMALE)
                .birthDate(LocalDate.of(1992, 5, 15))
                .parents(new HashSet<>())
                .children(new HashSet<>())
                .images(new ArrayList<>())
                .build();

        requestDto = new PersonRequestDto(
                "John",
                "Doe",
                null,
                null,
                Gender.MALE,
                null,
                new ArrayList<>(),
                LocalDate.of(1990, 1, 1),
                null,
                new HashSet<>(),
                new HashSet<>(),
                null
        );
    }

    @Test
    void testCreatePersonWithoutSpouse() {
        when(personRepository.save(any(Person.class))).thenReturn(person1);

        PersonResponseDto responseDto = personService.createPerson(requestDto);

        assertNotNull(responseDto);
        assertEquals(1L, responseDto.id());
        assertEquals("John", responseDto.firstName());
        assertNull(responseDto.spouseId());
        verify(personRepository, times(1)).save(any(Person.class));
    }

    @Test
    void testCreatePersonWithSpouse() {
        PersonRequestDto requestWithSpouse = new PersonRequestDto(
                "John",
                "Doe",
                null,
                null,
                Gender.MALE,
                null,
                new ArrayList<>(),
                LocalDate.of(1990, 1, 1),
                null,
                new HashSet<>(),
                new HashSet<>(),
                2L
        );

        when(personRepository.save(any(Person.class))).thenReturn(person1);
        when(personRepository.findById(2L)).thenReturn(Optional.of(person2));

        person1.setSpouse(person2);
        when(personRepository.save(person1)).thenReturn(person1);

        PersonResponseDto responseDto = personService.createPerson(requestWithSpouse);

        assertNotNull(responseDto);
        assertEquals(1L, responseDto.id());
        assertEquals(2L, responseDto.spouseId());
        verify(personRepository, atLeast(2)).save(any(Person.class));
        verify(personRepository, times(1)).findById(2L);
    }

    @Test
    void testUpdatePersonWithSpouse() {
        PersonRequestDto updateRequest = new PersonRequestDto(
                "John",
                "Doe",
                null,
                null,
                Gender.MALE,
                null,
                new ArrayList<>(),
                LocalDate.of(1990, 1, 1),
                null,
                new HashSet<>(),
                new HashSet<>(),
                2L
        );

        when(personRepository.findById(1L)).thenReturn(Optional.of(person1));
        when(personRepository.findById(2L)).thenReturn(Optional.of(person2));
        person1.setSpouse(person2);
        when(personRepository.save(any(Person.class))).thenReturn(person1);

        PersonResponseDto responseDto = personService.updatePerson(1L, updateRequest);

        assertNotNull(responseDto);
        assertEquals(1L, responseDto.id());
        assertEquals(2L, responseDto.spouseId());
        verify(personRepository, times(1)).findById(1L);
        verify(personRepository, times(1)).findById(2L);
    }

    @Test
    void testPreventSelfSpouseAssignment() {
        PersonRequestDto selfSpouseRequest = new PersonRequestDto(
                "John",
                "Doe",
                null,
                null,
                Gender.MALE,
                null,
                new ArrayList<>(),
                LocalDate.of(1990, 1, 1),
                null,
                new HashSet<>(),
                new HashSet<>(),
                1L
        );

        when(personRepository.findById(1L)).thenReturn(Optional.of(person1));

        assertThrows(IllegalArgumentException.class, () -> {
            personService.updatePerson(1L, selfSpouseRequest);
        });
    }

    @Test
    void testGetPersonByIdWithSpouse() {
        person1.setSpouse(person2);
        when(personRepository.findById(1L)).thenReturn(Optional.of(person1));

        PersonResponseDto responseDto = personService.getPersonById(1L);

        assertNotNull(responseDto);
        assertEquals(1L, responseDto.id());
        assertEquals(2L, responseDto.spouseId());
        verify(personRepository, times(1)).findById(1L);
    }
}
