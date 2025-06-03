package com.final_project.descubri_cba.model;

import com.final_project.descubri_cba.enums.TypeBodyOfWater;
import jakarta.persistence.*;

import lombok.*;

@Table(name = "bodies_of_waters")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class BodyOfWater extends Destiny {
    private TypeBodyOfWater typeBodyOfWater;
    private Double entrancePrice;
    private boolean freeAdmission;
    private String cleaningLevel;
}









