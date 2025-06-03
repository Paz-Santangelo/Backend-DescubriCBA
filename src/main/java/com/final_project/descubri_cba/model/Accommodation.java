package com.final_project.descubri_cba.model;
import com.final_project.descubri_cba.enums.AccommodationType;
import jakarta.persistence.*;
import lombok.*;


@Entity
@Table(name = "accommodations")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class Accommodation extends Destiny{

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AccommodationType type;


}


