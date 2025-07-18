
package com.sistema.aeropuerto.controladores;

import com.sistema.aeropuerto.entidades.Rol;
import com.sistema.aeropuerto.entidades.Usuario;
import com.sistema.aeropuerto.entidades.UsuarioRol;
import com.sistema.aeropuerto.servicios.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.Set;

@RestController
@RequestMapping("/usuarios")
@CrossOrigin("*")
public class UsuarioController {

  @Autowired
  private UsuarioService usuarioService;

  // 1. Elimina el BCryptPasswordEncoder del controlador
  // Ya no es necesario aquí porque el servicio manejará la codificación

  @PostMapping("/")
  public Usuario guardarUsuario(@RequestBody Usuario usuario) throws Exception{
    usuario.setPerfil("default.png");

    // 2. ¡ELIMINA ESTA LÍNEA! (La codificación se hará en el servicio)
    // usuario.setPassword(this.bCryptPasswordEncoder.encode(usuario.getPassword()));

    Set<UsuarioRol> usuarioRoles = new HashSet<>();

    Rol rol = new Rol();
    rol.setRolId(2L);
    rol.setRolNombre("NORMAL");

    UsuarioRol usuarioRol = new UsuarioRol();
    usuarioRol.setUsuario(usuario);
    usuarioRol.setRol(rol);

    usuarioRoles.add(usuarioRol);
    return usuarioService.guardarUsuario(usuario, usuarioRoles);
  }

  @GetMapping("/{username}")
  public Usuario obtenerUsuario(@PathVariable("username") String username){
    return usuarioService.obtenerUsuario(username);
  }

  @DeleteMapping("/{usuarioId}")
  public void eliminarUsuario(@PathVariable("usuarioId") Long usuarioId) {
    usuarioService.eliminarUsuario(usuarioId);
  }
}




/*
package com.sistema.aeropuerto.controladores;

import com.sistema.aeropuerto.entidades.Rol;
import com.sistema.aeropuerto.entidades.Usuario;
import com.sistema.aeropuerto.entidades.UsuarioRol;
import com.sistema.aeropuerto.servicios.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.Set;

@RestController
@RequestMapping("/usuarios")
@CrossOrigin("*")
public class UsuarioController {

  @Autowired
  private UsuarioService usuarioService;

  @Autowired
  private BCryptPasswordEncoder bCryptPasswordEncoder;

  @PostMapping("/")
  public Usuario guardarUsuario(@RequestBody Usuario usuario) throws Exception{
    usuario.setPerfil("default.png");

    // Encriptar la contraseña antes de guardarla
    usuario.setPassword(this.bCryptPasswordEncoder.encode(usuario.getPassword()));

    Set<UsuarioRol> usuarioRoles = new HashSet<>();

    Rol rol = new Rol();
    rol.setRolId(2L);
    rol.setRolNombre("NORMAL");

    UsuarioRol usuarioRol = new UsuarioRol();
    usuarioRol.setUsuario(usuario);
    usuarioRol.setRol(rol);

    usuarioRoles.add(usuarioRol);
    return usuarioService.guardarUsuario(usuario, usuarioRoles);
  }

  @GetMapping("/{username}")
  public Usuario obtenerUsuario(@PathVariable("username") String username){
    return usuarioService.obtenerUsuario(username);
  }

  @DeleteMapping("/{usuarioId}")
  public void eliminarUsuario(@PathVariable("usuarioId") Long usuarioId) {
    usuarioService.eliminarUsuario(usuarioId);
  }
}
*/