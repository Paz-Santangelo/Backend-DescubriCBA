package com.final_project.descubri_cba.model;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Table(name = "images_destinations")
@Entity
public class ImageDestination extends Image {
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    @ManyToOne
    @JoinColumn(name = "destination_id")
    private Destination destination;

    public ImageDestination(String name, String imageUrl, String imageId, User user, Destination destination) {
        super(name, imageUrl, imageId);
        this.user = user;
        this.destination = destination;
    }
}
