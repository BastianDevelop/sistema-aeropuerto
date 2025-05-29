
package com.sistema.aeropuerto.configuraciones;

import com.sistema.aeropuerto.servicios.impl.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider; // Nueva importación
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration; // Nueva importación
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity; // ¡Cambio aquí!
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy; // Nueva importación
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder; // ¡Cambio recomendado!
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain; // Nueva importación
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter; // Nueva importación

@EnableWebSecurity
@Configuration
// @EnableGlobalMethodSecurity(prePostEnabled = true) // Deprecated
@EnableMethodSecurity(prePostEnabled = true) // ¡Nueva anotación!
public class MySecurityConfig { // Ya no extiende WebSecurityConfigurerAdapter

  @Autowired
  private JwtAuthenticationEntryPoint unauthorizedHandler;

  @Autowired
  private UserDetailsServiceImpl userDetailsService;

  @Autowired
  private JwtAuthenticationFilter jwtAuthenticationFilter;

  // 1. PasswordEncoder: ¡Cambio recomendado a BCrypt!
  @Bean
  public PasswordEncoder passwordEncoder() {
    // NoOpPasswordEncoder es solo para desarrollo y NO DEBE USARSE EN PRODUCCIÓN.
    // Se recomienda encarecidamente usar BCryptPasswordEncoder.
    return new BCryptPasswordEncoder();
  }

  // 2. AuthenticationProvider: Cómo autenticar usuarios (con UserDetailsService y PasswordEncoder)
  @Bean
  public DaoAuthenticationProvider authenticationProvider() {
    DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
    authProvider.setUserDetailsService(userDetailsService);
    authProvider.setPasswordEncoder(passwordEncoder()); // Usa el PasswordEncoder que definiste
    return authProvider;
  }

  // 3. AuthenticationManager: Gestiona el proceso de autenticación
  @Bean
  public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
    return authenticationConfiguration.getAuthenticationManager();
  }

  // 4. SecurityFilterChain: Aquí se configura la seguridad de las peticiones HTTP
  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http
      .csrf(csrf -> csrf.disable()) // Deshabilita CSRF si vas a usar tokens JWT ( stateless )
      .cors(cors -> {}) // Habilita CORS (si tienes configurado un CorsConfigurationSource bean)
      .authorizeHttpRequests(authorize -> authorize
        // Rutas que no requieren autenticación (públicas)
        .requestMatchers("/generate-token", "/usuarios/").permitAll()
        .requestMatchers("/api/**").permitAll() // Ejemplo de ruta pública
        .requestMatchers("/images/**").permitAll() // Si accedes a imágenes directamente
        .requestMatchers("/css/**").permitAll() // Si accedes a CSS directamente
        .requestMatchers("/js/**").permitAll() // Si accedes a JS directamente
        // Todas las demás rutas requieren autenticación
        .anyRequest().authenticated()
        )
        .exceptionHandling(exception -> exception
            .authenticationEntryPoint(unauthorizedHandler) // Maneja errores de autenticación
        )
        .sessionManagement(session -> session
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS) // JWT es stateless
        );

    // Agrega el filtro JWT antes del filtro de autenticación de usuario/contraseña
    http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

    return http.build();
  }
}





/*
package com.sistema.aeropuerto.configuraciones;

import com.sistema.aeropuerto.servicios.impl.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableWebSecurity
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class MySecurityConfig extends WebSecurityConfigurerAdapter {

  @Autowired
  private JwtAuthenticationEntryPoint unauthorizedHandler;

  @Autowired
  private UserDetailsServiceImpl userDetailsService;

  @Autowired
  private JwtAuthenticationFilter jwtAuthenticationFilter;

  @Override
  @Bean
  public AuthenticationManager authenticationManagerBean() throws Exception {
      return super.authenticationManagerBean();
  }

  public PasswordEncoder passwordEncoder() {
      return NoOpPasswordEncoder.getInstance();
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception{
    super .configure(http);
  }
}
*/