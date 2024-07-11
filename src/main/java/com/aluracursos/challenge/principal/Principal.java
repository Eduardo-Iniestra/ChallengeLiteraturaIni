package com.aluracursos.challenge.principal;

import java.util.*;
import java.util.stream.Collectors;

import com.aluracursos.challenge.model.*;
import com.aluracursos.challenge.repository.AutorRepository;
import com.aluracursos.challenge.repository.LibroRepository;
import com.aluracursos.challenge.service.ConsumoAPI;
import com.aluracursos.challenge.service.ConvierteDatos;

public class Principal {
	private Scanner sc = new Scanner(System.in);
	private ConsumoAPI consumoApi = new ConsumoAPI();
	private final String URL_BASE = "https://gutendex.com/books/?search=";
	private ConvierteDatos conversor = new ConvierteDatos();
	private LibroRepository repositorioLibro;
	private AutorRepository repositorioAutor;
	private List<Autor> autores;
	private List<Libro> libros;

	public Principal(LibroRepository libroRepositorio, AutorRepository autorRepositorio) {
		this.repositorioLibro = libroRepositorio;
		this.repositorioAutor = autorRepositorio;
	}

	public void muestraElMenu() {
		var opcion = -1;
		while (opcion != 0) {
			var menu = """
					*************************************************
					Eliga su opción a través de su número

					      1 - Buscar libros por su título (Agregar Libro)
					      2 - Listar libros registrados
					      3 - Listar autores registrados
					      4 - Listar autores vivos en un determinado año
					      5 - Listar libros por idioma
					      0 - Salir
					   *************************************************
					      """;
			System.out.println(menu);
			opcion = sc.nextInt();
			sc.nextLine();

			switch (opcion) {
			case 1:
				buscarLibroPorTitulo();
				break;
			case 2:
				listaLibrosRegistrados();
				break;
			case 3:
				listarAutoresRegistrados();
				break;
			case 4:
				listarAutoresVivosDeterminadoAno();
				break;
			case 5:
				listarLibrosPorIdioma();
				break;
			case 0:
				System.out.println("Cerrando la aplicación...");
				break;
			default:
				System.out.println("Opción inválida");
			}
		}

	}

	private Libro agregarLibroBD(DatosLibro datosLibro, Autor autor) {
		Libro libro = new Libro(datosLibro, autor);
		return repositorioLibro.save(libro);

	}

	private DatosResultado DameDatosResultados() {
		System.out.println("Ingresa el nombre del libro que deseas buscar");
		var nombreLibro = sc.nextLine();
		var json = consumoApi.obtenerDatos(URL_BASE + nombreLibro.replace(" ", "+"));
		DatosResultado datos = conversor.obtenerDatos(json, DatosResultado.class);
		return datos;
	}

	private void buscarLibroPorTitulo() {
		DatosResultado datosResultado = DameDatosResultados();

		if (datosResultado.resultados().isEmpty()) {
			System.out.println("El libro buscado no se encuentra. Pruebe con otro.");
			return;
		}

		DatosLibro datosLibros = datosResultado.resultados().get(0);
		DatosAutor datosAutor = datosLibros.autor().get(0);

		Autor autorBuscado = repositorioAutor.findByNombreIgnoreCase(datosAutor.nombre());
		Autor autor = autorBuscado != null ? autorBuscado : repositorioAutor.save(new Autor(datosAutor));

		Libro libro = agregarLibroBD(datosLibros, autor);
		System.out.println("Libro agregado " + libro);
		
	}

	private void listaLibrosRegistrados() {
		libros = repositorioLibro.findAll();
		if (libros.isEmpty()) {
			System.out.println("No hay ningún Libro registrado.");
		} else {
			libros.stream().forEach(System.out::println);
		}
	}

	private void listarAutoresRegistrados() {
		autores = repositorioAutor.findAll();
		if (autores.isEmpty()) {
			System.out.println("No hay autores registrados");
		} else {
			autores.stream().forEach(System.out::println);
		}
	}

	private void listarAutoresVivosDeterminadoAno() {
		System.out.println("Ingresa la fecha de la cual deseas saber cuales autores seguían vivos");
		int fecha = sc.nextInt();
		try {
			autores = repositorioAutor.findAutoresVivosEnFecha(fecha);
			if (autores.isEmpty()) {
				System.out.println("No hay ningún autor en esa fecha");
			} else {
				autores.stream().forEach(System.out::println);
			}
		} catch (Exception e) {
			throw new RuntimeException();
		}
	}

	private void listarLibrosPorIdioma() {
		System.out.println("""
				
			##################################################
			
			Ingrese el idioma (es, en, fr, pt):
			
				 "es" = Español
				 "en" = Inglés
				 "fr" = Francés
				 "pt" = Portugués
				 "regresar" - Para regresar al menú principal

			Para listar los libros guardados en ese idioma
				
			##################################################
				""");

	        String idioma = sc.nextLine().toLowerCase(); 

	        switch (idioma) {
	            case "es":
	                System.out.println("Seleccionó Español");
	                break;
	            case "en":
	                System.out.println("Seleccionó Inglés");
	                break;
	            case "fr":
	                System.out.println("Seleccionó Francés");
	                break;
	            case "pt":
	                System.out.println("Seleccionó Portugués");
	                break;
	            default:
	                System.out.println("Opción incorrecta. Inténtelo nuevamente.");
	                return; 
	        }

     if (!idioma.isEmpty()) {
         var listaL = repositorioLibro.findByIdiomaContainsIgnoreCase(idioma);
         listaL.forEach(System.out::println);
		
		}
	}
}
