package clases;

import bbdd.BBDDLibro;
import bbdd.BaseDatosC;


/**
 * esta es la clase con los datos de los libros.
 *
 */

public class Libro {

	private String titulo;
	private String autor;
	private String editorial;
	private boolean estado_alquilado;
	private int id_libro;
	
	public Libro(String titulo, String autor, String editorial, boolean estado_alquilado, int id_libro) {
		
		this.titulo = titulo;
		this.autor = autor;
		this.editorial = editorial;
		this.estado_alquilado = estado_alquilado;
		this.id_libro = id_libro;
	}


/**
 * metodo para comprobar libro
 * @return libroValidado se devuelve el nombre del libro
 * @param titulo se pasan el titulo del libro
 */
	public static String comprobarLibro(String titulo){
		Libro lib;
		BaseDatosC mibase = new BaseDatosC("mysql-properties.xml");
		lib = new Libro(titulo);
		mibase.abrir();
		String libroValidado = BBDDLibro.comprobarLibro(lib, mibase.getConexion());
		libroValidado=libroValidado.toUpperCase();
		mibase.cerrar();
		return libroValidado;
	}
	
	
	/**
	 * buscar el id del libro para a�adirlo a la tabla de reservas
	 * @param titulo se pasa el titulo del libro para buscar el id
	 * @return idLibro devuelve el id del libro
	 */
	public static int buscarIdLibro( String titulo){
		Libro lib;
		BaseDatosC mibase = new BaseDatosC("mysql-properties.xml");
		lib=new Libro(titulo);
		mibase.abrir();
		int idLibro =BBDDLibro.buscarIdLibro(lib, mibase.getConexion()); 
		mibase.cerrar();
		return idLibro;
	}
	
	public Libro(String titulo) {
		this.titulo = titulo;
	}
	

	public Libro(int id_libro) {
		this.id_libro = id_libro;
	}


	public String getTitulo() {
		return titulo;
	}

	public String getAutor() {
		return autor;
	}

	public String getEditorial() {
		return editorial;
	}

	public boolean isEstado_alquilado() {
		return estado_alquilado;
	}

	public int getId_libro() {
		return id_libro;
	}

	@Override
	public String toString() {
		return "titulo= " + titulo + "\n\t autor= " + autor + "\n\t editorial= " + editorial + "\n\t estado_alquilado= "
				+ estado_alquilado + "\n\t id_libro= " + id_libro + "\n\n";
	}

	
}
