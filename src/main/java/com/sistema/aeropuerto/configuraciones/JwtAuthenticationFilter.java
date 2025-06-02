package com.sistema.aeropuerto.configuraciones;

import com.sistema.aeropuerto.servicios.impl.UserDetailsServiceImpl;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource; // Importa esta clase
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

  @Autowired
  private UserDetailsServiceImpl userDetailsService;

  @Autowired
  private JwtUtils jwtUtils;

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {

    String requestURI = request.getRequestURI();

    if (requestURI.equals("/generate-token") || requestURI.equals("/usuarios/")) {
      filterChain.doFilter(request, response);
      return; // Muy importante: salimos del método para no ejecutar el resto de la lógica JWT
    }

    String requestTokenHeader = request.getHeader("Authorization");
    String username = null;
    String jwtToken = null;

    if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")) {
      jwtToken = requestTokenHeader.substring(7); // Extrae el token sin "Bearer "
      try {
        username = this.jwtUtils.extractUsername(jwtToken);
      } catch (ExpiredJwtException expiredJwtException) {
        System.out.println("JWT Token ha expirado: " + expiredJwtException.getMessage());
      } catch (Exception exception) {
        System.out.println("JWT Token es inválido: " + exception.getMessage());
        exception.printStackTrace(); // Para ver el stack trace completo
      }
    } else {
      // Este mensaje ya no debería aparecer para /generate-token o /usuarios/
      // Solo aparecerá para otras rutas que no tengan token.
      System.out.println("JWT Token no comienza con Bearer String o está ausente");
      logger.warn("JWT Token does not begin with Bearer String or is missing for URI: " + requestURI);
    }

    // Si tenemos un nombre de usuario y no hay autenticación en el contexto de seguridad
    if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
      UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);

      // Valida el token contra los detalles del usuario
      if (this.jwtUtils.validateToken(jwtToken, userDetails)) {
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
            new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        // Agrega detalles de la petición para auditoría y logging
        usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
      } else {
        System.out.println("JWT Token no es válido (validación fallida)");
      }
    }
    filterChain.doFilter(request, response);
  }
}
