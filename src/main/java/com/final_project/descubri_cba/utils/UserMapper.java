package com.final_project.descubri_cba.utils;

import com.final_project.descubri_cba.dto.DestinationDTO;
import com.final_project.descubri_cba.dto.ImageDTO;
import com.final_project.descubri_cba.dto.UserDTO;
import com.final_project.descubri_cba.model.Destination;
import com.final_project.descubri_cba.model.User;

import java.security.SecureRandom;
import java.util.List;

public class UserMapper {

    private static final String ALPHANUMERIC_STRING = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static final SecureRandom secureRandom = new SecureRandom();

    public static String generateRandomConfirmationCode(int length) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < length; i++) {
            int randomIndex = secureRandom.nextInt(ALPHANUMERIC_STRING.length());
            stringBuilder.append(ALPHANUMERIC_STRING.charAt(randomIndex));
        }
        return stringBuilder.toString();
    }


    // --- MÉTODO BASE ---
    private static UserDTO convertBase(User user, List<Destination> destinations) {
        if (user == null) return null;

        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setName(user.getName());
        userDTO.setLastname(user.getLastname());
        userDTO.setRole(user.getRole());
        userDTO.setEmail(user.getEmail());

        userDTO.setImageUser(
                user.getImageUser() != null
                        ? ImageMapper.convertEntityImageToImageDTO(user.getImageUser(), ImageDTO.class)
                        : null
        );

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

    // --- VARIANTES ---
    public static UserDTO toDTO(User user) {
        return convertBase(user, null);
    }

    public static UserDTO toDTOWithDestinations(User user) {
        return convertBase(user, user.getDestinations());
    }

    public static UserDTO toDTOWithToken(User user, String token, String expirationTime) {
        UserDTO dto = convertBase(user, user.getDestinations());
        dto.setToken(token);
        dto.setTokenExpirationTime(expirationTime);
        return dto;
    }

    public static List<UserDTO> toDTOList(List<User> users) {
        return users.stream()
                .map(UserMapper::toDTOWithDestinations)
                .toList();
    }

    public static User toEntity(UserDTO dto) {
        if (dto == null) return null;
        User user = new User();
        user.setId(dto.getId());
        user.setName(dto.getName());
        user.setLastname(dto.getLastname());
        user.setEmail(dto.getEmail());
        user.setRole(dto.getRole());
        return user;
    }
}
