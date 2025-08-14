package com.final_project.descubri_cba.service;

import com.final_project.descubri_cba.dto.CommentDTO;
import java.util.List;

public interface ICommentService {
    List<CommentDTO> findAllComments();
    List<CommentDTO> findAllCommentsByUserAndDestination(Long idUser, Long idDestination);
    CommentDTO findCommentById(Long idComment);
    CommentDTO saveComment(String content, Long idUser, Long idDestination);
    CommentDTO updateComment(Long idComment, String content, Long idUser, Long idDestination);
    void deleteComment(Long idComment);
}