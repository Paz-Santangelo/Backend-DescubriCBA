package com.final_project.descubri_cba.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.final_project.descubri_cba.enums.Concurrence;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Table(name = "destinations")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Destination {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String department;
    private String locality;
    private String address;
    @Column(length = 1000)
    private String urlGoogleMaps;
    private String openingTime;
    private String closingTime;
    private Concurrence levelConcurrence;
    private boolean disabledAccessibility;
    private String numberPhone;
    private String cellPhone;
    @ElementCollection
    private List<String> website;
    @ElementCollection
    private List<String> paymentMethods;
    @OneToMany(mappedBy = "destination", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<Rating> ratings;
    @OneToMany(mappedBy = "destination", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> comments;
    private int averageScore;
}
