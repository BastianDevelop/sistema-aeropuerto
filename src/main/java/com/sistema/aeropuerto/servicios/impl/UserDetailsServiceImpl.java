package com.sistema.aeropuerto.servicios.impl;

import com.sistema.aeropuerto.entidades.Usuario;
import com.sistema.aeropuerto.repositorios.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;



@Service
public class UserDetailsServiceImpl implements UserDetailsService {
  @Autowired
  private UsuarioRepository usuarioRepository;
  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
      Usuario usuario = usuarioRepository.findByUsername(username);
      if (usuario == null) {
        throw new UsernameNotFoundException("Usuario no encontrado con el nombre: " + username);
      }
      return usuario;
  }
}


