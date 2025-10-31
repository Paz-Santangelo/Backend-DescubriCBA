package com.final_project.descubri_cba.service;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.final_project.descubri_cba.dto.LoginDTO;
import com.final_project.descubri_cba.dto.RoleDTO;
import com.final_project.descubri_cba.dto.UserDTO;
import com.final_project.descubri_cba.exception.CustomException;
import com.final_project.descubri_cba.model.ImageUser;
import com.final_project.descubri_cba.model.User;
import com.final_project.descubri_cba.repository.IUserRepository;
import com.final_project.descubri_cba.security.JWTUtils;
import com.final_project.descubri_cba.utils.UserMapper;

@Service

public class UserService implements IUserService {

    @Autowired
    private IUserRepository userRepository;

    @Autowired
    private IImageService imageService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JWTUtils jwtUtils;

    @Autowired
    private AuthenticationManager authenticationManager;

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

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        User userSaved = userRepository.save(user);
        UserDTO userDTO = UserMapper.toDTO(userSaved);
        return userDTO;
    }

    @Override
    public UserDTO login(LoginDTO loginDto) {
        authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(loginDto.getEmail(), loginDto.getPassword()));
        User user = userRepository.findByEmail(loginDto.getEmail())
                .orElseThrow(() -> new CustomException("Usuario no encontrado. Verifica el email ingresado.",
                        HttpStatus.NOT_FOUND));

        String token = jwtUtils.generateToken(user);

        return UserMapper.toDTOWithToken(user, token, "7 Days");
    }

    @Override
    public List<UserDTO> getAllUsers() {
        List<User> users = userRepository.findAll();
        return UserMapper.toDTOList(users);
    }

    @Override
    public UserDTO getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new CustomException("Usuario no encontrado.", HttpStatus.NOT_FOUND));

        return UserMapper.toDTOWithDestinations(user);
    }

    @Override
    public UserDTO getUserByEmail(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new CustomException("Usuario no encontrado con el email: " + email,
                        HttpStatus.NOT_FOUND));

        return UserMapper.toDTOWithDestinations(user);
    }

    @Override
    public void deleteUserById(Long id) throws IOException {
        User userFound = userRepository.findById(id)
                .orElseThrow(() -> new CustomException("Usuario no encontrado.", HttpStatus.NOT_FOUND));
        userRepository.deleteById(userFound.getId());
        imageService.deleteImageUser(userFound.getImageUser());
    }

    @Override
    public UserDTO updateUser(Long idUser, MultipartFile image, String name, String lastname, String email,
            String password, String currentPassword) throws IOException {
        User userFound = userRepository.findById(idUser)
                .orElseThrow(() -> new CustomException("Usuario no encontrado.", HttpStatus.NOT_FOUND));

        // 1. Validar la contraseña actual
        if (!passwordEncoder.matches(currentPassword, userFound.getPassword())) {
            throw new CustomException("La contraseña actual es incorrecta.", HttpStatus.UNAUTHORIZED);
        }

        // 2. Actualizar la imagen si se proporciona una nueva
        if (image != null && !image.isEmpty()) {
            ImageUser oldImage = userFound.getImageUser();
            // Evitar eliminar la imagen por defecto de Cloudinary
            if (oldImage != null && !"default_image_id".equals(oldImage.getImageId())) {
                imageService.deleteImageUser(oldImage);
            }
            ImageUser newImageUser = imageService.uploadImageUser(image);
            userFound.setImageUser(newImageUser);
        }

        // 3. Actualizar los datos del usuario
        if (name != null && !name.isBlank())
            userFound.setName(name);

        if (lastname != null && !lastname.isBlank())
            userFound.setLastname(lastname);

        if (email != null && !email.isBlank())
            userFound.setEmail(email);

        if (password != null && !password.isBlank())
            userFound.setPassword(passwordEncoder.encode(password));

        User userSaved = userRepository.save(userFound);
        return UserMapper.toDTO(userSaved);
    }

    @Override
    public UserDTO updateUserRole(Long idUser, String newRole) {
        User user = userRepository.findById(idUser)
                .orElseThrow(
                        () -> new CustomException("Usuario no encontrado con ID: " + idUser, HttpStatus.NOT_FOUND));

        if (!RoleDTO.isValidRole(newRole)) {
            throw new CustomException("Rol inválido: " + newRole + ". Roles válidos: " + RoleDTO.getValidRoles(),
                    HttpStatus.BAD_REQUEST);
        }

        user.setRole(newRole);
        User updatedUser = userRepository.save(user);

        return UserMapper.toDTO(updatedUser);
    }

    @Override
    public List<UserDTO> findUsersByNameOrLastname(String searchTerm) {
        List<User> users = userRepository.findByNameOrLastnameIgnoreCase(searchTerm);
        return UserMapper.toDTOList(users);
    }
}
