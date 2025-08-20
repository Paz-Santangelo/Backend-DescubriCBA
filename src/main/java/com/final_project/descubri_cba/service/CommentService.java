package com.final_project.descubri_cba.service;

import com.final_project.descubri_cba.dto.CommentDTO;
import com.final_project.descubri_cba.model.Comment;
import com.final_project.descubri_cba.model.Destination;
import com.final_project.descubri_cba.model.User;
import com.final_project.descubri_cba.repository.ICommentRepository;
import com.final_project.descubri_cba.repository.IUserRepository;
import com.final_project.descubri_cba.repository.IDestinationRepository;
import com.final_project.descubri_cba.utils.CommentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;

@Service
public class CommentService implements ICommentService {

    @Autowired
    private ICommentRepository commentRepository;

    @Autowired
    private IUserRepository userRepository;

    @Autowired
    private IDestinationRepository destinationRepository;

    @Override
    public List<CommentDTO> findAllComments() {
        List<Comment> comments = commentRepository.findAll();
        return CommentMapper.convertCommentEntityListToCommentDTOList(comments);
    }

    @Override
    public List<CommentDTO> findAllCommentsByUserAndDestination(Long userId, Long destinationId) {
        User userFound = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("Usuario no encontrado."));
        Destination destinationFound = destinationRepository.findById(destinationId).orElseThrow(() -> new RuntimeException("Destino no encontrado."));
        List<Comment> commentsFound = commentRepository.findByUserAndDestination(userFound, destinationFound);
        return CommentMapper.convertCommentEntityListToCommentDTOList(commentsFound);
    }

    @Override
    public CommentDTO findCommentById(Long idComment) {
        Comment commentFound = commentRepository.findById(idComment).orElseThrow(() -> new RuntimeException("No se encontró el comentario."));
        return CommentMapper.convertCommentEntityToCommentDTO(commentFound);
    }

    @Override
    public CommentDTO saveComment(String content, Long idUser, Long idDestination) {
        User userFound = userRepository.findById(idUser).orElseThrow(() -> new RuntimeException("No se encontró el usuario."));
        Destination destinationFound = destinationRepository.findById(idDestination).orElseThrow(() -> new RuntimeException("No se encontró el destino."));

        CommentDTO commentDto = new CommentDTO();
        commentDto.setDate(LocalDate.now());
        commentDto.setContent(content);
        commentDto.setIdUser(idUser);
        commentDto.setIdDestination(idDestination);

        Comment commentConverted = CommentMapper.convertCommentDtoToCommentEntity(commentDto, userFound, destinationFound);
        Comment commentSaved = commentRepository.save(commentConverted);

        return CommentMapper.convertCommentEntityToCommentDTO(commentSaved);
    }

    @Override
    public CommentDTO updateComment(Long idComment, String content, Long idUser, Long idDestination) {
        Comment commentFound = commentRepository.findById(idComment)
                .orElseThrow(() -> new RuntimeException("No se encontró el comentario."));

        User userFound = userRepository.findById(idUser)
                .orElseThrow(() -> new RuntimeException("No se encontró el usuario."));

        Destination destinationFound = destinationRepository.findById(idDestination)
                .orElseThrow(() -> new RuntimeException("No se encontró el destino."));

        commentFound.setDate(LocalDate.now());
        commentFound.setContent(content);
        commentFound.setUser(userFound);
        commentFound.setDestination(destinationFound);

        Comment updatedComment = commentRepository.save(commentFound);

        return CommentMapper.convertCommentEntityToCommentDTO(updatedComment);
    }

    @Override
    public void deleteComment(Long idComment) {
        CommentDTO commentFound = this.findCommentById(idComment);
        commentRepository.deleteById(commentFound.getIdComment());
    }


    public List<CommentDTO> getAllComments() {
        return findAllComments();
    }


    public List<CommentDTO> getCommentsByUserAndDestination(Long idUser, Long idDestination) {
        return findAllCommentsByUserAndDestination(idUser, idDestination);
    }


    public CommentDTO createComment(CommentDTO commentDTO) {
        User userFound = userRepository.findById(commentDTO.getIdUser())
            .orElseThrow(() -> new RuntimeException("Usuario no encontrado."));
        Destination destinationFound = destinationRepository.findById(commentDTO.getIdDestination())
            .orElseThrow(() -> new RuntimeException("Destino no encontrado."));
        Comment comment = CommentMapper.convertCommentDtoToCommentEntity(commentDTO, userFound, destinationFound);
        Comment commentSaved = commentRepository.save(comment);
        return CommentMapper.convertCommentEntityToCommentDTO(commentSaved);
    }
}
