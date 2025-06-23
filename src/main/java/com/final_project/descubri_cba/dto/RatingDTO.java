package com.final_project.descubri_cba.dto;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class RatingDTO {

    @NotNull(message = "El nombre del destino no puede ser nulo")
    @Size(min = 1, max = 100, message = "El nombre del destino debe tener entre 1 y 100 caracteres")
    private String nameDestination;

    @Min(value = 1, message = "El puntaje mínimo es 1")
    @Max(value = 5, message = "El puntaje máximo es 5")
    private int score;

    public RatingDTO() {
    }

    public RatingDTO(String nameDestination, int score) {
        this.nameDestination = nameDestination;
        this.score = score;
    }

    public String getNameDestination() {
        return nameDestination;
    }

    public void setNameDestination(String nameDestination) {
        this.nameDestination = nameDestination;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    @Override
    public String toString() {
        return "RatingDTO{" +
                "nameDestination='" + nameDestination + '\'' +
                ", score=" + score +
                '}';
    }
}
