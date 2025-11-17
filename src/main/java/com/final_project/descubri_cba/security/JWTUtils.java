package com.final_project.descubri_cba.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Date;
import java.util.function.Function;

/**
 * Utilidad para generar y validar tokens JWT
 * Maneja la creación, validación y extracción de información de los tokens
 */
@Service
public class JWTUtils {
    // Token válido por 7 días
    private static final long EXPIRATION_TIME = 1000 * 60 * 60 * 24 * 7;

    private final SecretKey key;

    public JWTUtils() {
        // Clave secreta para firmar los tokens JWT
        String secretKey = "843567893696976453275974432697R634976R738467TR678T34865R6834R8763T478378637664538745673865783678548735687R3";

        byte[] keyBytes = Base64.getDecoder().decode(secretKey.getBytes(StandardCharsets.UTF_8));

        this.key = new SecretKeySpec(keyBytes, "HmacSHA256");
    }

    /**
     * Genera un token JWT para el usuario autenticado
     * @param userDetails Detalles del usuario
     * @return Token JWT firmado
     */
    public String generateToken(UserDetails userDetails) {
        return Jwts.builder()
                .subject(userDetails.getUsername()) // Email del usuario
                .issuedAt(new Date(System.currentTimeMillis())) // Fecha de emisión
                .expiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME)) // Fecha de expiración
                .signWith(key) // Firma con clave secreta
                .compact();
    }

    /**
     * Extrae información específica del token JWT
     * @param token Token JWT
     * @param claimsTFunction Función para extraer el claim específico
     * @return Valor del claim extraído
     */
    private <T> T extractClaims(String token, Function<Claims, T> claimsTFunction) {
        return claimsTFunction
                .apply(
                        Jwts.parser()
                                .verifyWith(key)
                                .build()
                                .parseSignedClaims(token)
                                .getPayload());
    }

    /**
     * Verifica si el token ha expirado
     * @param token Token JWT a verificar
     * @return true si el token ha expirado
     */
    public boolean isTokenExpired(String token) {
        return extractClaims(token, Claims::getExpiration).before(new Date());
    }

    /**
     * Extrae el email del usuario del token JWT
     * @param token Token JWT
     * @return Email del usuario
     */
    public String extractUsername(String token) {
        return extractClaims(token, Claims::getSubject);
    }

    /**
     * Valida si el token es válido para el usuario
     * @param token Token JWT
     * @param userDetails Detalles del usuario
     * @return true si el token es válido y no ha expirado
     */
    public boolean isValidToken(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }
}
