package com.sistema.aeropuerto.entidades;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "usuarios")
public class Usuario implements UserDetails{

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String username;
  private String password;
  private String nombre;
  private String apellido;
  private String email;
  private String telefono;
  private boolean enabled = true;
  private String perfil;

  @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "usuario")
  @JsonIgnore
  private Set<UsuarioRol> usuarioRoles = new HashSet<>();

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getUsername() {
    return username;
  }

  @Override
  public boolean isAccountNonExpired() {
    //return UserDetails.super.isAccountNonExpired();
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    //return UserDetails.super.isAccountNonLocked();
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    //return UserDetails.super.isCredentialsNonExpired();
    return true;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    Set<Authority> autoridades = new HashSet<>();
    this.usuarioRoles.forEach(usuarioRol -> {
      autoridades.add(new Authority(usuarioRol.getRol().getRolNombre()));
    });
    return autoridades;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getNombre() {
    return nombre;
  }

  public void setNombre(String nombre) {
    this.nombre = nombre;
  }

  public String getApellido() {
    return apellido;
  }

  public void setApellido(String apellido) {
    this.apellido = apellido;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getTelefono() {
    return telefono;
  }

  public void setTelefono(String telefono) {
    this.telefono = telefono;
  }

  public boolean isEnabled() {
    return enabled;
  }

  public void setEnabled(boolean enabled) {
    this.enabled = enabled;
  }

  public String getPerfil() {
    return perfil;
  }

  public void setPerfil(String perfil) {
    this.perfil = perfil;
  }

  public Set<UsuarioRol> getUsuarioRoles() {
    return usuarioRoles;
  }

  public void setUsuarioRoles(Set<UsuarioRol> usuarioRoles) {
    this.usuarioRoles = usuarioRoles;
  }

  public Usuario() {
  }

}
