package com.final_project.descubri_cba.repository;

import com.final_project.descubri_cba.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IUserRepository extends JpaRepository<User, Long> {

    boolean existsByEmail(String email);

    Optional<User> findByEmail(String email);

    @Query("SELECT u FROM User u " +
            "WHERE LOWER(u.name) LIKE LOWER(CONCAT('%', :searchTerm, '%')) " +
            "   OR LOWER(u.lastname) LIKE LOWER(CONCAT('%', :searchTerm, '%'))")
    List<User> findByNameOrLastnameIgnoreCase(@Param("searchTerm") String searchTerm);
}
