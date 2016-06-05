package bbdd;

import java.sql.*;

import clases.Libro;
import clases.*;

import java.util.*;

public class BBDDLibro {
	private static Statement s;
	private static Connection c;
	private static ResultSet reg;
	/**
	 * método para añadir libros en la biblioteca
	 * @param c se pasa la conexion
	 * @param lib objeto libro
	 */
	public static void anadir(Libro lib, Connection c){
		String cadena="INSERT INTO libros VALUES('" + lib.getTitulo() + "','" + lib.getAutor()+"','" + lib.getEditorial()+"','" + lib.isEstado_alquilado()+"','"+ lib.getId_libro() +"')";  

		try{
			s=c.createStatement();
			s.executeUpdate(cadena);
			s.close();
		}
		catch ( SQLException e){
			System.out.println(e.getMessage());
		}
	}
	/**
	 * método para alquilar un libro poniendo el estado del libro a true
	 * @param c se pasa la conexion
	 * @param lib objeto libro
	 */
	public static void actualizarEstadoLibroTrue(Libro lib, Connection c){
		String cadena="UPDATE libros SET estado_Alquilado=1 WHERE titulo='"+lib.getTitulo()+"'";    

		try{
			s=c.createStatement();
			s.executeUpdate(cadena);
			s.close();
		}
		catch ( SQLException e){
			System.out.println(e.getMessage());
		}
	}
	/**
	 * método para devolver un libro alquilado poniendo el estado del libro a false
	 * @param c se pasa la conexion
	 * @param lib objeto libro
	 */
	public static void actualizarEstadoLibroFalse(Libro lib, Connection c){
		String cadena="UPDATE libros SET estado_Alquilado=0 WHERE titulo='"+lib.getTitulo()+"'"; 

		try{
			s=c.createStatement();
			s.executeUpdate(cadena);
			s.close();
		}
		catch ( SQLException e){
			System.out.println(e.getMessage());
		}
	}
	/**
	 *  método para borrar un libro
	 *  @param c se pasa la conexion
	 *  @param lib objeto libro
	 */
	public static void borrar(Libro lib, Connection c){
		String cadena="DELETE FROM libros WHERE titulo='" +  lib.getTitulo() + "' AND autor='" + lib.getAutor()+"' AND editorial='" + lib.getEditorial()+ "'";  

		try{
			s=c.createStatement();
			s.executeUpdate(cadena);
			s.close();
		}
		catch ( SQLException e){
			System.out.println(e.getMessage());
		}
	}
	/**
	 *método para comprobar si un libro existe
	 *@param c se pasa la conexion
	 *@param lib objeto libro
	 *@return t devuelve el titulo 
	 */
	public static String comprobarLibro(Libro lib, Connection c){ 
		String cadena="SELECT titulo FROM libros WHERE titulo='" + lib.getTitulo() +"'";
		try{
			s=c.createStatement();
			reg=s.executeQuery(cadena);
			if ( reg.next()){
				String t=reg.getString(1);
				s.close();
				return t;
			}

			s.close();
			return "";
		}
		catch ( SQLException e){
			//      System.out.println(e.getMessage());
			return null;
		}
	}
	/**
	 *  método para listar los libros
	 *  @param c se pasa la conexion
	 *  @return listaLibros devuelve un vector con los datos de los libros
	 */
	public static Vector<Libro> listarLibros(Connection c){
		String cadena="SELECT * FROM libros ";
		Vector <Libro> listaLibros=new Vector<Libro>();
		try{
			s=c.createStatement();
			reg=s.executeQuery(cadena);
			while ( reg.next()){
				Libro l=new Libro(reg.getString("titulo"),reg.getString("autor"),reg.getString("editorial"),reg.getBoolean("estado_alquilado"),reg.getInt("id_libro"));
				listaLibros.add(l);
			}

			s.close();
			return listaLibros;
		}
		catch ( SQLException e){
			//      System.out.println(e.getMessage());
			return null;
		}
	}
	/**
	 *  método para comprobar el estado del libro
	 *  @param c se pasa la conexion
	 *  @param lib objeto libro
	 *  @return t devuelve el estado del libro
	 */
	public static String buscarEstadoLibro(Libro lib, Connection c){
		String cadena="SELECT estado_alquilado FROM libros WHERE titulo='" + lib.getTitulo() +"'";
		try{
			s=c.createStatement();
			reg=s.executeQuery(cadena);
			if ( reg.next()){
				String t=reg.getString(1);
				s.close();
				return t;
			}

			s.close();
			return "";
		}
		catch ( SQLException e){
			//      System.out.println(e.getMessage());
			return null;
		}
	}
	/**
	 *  método para buscar el id del libro
	 *  @param c se pasa la conexion
	 *  @param lib objeto libro
	 *  @return t devuelve el id libro 
	 */
	public static int buscarIdLibro(Libro lib, Connection c){ //metodo para buscar id del libro
		String cadena="SELECT id_libro FROM libros WHERE titulo='" + lib.getTitulo() +"'";
		try{
			s=c.createStatement();
			reg=s.executeQuery(cadena);
			if ( reg.next()){
				int t=reg.getInt(1);
				s.close();
				return t;
			}

			s.close();
			return 0;
		}
		catch ( SQLException e){

			//      System.out.println(e.getMessage());
			return 0;
		}
	}
}