package com.sistema.aeropuerto.servicios.impl;

import com.sistema.aeropuerto.entidades.Usuario;
import com.sistema.aeropuerto.entidades.UsuarioRol;
import com.sistema.aeropuerto.excepciones.UsuarioFoundException;
import com.sistema.aeropuerto.repositorios.RolRepository;
import com.sistema.aeropuerto.repositorios.UsuarioRepository;
import com.sistema.aeropuerto.servicios.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder; // ¡CAMBIO AQUÍ! Importa PasswordEncoder
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class UsuarioServiceImpl implements UsuarioService {

  @Autowired
  private UsuarioRepository usuarioRepository;

  @Autowired
  private RolRepository rolRepository;

  @Autowired
  private PasswordEncoder passwordEncoder; // ¡CAMBIO AQUÍ! Inyecta PasswordEncoder

  @Override
  public Usuario guardarUsuario(Usuario usuario, Set<UsuarioRol> usuarioRoles) throws Exception {
    Usuario usuarioLocal = usuarioRepository .findByUsername(usuario.getUsername());
    if (usuarioLocal != null) {
      System.out.println("El usuario ya existe");
      throw new UsuarioFoundException("El usuario ya esta en la DataBase");
    }else {
      for (UsuarioRol usuarioRol : usuarioRoles) {
        // Verifica si el rol ya existe antes de guardarlo
        if (!rolRepository.existsById(usuarioRol.getRol().getRolId())) {
          rolRepository.save(usuarioRol.getRol());
        }
      }
      usuario.getUsuarioRoles().addAll(usuarioRoles);
      // ¡CODIFICACIÓN DE CONTRASEÑA AQUÍ!
      usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));

      usuarioLocal = usuarioRepository.save(usuario);
    }
    return usuarioLocal;
  }

  @Override
  public Usuario obtenerUsuario(String username) {
    return usuarioRepository.findByUsername(username);
  }

  @Override
  public void eliminarUsuario(Long usuarioId) {
    usuarioRepository.deleteById(usuarioId);
  }
}
