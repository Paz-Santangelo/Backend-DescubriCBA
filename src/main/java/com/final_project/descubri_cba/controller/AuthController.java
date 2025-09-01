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

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private IUserService userService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody User user) {
        userService.register(user);
        return ResponseEntity.ok("Usuario registrado con éxito.");
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginDTO loginDto) {
        UserDTO userDTO = userService.login(loginDto);
        return ResponseEntity.status(HttpStatus.OK).body(userDTO);
    }
}
