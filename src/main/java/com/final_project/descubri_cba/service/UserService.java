package com.final_project.descubri_cba.service;

import com.final_project.descubri_cba.dto.UserDTO;
import com.final_project.descubri_cba.exception.CustomException;
import com.final_project.descubri_cba.model.ImageUser;
import com.final_project.descubri_cba.model.User;
import com.final_project.descubri_cba.repository.IUserRepository;
import com.final_project.descubri_cba.utils.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class UserService implements IUserService {

    @Autowired
    private IUserRepository userRepository;

    @Autowired
    private IImageService imageService;

    @Override
    public UserDTO register(User user) {
        if (user.getRole() == null || user.getRole().isBlank()) {
            user.setRole("USER");
        }

        // Asignar imagen por defecto si no se proporciona
        if (user.getImageUser() == null) {
            ImageUser defaultImage = new ImageUser(
                    "default-user.jpg",
                    "https://as1.ftcdn.net/jpg/00/64/67/52/1000_F_64675209_7ve2XQANuzuHjMZXP3aIYIpsDKEbF5dD.jpg",
                    "default_image_id");
            user.setImageUser(defaultImage);
        }

        user.setPassword(user.getPassword());
        User userSaved = userRepository.save(user);
        UserDTO userDTO = UserMapper.convertUserEntityToUserDTO(userSaved);
        return userDTO;
    }

    @Override
    public List<UserDTO> getAllUsers() {
        List<User> users = userRepository.findAll();
        return UserMapper.convertUserEntityListToUserDTOList(users);
    }

    @Override
    public UserDTO getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new CustomException("Usuario no encontrado.", HttpStatus.NOT_FOUND));

        return UserMapper.convertUserEntityToUserDTOWithDestinations(user);
    }

    @Override
    public UserDTO getUserByEmail(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new CustomException("Usuario no encontrado con el email: " + email, HttpStatus.NOT_FOUND));

        return UserMapper.convertUserEntityToUserDTOWithDestinations(user);
    }

    @Override
    public void deleteUserById(Long id) throws IOException {
        User userFound = userRepository.findById(id)
                .orElseThrow(() -> new CustomException("Usuario no encontrado.", HttpStatus.NOT_FOUND));
        userRepository.deleteById(userFound.getId());
        imageService.deleteImageUser(userFound.getImageUser());
    }

    @Override
    public UserDTO updateUser(Long idUser, MultipartFile image, String name, String lastname, String email, String password) throws IOException {
        User userFound = userRepository.findById(idUser).orElseThrow(() -> new CustomException("Usuario no encontrado.", HttpStatus.NOT_FOUND));

        if (image != null) {
            ImageUser imageFound = userFound.getImageUser();
            imageService.deleteImageUser(imageFound);
        }

        ImageUser newImageUser = imageService.uploadImageUser(image);
        userFound.setImageUser(newImageUser);

        if (name != null && !name.isBlank())
            userFound.setName(name);

        if (name != null && !name.isBlank())
            userFound.setLastname(lastname);

        if (email != null && !email.isBlank())
            userFound.setEmail(email);

        if (password != null && !password.isBlank())
            userFound.setPassword(password);

        User userSaved = userRepository.save(userFound);
        UserDTO userDTO = UserMapper.convertUserEntityToUserDTO(userSaved);
        return userDTO;
    }
}
