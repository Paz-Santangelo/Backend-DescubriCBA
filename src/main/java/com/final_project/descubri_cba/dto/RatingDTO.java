package com.final_project.descubri_cba.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RatingDTO {
    private int score;
    private Long idUser;
    private Long idDestination;
    private String nameDestination;
}
