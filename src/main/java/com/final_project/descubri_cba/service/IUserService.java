package com.final_project.descubri_cba.service;

import com.final_project.descubri_cba.dto.UserDTO;
import com.final_project.descubri_cba.model.User;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface IUserService {

    public UserDTO register(User user);

    public List<UserDTO> getAllUsers();

    public UserDTO getUserById(Long id);

    public UserDTO getUserByEmail(String email);

    public void deleteUserById(Long id) throws IOException;

    public UserDTO updateUser(Long idUser, MultipartFile image, String name, String lastname, String email, String password) throws IOException;

}
