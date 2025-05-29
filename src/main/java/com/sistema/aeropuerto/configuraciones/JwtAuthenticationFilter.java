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
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

  /*Aquí iría la implementación del filtro de autenticación JWT
  Este filtro interceptaría las solicitudes HTTP para verificar el token JWT
  y establecer el contexto de seguridad en función de la validez del token.

  Ejemplo de implementación:
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
  String jwt = extractJwtFromRequest(request);
  if (jwt != null && validateToken(jwt)) {
  setAuthenticationContext(jwt);
  }
  filterChain.doFilter(request, response);
  }*/


  @Autowired
  private UserDetailsServiceImpl userDetailsService;

  @Autowired
  private JwtUtils jwtUtils;

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

    String requestTokenHeader = request.getHeader("Authorization");
    String username = null;
    String jwtToken = null;

    if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")) {
      jwtToken = requestTokenHeader.substring(7);
      try {
        username = this.jwtUtils.extractUsername(jwtToken);
      } catch (ExpiredJwtException expiredJwtException) {
        System.out.println("JWT Token ha expirado");
      } catch (Exception exception) {
        exception.printStackTrace();
        System.out.println("JWT Token es inválido");
      }
    } else {
      System.out.println("JWT Token no comienza con Bearer String");
      logger.warn("JWT Token does not begin with Bearer String");
    }
    if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
      UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
      if (this.jwtUtils.validateToken(jwtToken, userDetails)) {
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        usernamePasswordAuthenticationToken.setDetails(userDetails);
        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
      } else {
        System.out.println("JWT Token no es válido");
      }
      filterChain .doFilter(request, response);
    } /*else {
      System.out.println("Username es nulo o el contexto de seguridad ya está establecido");
    }*/

  }

}
