package com.final_project.descubri_cba.model;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "restaurants")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class Restaurant extends Destiny{

    @ElementCollection
    @CollectionTable(name = "restaurant_cuisines", joinColumns = @JoinColumn(name = "restaurant_id"))
    @Column(name = "cuisine_type")
    private List<String> cuisineType;

    private boolean delivery;

    private boolean reservations;
}
