package bbdd;

import java.sql.*;

import clases.Libro;
import clases.*;

import java.util.*;

public class BBDDLibro {
	private static Statement s;
	private static Connection c;
	private static ResultSet reg;
	
	public static void añadir(Libro lib, Connection c){
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
	
	public static void actualizarEstadoTrue(Libro lib, Connection c){//metodo para alquilar un libro cambiando el estado
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
	
	public static void actualizarEstadoFalse(Libro lib, Connection c){//metodo para devolver un libro cambiando el estado
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
	
	public static String comprobarLibro(Libro lib, Connection c){ //metodo para comprobar si un libro existe
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
	//		System.out.println(e.getMessage());
			return null;
		
		}
	}
	
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
	//		System.out.println(e.getMessage());
			return null;
			
		}
	}
	
	public static String buscarEstadoLibro(Libro lib, Connection c){ //metodo para comprobar estado del libro
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
	//		System.out.println(e.getMessage());
			return null;
		
		}
	}
	
	public static int buscarIdLibro(Libro lib, Connection c){ //metodo para comprobar estado del libro
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
			
	//		System.out.println(e.getMessage());
			return 0;
		
		}
	}
	
	}
