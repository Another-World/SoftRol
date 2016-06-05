package bbdd;


import java.sql.*;

import clases.Sancion;



public class BBDDSancion {
	private static Statement s;
	private static Connection c;
	private static ResultSet reg;
	/**
	 * @see  método para añadir sanciones a los socios que no devuelvan los libros a tiempo
	 */
	public static void añadir(Sancion san, Connection c){
		String cadena="INSERT INTO sanciones(fecha_inicio, fecha_final, motivo, dni_socio, dni_emple) VALUES('" +  san.getFecha_inicio()+"','" + san.getFecha_final()+"','" + san.getMotivo()+"','" + san.getDni_socio()+"','"+ san.getDni_emple() +"')";   
		/**
		 * @see  insertamos los valores de la sanción para darla de alta
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
	/**
	 * @see  método para borrar sanciones una vez cooncluidas
	 */
	public static void borrar(Sancion san, Connection c){
		String cadena="DELETE FROM sanciones WHERE n_sancion='" +  san.getN_sancion() + "' AND motivo='" + san.getMotivo()+"' AND dni_socio='" + san.getDni_socio()+ "'";   
		/**
		 * @see borrar la sanción mediante el número de sanción, motivo y el dni del socio
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
	/**
	 * @see  método para buscar sanciones de socios
	 */
	public static String buscarSancion(Sancion san, Connection c){
		String cadena="SELECT count(*) FROM sanciones WHERE dni_socio='" + san.getDni_socio() +"'";
		/**
		 * @see  buscamos la información de la sanción mediante el dni del socio
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
			//      System.out.println(e.getMessage());
			return null;

		}

	}
}