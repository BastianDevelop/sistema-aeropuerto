package com.sistema.aeropuerto.entidades;

import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "roles")



public class Rol {

  @Id
  private Long rolId;
  private String rolNombre;

  @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY,mappedBy = "rol")
  private Set<UsuarioRol> usuarioRoles = new HashSet<>();

  public Rol(){

  }

  public Rol(Long rolId, String rolNombre) {
    this.rolId = rolId;
    this.rolNombre = rolNombre;
  }

  public Long getRolId() {
    return rolId;
  }

  public void setRolId(Long rolId) {
    this.rolId = rolId;
  }

  public String getRolNombre() {
    return rolNombre;
  }

  public void setRolNombre(String rolNombre) {
    this.rolNombre = rolNombre;
  }

  public Set<UsuarioRol> getUsuarioRoles() {
    return usuarioRoles;
  }

  public void setUsuarioRoles(Set<UsuarioRol> usuarioRoles) {
    this.usuarioRoles = usuarioRoles;
  }
}



/*
public class Rol {

  @Id
  private Long rolId;
  private String rolNombre;

  @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "rol")
  private Set<UsuarioRol> usuarioRoles = new HashSet<>();

  public Long getRolId() {
    return rolId;
  }

  public void setRolId(Long rolId) {
    this.rolId = rolId;
  }

  public String getNombre() {
    return rolNombre;
  }

  public void setNombre(String nombre) {
    this.rolNombre = nombre;
  }

  public Set<UsuarioRol> getUsuarioRoles() {
    return usuarioRoles;
  }

  public void setUsuarioRoles(Set<UsuarioRol> usuarioRoles) {
    this.usuarioRoles = usuarioRoles;
  }

  public Rol(){
  }
}
*/