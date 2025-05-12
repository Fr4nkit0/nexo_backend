package com.nexo.security.application.service.impl;

import java.util.Date;
import java.util.Map;
import javax.crypto.SecretKey;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.nexo.security.application.service.JwtService;

import jakarta.servlet.http.HttpServletRequest;

@Service
public class JwtServiceImpl implements JwtService {
    @Value("${security.jwt.expiration-in-minutes}")
    private Long EXPIRATION_MIN_MINUTES;
    @Value("${security.jwt.secret-key}")
    private String SECRET_KEY;

    /**
     * Genera un token JWT para el {@link UserDetails} proporcionado, incluyendo
     * opcionalmente reclamaciones (claims)
     * adicionales.
     *
     * @param userDetails Los detalles del usuario para el cual se generará el
     *                    token. El 'subject' del token será el
     *                    nombre de usuario de este objeto.
     * @param extraClaims Un mapa de reclamaciones adicionales que se incluirán en
     *                    el cuerpo (payload) del token.
     *                    Puede ser nulo si no se desean reclamaciones adicionales.
     * @return El token JWT generado como una cadena de texto.
     * @see io.jsonwebtoken.Jwts.Builder
     */

    @Override
    public String generateToken(UserDetails userDetails, Map<String, Object> extraClaims) {
        Date issuedAt = new Date(System.currentTimeMillis());
        Date expiration = new Date((EXPIRATION_MIN_MINUTES * 60 * 1000) + issuedAt.getTime());
        return Jwts.builder()
                .header()
                .type("JWT")
                .and()
                .subject(userDetails.getUsername())
                .issuedAt(issuedAt)
                .expiration(expiration)
                .claims(extraClaims)
                .signWith(generateKey(), Jwts.SIG.HS256)
                .compact();
    }

    /**
     * Extrae el nombre de usuario (el 'subject' del token) de un token JWT dado.
     *
     * @param jwt El token JWT del cual se extraerá el nombre de usuario.
     * @return El nombre de usuario extraído del token.
     * @throws io.jsonwebtoken.ExpiredJwtException         si el token ha expirado.
     * @throws io.jsonwebtoken.MalformedJwtException       si el token no tiene un
     *                                                     formato JWT válido.
     * @throws io.jsonwebtoken.security.SignatureException si la firma del token no
     *                                                     es válida.
     * @throws IllegalArgumentException                    si el argumento del token
     *                                                     es nulo o una cadena
     *                                                     vacía.
     */

    @Override
    public String extractUsername(String jwt) {
        return extractAllClaims(jwt).getSubject();
    }

    /**
     * Extrae el token JWT del encabezado "Authorization" de una petición HTTP.
     * Se espera que el encabezado tenga el formato "Bearer [token]".
     *
     * @param request La petición HTTP de la cual se intentará extraer el token.
     * @return El token JWT como una cadena de texto, o {@code null} si el
     *         encabezado no existe o no tiene el formato esperado.
     */

    @Override
    public String extractJwtFromRequest(HttpServletRequest request) {
        String authorizationHeader = request.getHeader("Authorization");
        if (!StringUtils.hasText(authorizationHeader) || !authorizationHeader.startsWith("Bearer ")) {
            return null;
        }

        return authorizationHeader.split(" ")[1];
    }

    /**
     * Extrae la fecha de expiración de un token JWT dado.
     *
     * @param jwt El token JWT del cual se extraerá la fecha de expiración.
     * @return La fecha de expiración del token.
     * @throws io.jsonwebtoken.ExpiredJwtException         si el token ha expirado.
     * @throws io.jsonwebtoken.MalformedJwtException       si el token no tiene un
     *                                                     formato JWT válido.
     * @throws io.jsonwebtoken.security.SignatureException si la firma del token no
     *                                                     es válida.
     * @throws IllegalArgumentException                    si el argumento del token
     *                                                     es nulo o una cadena
     *                                                     vacía.
     */

    @Override
    public Date extractExpiration(String jwt) {
        return extractAllClaims(jwt).getExpiration();
    }

    /**
     * Genera la clave secreta utilizada para firmar y verificar el token JWT.
     * La clave se crea a partir de la cadena {@link #SECRET_KEY} utilizando el
     * algoritmo de firma HS256.
     *
     * @return La clave secreta para la firma del token.
     */

    private SecretKey generateKey() {

        byte[] key = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(key);
    }

    /**
     * Extrae todas las reclamaciones (claims) del cuerpo (payload) de un token JWT
     * dado.
     */
    private Claims extractAllClaims(String jwt) {
        return Jwts.parser().verifyWith(generateKey()).build()
                .parseSignedClaims(jwt).getPayload();
    }

}
