package com.sistema.aeropuerto.repositorios;

import com.sistema.aeropuerto.entidades.Examen;
import com.sistema.aeropuerto.entidades.Pregunta;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface PreguntaRepository extends JpaRepository<Pregunta,Long> {

  Set<Pregunta> findByExamen(Examen examen);

}
