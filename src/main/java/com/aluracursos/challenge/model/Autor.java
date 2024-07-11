package com.aluracursos.challenge.model;

import java.util.List;

import jakarta.persistence.*;

@Entity
@Table(name = "autor")
public class Autor {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;
	@Column(unique = true)
	private String nombre;
	private Integer fechaNacimiento;
	private Integer fechaMuerte;
    @OneToMany(mappedBy = "autor", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Libro> libros;
	
	public Autor() {
		
	}
	public Autor(DatosAutor a) {
		this.nombre = a.nombre();
		this.fechaNacimiento = a.fechaNacimiento();
		this.fechaMuerte = a.fechaMuerte();
	}
	
	
	
	public List<Libro> getLibros() {
		return libros;
	}
	public void setLibros(List<Libro> libros) {
		this.libros = libros;
	}
	public Long getId() {
		return Id;
	}
	public void setId(Long id) {
		Id = id;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	public Integer getFechaNacimiento() {
		return fechaNacimiento;
	}
	public void setFechaNacimiento(Integer fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}
	public Integer getFechaMuerte() {
		return fechaMuerte;
	}
	public void setFechaMuerte(Integer fechaMuerte) {
		this.fechaMuerte = fechaMuerte;
	}
	@Override
	public String toString() {
		var respuesta = String.format(
				
				"/-------------AUTOR--------------/\n" +
				"Nombre: %s\n"+
				"Fecha de nacimiento: %s\n"+	
				"fecha de muerte: %s\n"+
				"/--------------------------------/\n",
	
				nombre, fechaNacimiento, fechaMuerte);
		
		return respuesta;
		
	}
	
	
	
	

}
