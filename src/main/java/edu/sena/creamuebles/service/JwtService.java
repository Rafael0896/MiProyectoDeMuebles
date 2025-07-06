package edu.sena.creamuebles.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {

    // Inyecta la clave secreta desde application.properties
    @Value("${jwt.secret}")
    private String SECRET_KEY;

    /**
     * Extrae el nombre de usuario (en nuestro caso, el email) del token JWT.
     * @param token El token JWT.
     * @return El email del usuario.
     */
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    /**
     * Genera un token JWT para un usuario.
     * @param userDetails Los detalles del usuario obtenidos de Spring Security.
     * @return El token JWT como una cadena.
     */
    public String generateToken(UserDetails userDetails) {
        return generateToken(new HashMap<>(), userDetails);
    }

    /**
     * Genera un token JWT con claims (información extra) adicionales.
     * @param extraClaims Un mapa con la información adicional a incluir en el token.
     * @param userDetails Los detalles del usuario.
     * @return El token JWT.
     */
    public String generateToken(Map<String, Object> extraClaims, UserDetails userDetails) {
        return Jwts.builder()
                .setClaims(extraClaims)
                .setSubject(userDetails.getUsername()) // El "subject" del token será el email del usuario
                .setIssuedAt(new Date(System.currentTimeMillis())) // Fecha de creación
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24)) // Válido por 24 horas
                .signWith(getSignInKey(), SignatureAlgorithm.HS256) // Firma el token
                .compact();
    }

    /**
     * Valida si un token es correcto y pertenece al usuario.
     * @param token El token JWT.
     * @param userDetails Los detalles del usuario.
     * @return true si el token es válido, false en caso contrario.
     */
    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        // Comprueba que el usuario del token sea el mismo y que el token no haya expirado
        return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    /**
     * Método genérico para extraer cualquier "claim" (información) del token.
     */
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    /**
     * Parsea el token y extrae todos los claims usando la clave secreta.
     */
    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    /**
     * Decodifica la clave secreta (que está en Base64) y la prepara para ser usada en la firma.
     */
    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}