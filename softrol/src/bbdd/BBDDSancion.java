package bbdd;


import java.sql.*;

import clases.Sancion;



public class BBDDSancion {
	private static Statement s;
	private static Connection c;
	private static ResultSet reg;
	
	public static void añadir(Sancion san, Connection c){
		String cadena="INSERT INTO sanciones VALUES('" + san.getN_sancion() + "','" + san.getFecha_inicio()+"','" + san.getFecha_final()+"','" + san.getMotivo()+"','" + san.getDni_socio()+"','"+ san.getDni_emple() +"')"; 	
		/*
		 * insertamos los valores de la sancion para darla de alta
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
	public static void borrar(Sancion san, Connection c){
		String cadena="DELETE FROM sanciones WHERE n_sancion='" +  san.getN_sancion() + "' AND motivo='" + san.getMotivo()+"' AND dni_socio='" + san.getDni_socio()+ "'";	
		/*
		 * a la hora de borrar la sancion
		 * lo hacemos mediante numero de sancion
		 * ,motivo y dni del socio
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
	
	public static String buscarSancion(Sancion san, Connection c){
		String cadena="SELECT count(*) FROM sanciones WHERE dni_socio='" + san.getDni_socio() +"'";
		/*
		 * buscamos la informacion de la sancion mediante el dni del socio.
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
