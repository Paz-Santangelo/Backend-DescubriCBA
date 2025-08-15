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
import java.util.Optional;

@Service
public class CommentService implements ICommentService {
    @Autowired
    private ICommentRepository commentRepository;
    @Autowired
    private IUserRepository userRepository;
    @Autowired
    private IDestinationRepository destinationRepository;
    @Autowired
    private CommentMapper commentMapper;

    @Override
    public List<CommentDTO> findAllComments() {
        List<Comment> comments = commentRepository.findAll();
        return CommentMapper.convertCommentEntityListToCommentDTOList(comments);
    }

    @Override
    public List<CommentDTO> findAllCommentsByUserAndDestination(Long userId, Long destinationId) {
        Optional<User> userOpt = userRepository.findById(userId);
        if (userOpt.isEmpty()) throw new RuntimeException("Usuario no encontrado");
        Optional<Destination> destOpt = destinationRepository.findById(destinationId);
        if (destOpt.isEmpty()) throw new RuntimeException("Destino no encontrado");
        List<Comment> comments = commentRepository.findByUserAndDestination(userOpt.get(), destOpt.get());
        return CommentMapper.convertCommentEntityListToCommentDTOList(comments);
    }

    @Override
    public CommentDTO findCommentById(Long commentId) {
        Optional<Comment> commentOpt = commentRepository.findById(commentId);
        if (commentOpt.isEmpty()) throw new RuntimeException("Comentario no encontrado");
        return CommentMapper.convertCommentEntityToCommentDTO(commentOpt.get());
    }

    @Override
    public CommentDTO saveComment(String content, Long idUser, Long idDestination) {
        Optional<User> userOpt = userRepository.findById(idUser);
        if (userOpt.isEmpty()) throw new RuntimeException("Usuario no encontrado");
        Optional<Destination> destOpt = destinationRepository.findById(idDestination);
        if (destOpt.isEmpty()) throw new RuntimeException("Destino no encontrado");
        CommentDTO commentDTO = new CommentDTO();
        commentDTO.setContent(content);
        commentDTO.setDate(LocalDate.now());
        commentDTO.setIdUser(idUser);
        commentDTO.setIdDestination(idDestination);
        Comment comment = CommentMapper.convertCommentDtoToCommentEntity(commentDTO, userOpt.get(), destOpt.get());
        commentRepository.save(comment);
        return CommentMapper.convertCommentEntityToCommentDTO(comment);
    }

    @Override
    public CommentDTO updateComment(Long idComment, String content, Long idUser, Long idDestination) {
        Optional<Comment> commentOpt = commentRepository.findById(idComment);
        if (commentOpt.isEmpty()) throw new RuntimeException("Comentario no encontrado");
        Optional<User> userOpt = userRepository.findById(idUser);
        if (userOpt.isEmpty()) throw new RuntimeException("Usuario no encontrado");
        Optional<Destination> destOpt = destinationRepository.findById(idDestination);
        if (destOpt.isEmpty()) throw new RuntimeException("Destino no encontrado");
        Comment comment = commentOpt.get();
        comment.setContent(content);
        comment.setDate(LocalDate.now());
        comment.setUser(userOpt.get());
        comment.setDestination(destOpt.get());
        commentRepository.save(comment);
        return CommentMapper.convertCommentEntityToCommentDTO(comment);
    }

    @Override
    public void deleteComment(Long commentId) {
        CommentDTO commentDTO = findCommentById(commentId);
        commentRepository.deleteById(commentId);
    }
}
