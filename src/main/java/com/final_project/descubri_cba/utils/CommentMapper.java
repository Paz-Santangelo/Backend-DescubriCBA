package com.final_project.descubri_cba.utils;

import com.final_project.descubri_cba.dto.CommentDTO;
import com.final_project.descubri_cba.model.Comment;
import com.final_project.descubri_cba.model.Destination;
import com.final_project.descubri_cba.model.User;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CommentMapper {
    public static CommentDTO convertCommentEntityToCommentDTO(Comment comment) {
        CommentDTO commentDTO = new CommentDTO();
        commentDTO.setId(comment.getId());
        commentDTO.setDate(comment.getDate());
        commentDTO.setContent(comment.getContent());
        commentDTO.setIdUser(comment.getUser().getId());
        commentDTO.setUsername(comment.getUser().getName());
        commentDTO.setIdDestination(comment.getDestination().getId());
        return commentDTO;
    }

    public static List<CommentDTO> convertCommentEntityListToCommentDTOList(List<Comment> comments) {
        return comments.stream().map(CommentMapper::convertCommentEntityToCommentDTO).toList();
    }

    public static Comment convertCommentDtoToCommentEntity(CommentDTO commentDto, User user, Destination destination) {
        return new Comment(
                commentDto.getId(),
                commentDto.getDate(),
                commentDto.getContent(),
                user,
                destination
        );
    }
}
