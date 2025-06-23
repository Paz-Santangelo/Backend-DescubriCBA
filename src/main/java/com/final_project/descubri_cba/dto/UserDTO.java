package com.final_project.descubri_cba.dto;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {

    private Long id;
    private String email;
    private String name;
    private String lastname;
    private String token;
    private String tokenExpirationTime;
    private String role;
    private ImageDTO imageUser;
    private List<CommentDTO> comments = new ArrayList<>();

}
