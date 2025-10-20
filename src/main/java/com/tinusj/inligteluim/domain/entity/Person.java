package com.tinusj.inligteluim.domain.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.tinusj.inligteluim.domain.enums.Gender;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@AllArgsConstructor
@Builder
@Entity
@Getter
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
@NoArgsConstructor
@Setter
@Table(name = "persons")
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    private String maidenName;

    private String middleName;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Gender gender;

    @Lob
    private String profileImage;

    @ElementCollection
    @CollectionTable(name = "person_images", joinColumns = @JoinColumn(name = "person_id"))
    @Column(name = "image_url")
    @Builder.Default
    private List<String> images = new ArrayList<>();

    @Column(nullable = false)
    private LocalDate birthDate;

    private LocalDate dateOfDeath;

    @ManyToMany
    @JoinTable(
            name = "person_parents",
            joinColumns = @JoinColumn(name = "child_id"),
            inverseJoinColumns = @JoinColumn(name = "parent_id")
    )
    @Builder.Default
    private Set<Person> parents = new HashSet<>();

    @ManyToMany(mappedBy = "parents")
    @Builder.Default
    private Set<Person> children = new HashSet<>();

    @JoinColumn(name = "spouse_id")
    @OneToOne
    private Person spouse;
}
