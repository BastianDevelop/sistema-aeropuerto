package com.sistema.aeropuerto;

import com.sistema.aeropuerto.entidades.Rol;
import com.sistema.aeropuerto.entidades.Usuario;
import com.sistema.aeropuerto.entidades.UsuarioRol;
import com.sistema.aeropuerto.servicios.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.HashSet;
import java.util.Set;

@SpringBootApplication
public class SistemaAeropuertoApplication implements CommandLineRunner {

	@Autowired
	private UsuarioService usuarioService;

	public static void main(String[] args) {
		SpringApplication.run(SistemaAeropuertoApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		/*
		Usuario usuario = new Usuario();
		usuario.setNombre("Christian");
		usuario.setApellido("Ramirez");
		usuario.setUsername("christian");
		usuario.setPassword("12345");
		usuario.setEmail("c1@gmail.com");
		usuario.setTelefono("1234567890");
		usuario.setPerfil("foto.png");

		Rol rol = new Rol();
		rol.setRolId(1L);
		rol.setNombre("ADMIN");

		Set<UsuarioRol> usuarioRoles = new HashSet<>();
		UsuarioRol usuarioRol = new UsuarioRol();
		usuarioRol.setRol(rol);
		usuarioRol.setUsuario(usuario);
		usuarioRoles.add(usuarioRol);

		Usuario usuarioGuardado = usuarioService.guardarUsuario(usuario, usuarioRoles);
		System.out.println(usuarioGuardado.getUsername());
		*/

	}




}
