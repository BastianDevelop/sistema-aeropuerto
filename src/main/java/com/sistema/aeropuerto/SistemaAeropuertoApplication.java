package com.sistema.aeropuerto;

import com.sistema.aeropuerto.entidades.Rol;
import com.sistema.aeropuerto.entidades.Usuario;
import com.sistema.aeropuerto.entidades.UsuarioRol;
import com.sistema.aeropuerto.excepciones.UsuarioFoundException;
import com.sistema.aeropuerto.servicios.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.HashSet;
import java.util.Set;

@SpringBootApplication
public class SistemaAeropuertoApplication implements CommandLineRunner {

	@Autowired
	private UsuarioService usuarioService;

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	public static void main(String[] args) {
		SpringApplication.run(SistemaAeropuertoApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		try{
			Usuario usuario = new Usuario();
			usuario.setNombre("Bastriany");
			usuario.setApellido("Aldana");
			usuario.setUsername("Admon");
			//usuario.setPassword(bCryptPasswordEncoder.encode("Pass123"));
			usuario.setPassword("Pass123"); // Contraseña en texto plano
			usuario.setEmail("bastri@gmail.com");
			usuario.setTelefono("601-6855020");
			usuario.setPerfil("foto.png");
			usuario.setEnabled(true);

			Rol rol = new Rol();
			rol.setRolId(1L);
			rol.setRolNombre("ADMIN");

			Set<UsuarioRol> usuarioRoles = new HashSet<>();
			UsuarioRol usuarioRol = new UsuarioRol();
			usuarioRol.setRol(rol);
			usuarioRol.setUsuario(usuario);
			usuarioRoles.add(usuarioRol);

			Usuario usuarioGuardado = usuarioService.guardarUsuario(usuario, usuarioRoles);
			System.out.println(usuarioGuardado.getUsername());
		}catch (UsuarioFoundException exception) {
			exception.printStackTrace();
			System.out.println("DEBUG: El usuario 'Admon' ya existe. Saltando creación.");
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("DEBUG: Error inesperado al intentar crear el usuario: " + e.getMessage());
		}
	}
}
