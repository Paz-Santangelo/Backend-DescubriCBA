package com.final_project.descubri_cba.dto;


import com.final_project.descubri_cba.enums.Concurrence;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public abstract class DestinationDTO {
    private Long id;
    private String name;
    private String department;
    private String locality;
    private String address;
    private String urlGoogleMaps;
    private String openingTime;
    private String closingTime;
    private Concurrence levelConcurrence;
    private boolean disabledAccessibility;
    private String numberPhone;
    private String cellPhone;
    private List<String> website;
    private List<String> paymentMethods;
    private List<ImageDTO> imagesDestinations = new ArrayList<>();
    private List<RatingDTO> ratings;
    private List<CommentDTO> comments;
    private int averageScore;
    private Long ownerId;
}
