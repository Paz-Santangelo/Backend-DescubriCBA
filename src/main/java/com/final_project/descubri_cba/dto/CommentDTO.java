package com.final_project.descubri_cba.dto;

import java.time.LocalDate;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CommentDTO {
    private Long idComment;
    private LocalDate date;
    private String content;
    private Long idUser;
    private Long idDestination;

    public Long getId() {
        return this.idComment;
    }
}
