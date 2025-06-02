package com.sistema.aeropuerto.entidades;

import jakarta.persistence.*;

@Entity
public class UsuarioRol {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long UsuarioRolId;

  @ManyToOne(fetch = FetchType.EAGER)
  private Usuario usuario;

  @ManyToOne
  private Rol rol;


  public Long getUsuarioRolId() {
    return UsuarioRolId;
  }

  public void setUsuarioRolId(Long usuarioRolId) {
    UsuarioRolId = usuarioRolId;
  }

  public Usuario getUsuario() {
    return usuario;
  }

  public void setUsuario(Usuario usuario) {
    this.usuario = usuario;
  }

  public Rol getRol() {
    return rol;
  }

  public void setRol(Rol rol) {
    this.rol = rol;
  }

  public UsuarioRol(){
  }

}
