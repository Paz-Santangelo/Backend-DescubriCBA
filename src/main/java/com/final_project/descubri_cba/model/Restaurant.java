package com.final_project.descubri_cba.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Table(name = "restaurants")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Restaurant extends Destination {
    @ElementCollection
    private List<String> cuisineType;
    private boolean delivery;
    private boolean reservations;
}
