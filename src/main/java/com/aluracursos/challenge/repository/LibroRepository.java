package com.aluracursos.challenge.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.aluracursos.challenge.model.Libro;

import java.util.List;

public interface LibroRepository extends JpaRepository<Libro,Long> {
	
	Libro findByTituloContainsIgnoreCase(String nombreLibro);

	@Query("SELECT l FROM Libro l WHERE l.idioma ILIKE %:idioma%")
    List <Libro> findByIdiomaContainsIgnoreCase(@Param("idioma") String idioma);}
