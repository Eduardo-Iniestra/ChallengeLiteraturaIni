package com.aluracursos.challenge.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.aluracursos.challenge.model.Autor;

public interface AutorRepository extends JpaRepository<Autor, Long> {
	Autor findByNombreIgnoreCase(String nombre);

	@Query(value = "SELECT * FROM Autor a WHERE a.fecha_nacimiento <= :fecha AND (a.fecha_muerte IS NULL OR a.fecha_muerte >= :fecha)", nativeQuery = true)
	List<Autor> findAutoresVivosEnFecha(@Param("fecha") int fecha);
}

