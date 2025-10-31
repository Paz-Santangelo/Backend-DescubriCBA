package com.final_project.descubri_cba.controller;

import com.final_project.descubri_cba.dto.UserDTO;
import com.final_project.descubri_cba.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * Controlador para la gestión de usuarios
 * Todos los endpoints requieren autenticación JWT
 */
@RestController
@RequestMapping("/api/usuarios")
public class UserController {
    @Autowired
    private IUserService userService;

    /**
     * Obtiene todos los usuarios del sistema
     * Requiere token JWT válido
     * @return Lista de usuarios
     */
    @GetMapping("/all")
    public ResponseEntity<?> getAllUsers() {
        List<UserDTO> users = userService.getAllUsers();
        return ResponseEntity.ok().body(users);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getUserById(@PathVariable Long id) {
        UserDTO userDto = userService.getUserById(id);
        return ResponseEntity.ok().body(userDto);
    }

    @GetMapping("/findByEmail/{email}")
    public ResponseEntity<?> getUserByEmail(@PathVariable String email) {
        UserDTO userDTO = userService.getUserByEmail(email);
        return ResponseEntity.ok().body(userDTO);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteUserById(@PathVariable Long id) throws IOException {
        userService.deleteUserById(id);
        return ResponseEntity.ok("El perfil fue eliminado con éxito.");
    }

    @PutMapping("/update/{idUser}")
    public ResponseEntity<?> updateUser(@PathVariable Long idUser,
                                        @RequestParam(value = "image", required = false) MultipartFile imageUser,
                                        @RequestParam(value = "name", required = false) String name,
                                        @RequestParam(value = "lastname", required = false) String lastname,
                                        @RequestParam(value = "email", required = true) String email,
                                        @RequestParam(value = "password", required = true) String password,
                                        @RequestParam(value = "oldPassword", required = true) String currentPassword) throws IOException {
        userService.updateUser(idUser, imageUser, name, lastname, email, password, currentPassword);
        return ResponseEntity.ok().body(
                "Usuario modificado con éxito. Deberá volver a iniciar sesión para poder continuar usando el sistema.");
    }

    @PutMapping("/{idUser}/role")
    public ResponseEntity<?> updateUserRole(@PathVariable Long idUser, @RequestBody Map<String, String> roleRequest) {
        String newRole = roleRequest.get("role");
        UserDTO updatedUser = userService.updateUserRole(idUser, newRole);
        return ResponseEntity.ok().body(updatedUser);
    }

    @GetMapping("/search")
    public ResponseEntity<List<UserDTO>> searchUsers(@RequestParam String query) {
        List<UserDTO> users = userService.findUsersByNameOrLastname(query);
        return ResponseEntity.ok(users);
    }
}
