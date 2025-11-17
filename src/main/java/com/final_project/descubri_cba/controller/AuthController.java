package com.final_project.descubri_cba.controller;

import com.final_project.descubri_cba.dto.LoginDTO;
import com.final_project.descubri_cba.dto.UserDTO;
import com.final_project.descubri_cba.model.User;
import com.final_project.descubri_cba.service.IUserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controlador para la autenticación de usuarios
 * Maneja el registro y login con JWT
 */
@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    private IUserService userService;

    /**
     * Registra un nuevo usuario en el sistema
     * @param user Datos del usuario a registrar
     * @return Mensaje de confirmación
     */
    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody User user) {
        userService.register(user);
        return ResponseEntity.ok("Usuario registrado con éxito.");
    }

    /**
     * Autentica un usuario y devuelve un token JWT
     * @param loginDto Email y password del usuario
     * @return UserDTO con token JWT incluido
     */
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginDTO loginDto) {
        UserDTO userDTO = userService.login(loginDto);
        return ResponseEntity.status(HttpStatus.OK).body(userDTO);
    }
}
