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

	/**
	 * añadimos un socio a la tabla introduciendo todos sus datos esenciales.
	 */
	public static void anadir(Alquiler alq, Connection c){
		String cadena="INSERT INTO alquileres_libro VALUES('" + alq.getFecha_inicio() + "','" + alq.getFecha_final()+"','" + alq.getId_libro()+"','"+ alq.getDni_socio() +"')"; 	
		
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
	 *  borramos el alquiler de un libro mediante el id y el dni del socio ya que son los datos que no se pueden repetir y por tanto aseguran el borrado exacto de un libro en concreto 
	 */
	public static void borrar(Alquiler alq, Connection c){
		String cadena="DELETE FROM alquileres_libro WHERE id_libro='" +  alq.getId_libro() + "' AND dni_socio='" + alq.getDni_socio()+ "'";	
		
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
	 * esta consulta la utilizaremos para saber mediante el id_libro la fecha final del alquiler.
	 */
	public static Date buscarFechaFinalAlquiler(Alquiler alq, Connection c){
		String cadena="SELECT fecha_final FROM alquileres_libro WHERE id_libro=" + alq.getId_libro() ;
		
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

	/**
	 *Metodo para consultar el id del libro.
	 */
	public static int consultarIDLibro(Socio soc, Connection c){
		String cadena="SELECT COUNT(id_libro) FROM alquileres_libro WHERE dni_socio='"+soc.getDni_socio()+"'";//consultar por qué falla

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
			
		System.out.println(e.getMessage());
			return 0;
		
		}

	}

	/**
	 * vector para almacenar el id_libro.
	 */
public static Vector<Libro> consultarIDLibro2(int id_libro,int numero,Socio soc, Connection c){
	if(numero==0){
		System.out.println("No posee libros alquilados");
	}
	else{
		String cadena="SELECT id_libro FROM alquileres_libro WHERE dni_socio='" + soc.getDni_socio()+"'" ;
		Vector <Libro> IDLibros=new Vector<Libro>(); 
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
				System.out.println(e.getMessage());
				return null;

		}
		
	}
	return null;
}
}
