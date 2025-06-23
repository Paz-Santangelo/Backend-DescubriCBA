package com.final_project.descubri_cba.dto;

import java.time.LocalDate;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CommentDTO {
    private Long id;
    private LocalDate date;
    private String content;
    private UserDTO user;
}
