package com.final_project.descubri_cba.exception;

import com.final_project.descubri_cba.dto.ErrorMessageDTO;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {
    // Manejo de errores de validación como campos vacios
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        Map<String, Object> errors = new HashMap<>();

        ex.getBindingResult().getFieldErrors().forEach(error -> {
            errors.put(error.getField(), error.getDefaultMessage());
        });

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
    }

    // Manejo de errores personalizados como el not found
    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ErrorMessageDTO> handleException(CustomException exception) {
        ErrorMessageDTO message = new ErrorMessageDTO(exception.getStatus(), exception.getMessage());
        return ResponseEntity.status(exception.getStatus()).body(message);
    }

    // Errores de base de datos (caída de conexión, constraint violation, etc.)
    @ExceptionHandler(org.springframework.dao.DataAccessException.class)
    public ResponseEntity<ErrorMessageDTO> handleDatabaseException(DataAccessException ex) {
        ErrorMessageDTO message = new ErrorMessageDTO(HttpStatus.INTERNAL_SERVER_ERROR,
                "Error en la base de datos. Intente nuevamente más tarde.");
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(message);
    }

    // Excepciones no controladas (NullPointer, IllegalArgument, etc.)
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorMessageDTO> handleGeneralException(Exception ex) {
        ErrorMessageDTO message = new ErrorMessageDTO(HttpStatus.INTERNAL_SERVER_ERROR,
                "Ha ocurrido un error inesperado. Intente nuevamente más tarde.");
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(message);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ErrorMessageDTO> handleBadCredentials(BadCredentialsException ex) {
        ErrorMessageDTO message = new ErrorMessageDTO(HttpStatus.UNAUTHORIZED,
                "Email o contraseña incorrectos. Por favor, verifique sus credenciales.");
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(message);
    }

    // Usuario no autenticado (no token o token inválido)
    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ErrorMessageDTO> handleAuthenticationException(AuthenticationException ex) {
        ErrorMessageDTO message = new ErrorMessageDTO(
                HttpStatus.UNAUTHORIZED,
                "Debe autenticarse para acceder a este recurso."
        );
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(message);
    }

    // Usuario autenticado pero sin permisos suficientes
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErrorMessageDTO> handleAccessDeniedException(AccessDeniedException ex) {
        ErrorMessageDTO message = new ErrorMessageDTO(
                HttpStatus.FORBIDDEN,
                "No tiene permisos para acceder a este recurso."
        );
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(message);
    }
}
