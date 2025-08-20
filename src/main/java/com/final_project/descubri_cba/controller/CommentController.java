package com.final_project.descubri_cba.controller;

import com.final_project.descubri_cba.dto.CommentDTO;
import com.final_project.descubri_cba.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/comentarios")
public class CommentController {

    @Autowired
    private CommentService commentService;


    @GetMapping("/todos")
    public ResponseEntity<List<CommentDTO>> getAllComments() {
        List<CommentDTO> comentarios = commentService.getAllComments();
        return ResponseEntity.ok(comentarios);
    }


    @GetMapping("/{idUsuario}/{idDestino}")
    public ResponseEntity<List<CommentDTO>> getCommentsByUserAndDestination(@PathVariable Long idUsuario, @PathVariable Long idDestino) {
        List<CommentDTO> comentarios = commentService.getCommentsByUserAndDestination(idUsuario, idDestino);
        return ResponseEntity.ok(comentarios);
    }


    @PostMapping("/crear")
    public ResponseEntity<CommentDTO> createComment(@RequestBody CommentDTO commentDTO) {
        CommentDTO comentarioCreado = commentService.createComment(commentDTO);
        return ResponseEntity.ok(comentarioCreado);
    }
}