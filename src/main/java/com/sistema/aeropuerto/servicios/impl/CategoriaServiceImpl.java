package com.sistema.aeropuerto.servicios.impl;

import com.sistema.aeropuerto.entidades.Categoria;
import com.sistema.aeropuerto.repositorios.CategoriaRepository;
import com.sistema.aeropuerto.servicios.CategoriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedHashSet;
import java.util.Set;

@Service
public class CategoriaServiceImpl implements CategoriaService {

  @Autowired
  private CategoriaRepository categoriaRepository;

  @Override
  public Categoria agregarCategoria(Categoria categoria) {
    return categoriaRepository.save(categoria);
  }

  @Override
  public Categoria actualizarCategoria(Categoria categoria) {
    return categoriaRepository.save(categoria);
  }

  @Override
  public Set<Categoria> obtenerCategorias() {
    return new LinkedHashSet<>(categoriaRepository.findAll());
  }

  @Override
  public Categoria obtenerCategoria(Long categoriaId) {
    return categoriaRepository.findById(categoriaId).get();
  }

  @Override
  public void eliminarCategoria(Long categoriaId) {
    Categoria categoria = new Categoria();
    categoria.setCategoriaId(categoriaId);
    categoriaRepository.delete(categoria);
  }

}
