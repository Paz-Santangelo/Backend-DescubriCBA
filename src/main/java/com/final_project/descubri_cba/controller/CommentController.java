package com.final_project.descubri_cba.controller;

import com.final_project.descubri_cba.dto.CommentDTO;
import com.final_project.descubri_cba.service.ICommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/comments")
public class CommentController {

    @Autowired
    private ICommentService commentService;

    @GetMapping("/all")
    public List<CommentDTO> findAllComments() {
        return commentService.findAllComments();
    }

    @GetMapping("/destination/{idDestination}")
    public List<CommentDTO> findAllCommentsByDestination(@PathVariable Long idDestination) {
        return commentService.findAllCommentsByDestination(idDestination);
    }

    @GetMapping("/{idUser}/{idDestination}")
    public List<CommentDTO> findAllCommentsByUserAndDestination(@PathVariable Long idUser, @PathVariable Long idDestination) {
        return commentService.findAllCommentsByUserAndDestination(idUser, idDestination);
    }

    @GetMapping("/{idComment}")
    public ResponseEntity<?> findCommentById(@PathVariable Long idComment) {
        try {
            CommentDTO comment = commentService.findCommentById(idComment);
            return ResponseEntity.ok(comment);
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body("No se encontró el comentario.");
        }
    }

    @PostMapping("/create")
    public ResponseEntity<CommentDTO> saveComment(@RequestBody CommentDTO commentDto) {
        CommentDTO commentSaved = commentService.saveComment(commentDto.getContent(), commentDto.getIdUser(), commentDto.getIdDestination());
        return ResponseEntity.ok(commentSaved);
    }

    @PutMapping("/update/{idComment}")
    public ResponseEntity<CommentDTO> updateComment(@PathVariable Long idComment, @RequestBody CommentDTO commentDto) {
        CommentDTO commentUpdated = commentService.updateComment(idComment, commentDto.getContent(), commentDto.getIdUser(), commentDto.getIdDestination());
        return ResponseEntity.ok(commentUpdated);
    }

    @DeleteMapping("/delete/{idComment}")
    public ResponseEntity<String> deleteComment(@PathVariable Long idComment) {
        commentService.deleteComment(idComment);
        return ResponseEntity.ok("El comentario fue eliminado con éxito.");
    }
}