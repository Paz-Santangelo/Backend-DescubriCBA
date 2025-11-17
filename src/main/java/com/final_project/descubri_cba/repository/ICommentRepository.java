package com.final_project.descubri_cba.repository;

import com.final_project.descubri_cba.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import com.final_project.descubri_cba.model.User;
import com.final_project.descubri_cba.model.Destination;

@Repository
public interface ICommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByUserAndDestination(User user, Destination destination);
    List<Comment> findAllByDestinationId(Long idDestination);
}
