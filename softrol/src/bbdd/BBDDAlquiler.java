package bbdd;

import java.sql.*;
import clases.Alquiler;
import clases.*;

import java.time.*;
import java.util.Vector;

public class BBDDAlquiler {
	private static Statement s;
	private static Connection c;
	private static ResultSet reg;

	public static void añadir(Alquiler alq, Connection c){
		String cadena="INSERT INTO alquileres_libro VALUES('" + alq.getFecha_inicio() + "','" + alq.getFecha_final()+"','" + alq.getId_libro()+"','"+ alq.getDni_socio() +"')"; 	
		/*
		 * añadimos un socio a la tabla introduciendo todos sus datos esenciales.
		 */
		try{
			s=c.createStatement();
			s.executeUpdate(cadena);
			s.close();
		}
		catch ( SQLException e){
			System.out.println(e.getMessage());
		}
	}
	public static void borrar(Alquiler alq, Connection c){
		String cadena="DELETE FROM alquileres_libro WHERE id_libro='" +  alq.getId_libro() + "' AND dni_socio='" + alq.getDni_socio()+ "'";	
		/*
		 * borramos el alquiler de un libro mediante el id y el dni del socio 
		 * ya que son los datos que no se pueden repetir y
		 * por tanto aseguran el borrado exacto de un libro en concreto
		 */
		try{
			s=c.createStatement();
			s.executeUpdate(cadena);
			s.close();
		}
		catch ( SQLException e){
			System.out.println(e.getMessage());
		}
	}

	public static Date buscarFechaFinalAlquiler(Alquiler alq, Connection c){
		String cadena="SELECT MAX(fecha_final) FROM alquileres_libro WHERE id_libro=" + alq.getId_libro() ;
		/*
		 * esta consulta la utilizaremos para saber mediante el dni_socio que libros tiene ese socio en alquiler.
		 */
		try{
			s=c.createStatement();
			reg=s.executeQuery(cadena);
			if ( reg.next()){
				java.sql.Date  t=reg.getDate(1);
				s.close();
				return t;
			}
			s.close();
			return null;
		}
		catch ( SQLException e){
			//		System.out.println(e.getMessage());
			return null;

		}
	}

	//Metodo para buscar en la base de datos el id_libro del socio recibido en cada caso
	public static Vector<Libro> consultarIDLibro( Socio soc, Connection c){
		String cadena="SELECT id_libro FROM aquileres_libro WHERE dni_socio='" + soc.getDni_socio()+"'" ;
		Vector <Libro> IDLibros=new Vector<Libro>(); //vector para almacenar el id_libro
		try{
			s=c.createStatement();
			reg=s.executeQuery(cadena);
			while ( reg.next()){
				Libro l=new Libro(reg.getInt("id_libro"));
				IDLibros.add(l);
			}
			s.close();
			return IDLibros;
		}
		catch ( SQLException e){
			//		System.out.println(e.getMessage());
			return null;

		}
	}

}
