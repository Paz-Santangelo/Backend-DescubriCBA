package com.final_project.descubri_cba.utils;

import com.final_project.descubri_cba.dto.DestinationDTO;
import com.final_project.descubri_cba.dto.ImageDTO;
import com.final_project.descubri_cba.dto.UserDTO;
import com.final_project.descubri_cba.model.Destination;
import com.final_project.descubri_cba.model.User;

import java.util.List;

public class UserMapper {

    // Este es el metodo base que usan los otros metodos de esta clase
    private static UserDTO convertUserEntityToUserDTO(User user, List<Destination> destinations) {
        UserDTO userDTO = new UserDTO();

        userDTO.setId(user.getId());
        userDTO.setName(user.getName());
        userDTO.setLastname(user.getLastname());
        userDTO.setRole(user.getRole());
        userDTO.setImageUser(
                user.getImageUser() != null
                        ? ImageMapper.convertEntityImageToImageDTO(user.getImageUser(), ImageDTO.class)
                        : null
        );
        userDTO.setEmail(user.getEmail());
        userDTO.setToken(null);
        userDTO.setTokenExpirationTime(null);

        if (user.getComments() != null && !user.getComments().isEmpty()) {
            userDTO.setComments(user.getComments().stream()
                    .map(CommentMapper::convertCommentEntityToCommentDTO)
                    .toList());
        }

        if (destinations != null && !destinations.isEmpty()) {
            userDTO.setOwnedDestinations(
                    DestinationMapper.genericMapListToTypedDTO(destinations, DestinationDTO.class)
            );
        }

        return userDTO;
    }

    // Este metodo retorna un userDTO sin la lista de destinos, se usa para el alta de un nuevo usuario
    public static UserDTO convertUserEntityToUserDTO(User user) {
        return convertUserEntityToUserDTO(user, null);
    }

    // Este metodo retorna un userDTO con la lista de destinos, es para traer un usuario por su id o email o para traer todos los usuarios.
    public static UserDTO convertUserEntityToUserDTOWithDestinations(User user) {
        return convertUserEntityToUserDTO(user, user.getDestinations());
    }

    public static List<UserDTO> convertUserEntityListToUserDTOList(List<User> users) {
        return users.stream().map(UserMapper::convertUserEntityToUserDTOWithDestinations).toList();
    }

    public static User toEntity(UserDTO dto) {
        if (dto == null) return null;
        User user = new User();
        user.setId(dto.getId());
        user.setName(dto.getName());
        user.setLastname(dto.getLastname());
        user.setEmail(dto.getEmail());
        user.setRole(dto.getRole()); // si `role` viene como String
        return user;
    }
}
