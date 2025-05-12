package com.nexo.security.infraestructura.config.filter;

import java.io.IOException;
import java.util.Date;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.nexo.security.application.service.JwtService;
import com.nexo.security.domain.persistence.JwtToken;
import com.nexo.security.domain.repository.JwtRepository;
import com.nexo.user.application.service.UserService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtService jwtService;
    private final UserService userService;
    private final JwtRepository jwtRepository;

    public JwtAuthenticationFilter(JwtService jwtService, UserService userService, JwtRepository jwtRepository) {
        this.jwtService = jwtService;
        this.userService = userService;
        this.jwtRepository = jwtRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        // Intenta extraer el token JWT de la petición.
        String jwt = jwtService.extractJwtFromRequest(request);
        // Si no se encuentra un token JWT, permite que la petición continúe sin
        // autenticación.
        if (!StringUtils.hasText(jwt)) {
            filterChain.doFilter(request, response);
            return;
        }
        // Valida la firma y la fecha de expiración del token JWT, y verifica su estado
        // en el repositorio.
        if (!validateToken(jwt)) {
            filterChain.doFilter(request, response);
            return;
        }
        String username = jwtService.extractUsername(jwt);
        // 4. Setear objecto authentication dentro security content holder
        UserDetails userDetails = userService.findByUsername(username);
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                username, null, userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        // 5. ejecutar el resto de filtro
        filterChain.doFilter(request, response);
    }

    private boolean validateToken(String jwt) {
        boolean isValid = jwtRepository.findByToken(jwt)
                .map(jwtToken -> jwtToken.isValid() &&
                        jwtToken.getExpiration().after(new Date(System.currentTimeMillis())))
                .orElse(false);
        if (!isValid) {
            System.out.println("Token Invaido");
            updateTokenStatus(jwt);
        }
        return isValid;
    }

    private void updateTokenStatus(String jwt) {
        JwtToken jwtToken = jwtRepository.findByToken(jwt)
                .orElseThrow(() -> new RuntimeException("Token not Found"));
        jwtToken.setValid(false);
        jwtRepository.save(jwtToken);
    }

}
