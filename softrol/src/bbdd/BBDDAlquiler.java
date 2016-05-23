package bbdd;

import java.sql.*;
import clases.Alquiler;


public class BBDDAlquiler {
	private static Statement s;
	private static Connection c;
	private static ResultSet reg;
	
	public static void añadir(Alquiler alq, Connection c){
		String cadena="INSERT INTO alquileres_libro VALUES('" + alq.getFecha_inicio() + "','" + alq.getFecha_final()+"','" + alq.getEstado_peticion()+"','" + alq.getId_libro()+"','"+ alq.getDni_socio() +"')"; 	
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
	
	public static String buscarDatosAlquiler(Alquiler alq, Connection c){
		String cadena="SELECT * FROM alquileres_libros WHERE dni_socio='" + alq.getDni_socio() +"'";
		/*
		 * esta consulta la utilizaremos para saber mediante el dni_socio que libros tiene ese socio en alquiler.
		 */
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
}
