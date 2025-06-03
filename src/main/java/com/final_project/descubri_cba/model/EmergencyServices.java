package com.final_project.descubri_cba.model;
import com.final_project.descubri_cba.enums.TypeOfEmergency;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "emergency_services")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class EmergencyServices extends Destiny {

    @Enumerated(EnumType.STRING)
    @Column(name = "type_of_emergency", nullable = false)
    private TypeOfEmergency typeOfEmergency;


}
