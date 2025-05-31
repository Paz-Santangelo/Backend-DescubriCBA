package com.final_project.descubri_cba.model;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "images_destinies")
public class ImageDestiny extends Image {
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    @ManyToOne
    @JoinColumn(name = "destiny_id")
    private Destiny destiny;

    public ImageDestiny(String name, String imageUrl, String imageId, User user, Destiny destiny) {
        super(name, imageUrl, imageId);
        this.user = user;
        this.destiny = destiny;
    }
}
