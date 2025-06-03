package com.final_project.descubri_cba.model;

import com.final_project.descubri_cba.enums.TypeOfEmergency;
import jakarta.persistence.*;
import lombok.*;

@Table(name = "emergency_services")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class EmergencyServices extends Destiny {
    private TypeOfEmergency typeOfEmergency;
}
