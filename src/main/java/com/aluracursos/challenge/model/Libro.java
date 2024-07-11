package com.aluracursos.challenge.model;

import jakarta.persistence.*;

import java.util.List;
import java.util.OptionalDouble;

@Entity
@Table(name = "libro")
public class Libro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;
	@Column(unique = true)
    private String titulo;
    private String idioma;
    private Integer numeroDescargas;
    @ManyToOne
    private Autor autor;

	public Libro(){}

    public Libro(DatosLibro datosLibro, Autor autor){
        this.titulo = datosLibro.titulo();     
        this.autor = autor;
        this.idioma = datosLibro.idiomas().get(0);
        this.numeroDescargas = datosLibro.numeroDescargas();
     
    }

	public Long getId() {
		return Id;
	}

	public void setId(Long id) {
		Id = id;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}


	public Integer getNumeroDescargas() {
		return numeroDescargas;
	}

	public void setNumeroDescargas(Integer numeroDescargas) {
		this.numeroDescargas = numeroDescargas;
	}

	public String getIdioma() {
		return idioma;
	}

	public void setIdioma(String idioma) {
		this.idioma = idioma;
	}

	public Autor getAutor() {
		return autor;
	}

	public void setAutor(Autor autor) {
		this.autor = autor;
	}

   

	@Override
	public String toString() {
		var respuesta = String.format(
				
				"/-------------LIBRO--------------/\n" +
				"Titulo: %s\n"+
				"Autor: %s\n"+	
				"Idioma: %s\n"+
				"Numero de descargas: %s\n"+
				"/--------------------------------/\n",
	
		titulo, autor.getNombre(), idioma, numeroDescargas);
		
		return respuesta;
	}
   

} 
