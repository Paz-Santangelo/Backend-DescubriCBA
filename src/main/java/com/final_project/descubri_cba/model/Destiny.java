package com.final_project.descubri_cba.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Destiny {
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
    private boolean disabledAccessibility;
    private String numberPhone;
    private String cellPhone;
    @ElementCollection
    private List<String> website;
    @ElementCollection
    private List<String> paymentMethods;
    @OneToMany(mappedBy = "destiny", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<Rating> ratings;
    private int averageScore;
}
