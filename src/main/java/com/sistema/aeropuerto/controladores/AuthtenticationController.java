package com.sistema.aeropuerto.controladores;

import com.sistema.aeropuerto.configuraciones.JwtUtils;
import com.sistema.aeropuerto.entidades.JwtRequest;
import com.sistema.aeropuerto.entidades.JwtResponse;
import com.sistema.aeropuerto.entidades.Usuario;
import com.sistema.aeropuerto.excepciones.UsuarioFoundException;
import com.sistema.aeropuerto.servicios.impl.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@CrossOrigin("*")
public class AuthtenticationController {

  @Autowired
  private AuthenticationManager authenticationManager;

  @Autowired
  private UserDetailsServiceImpl userDetailsService;

  @Autowired
  private JwtUtils jwtUtils;

  @PostMapping("/generate-token")
  public ResponseEntity<?> generateToken(@RequestBody JwtRequest jwtRequest) throws Exception{
    try{
      autenticar(jwtRequest.getUsername(), jwtRequest.getPassword());
    }catch (UsuarioFoundException exception){
        exception.printStackTrace();
        throw new Exception("Usuario no encontrado " + exception.getMessage());
    }
    UserDetails userDetails = this.userDetailsService.loadUserByUsername(jwtRequest.getUsername());
    String token = this.jwtUtils.generateToken(userDetails);
    return ResponseEntity.ok(new JwtResponse(token));
  }

  private void autenticar(String username, String password) throws Exception {
    try{
      authenticationManager.authenticate(
          new UsernamePasswordAuthenticationToken(username, password)
      );

    }catch (DisabledException diasbledException){
      throw new Exception( "Usuario deshabilitado " + diasbledException.getMessage());
    }catch (BadCredentialsException badCredentialsException){
      throw new Exception("Credenciales inválidas " + badCredentialsException.getMessage());
    }
  }

  @GetMapping("/actual-usuario")
  public Usuario obtenerUsuarioActual(Principal principal) {
    return (Usuario) this.userDetailsService.loadUserByUsername(principal.getName());
  }
}
