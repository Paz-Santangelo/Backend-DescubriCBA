package com.final_project.descubri_cba.model;

import com.final_project.descubri_cba.enums.AccommodationType;
import jakarta.persistence.*;
import lombok.*;

@Table(name = "accommodations")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Accommodation extends Destination {
    private AccommodationType type;
}


