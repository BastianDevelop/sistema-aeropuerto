package com.sistema.aeropuerto.repositorios;

import com.sistema.aeropuerto.entidades.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

  public Usuario findByUsername(String username);






}
