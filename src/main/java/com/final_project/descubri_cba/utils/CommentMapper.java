package com.final_project.descubri_cba.utils;

import com.final_project.descubri_cba.dto.CommentDTO;
import com.final_project.descubri_cba.model.Comment;
import com.final_project.descubri_cba.model.Destination;
import com.final_project.descubri_cba.model.User;

import java.util.List;

public class CommentMapper {
    public static CommentDTO convertCommentEntityToCommentDTO(Comment comment) {
        return new CommentDTO(
                comment.getId(),
                comment.getDate(),
                comment.getContent(),
                comment.getUser().getId(),
                comment.getDestination().getId()
        );
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
