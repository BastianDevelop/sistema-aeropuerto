package com.sistema.aeropuerto.servicios;

import com.sistema.aeropuerto.entidades.Usuario;
import com.sistema.aeropuerto.entidades.UsuarioRol;

import java.util.Set;

public interface UsuarioService {


  public Usuario guardarUsuario(Usuario usuario, Set<UsuarioRol> usuarioRoles) throws Exception;

  public Usuario obtenerUsuario(String username);
  public void eliminarUsuario(Long usuarioId);



}
