package com.sistema.aeropuerto.servicios.impl;

import com.sistema.aeropuerto.entidades.Examen;
import com.sistema.aeropuerto.entidades.Pregunta;
import com.sistema.aeropuerto.repositorios.PreguntaRepository;
import com.sistema.aeropuerto.servicios.PreguntaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class PreguntaServiceImpl implements PreguntaService {

  @Autowired
  private PreguntaRepository preguntaRepository;

  @Override
  public Pregunta agregarPregunta(Pregunta pregunta) {
    return preguntaRepository.save(pregunta);
  }

  @Override
  public Pregunta actualizarPregunta(Pregunta pregunta) {
    return preguntaRepository.save(pregunta);
  }

  @Override
  public Set<Pregunta> obtenerPreguntas() {
    return (Set<Pregunta>) preguntaRepository.findAll();
  }

  @Override
  public Pregunta obtenerPregunta(Long preguntaId) {
    return preguntaRepository.findById(preguntaId).get();
  }

  @Override
  public Set<Pregunta> obtenerPreguntasDelExamen(Examen examen) {
    return preguntaRepository.findByExamen(examen);
  }

  @Override
  public void eliminarPregunta(Long preguntaId) {
    Pregunta pregunta = new Pregunta();
    pregunta.setPreguntaId(preguntaId);
    preguntaRepository.delete(pregunta);
  }

  @Override
  public Pregunta listarPregunta(Long preguntaId) {
    return this.preguntaRepository.getOne(preguntaId);
  }



}
