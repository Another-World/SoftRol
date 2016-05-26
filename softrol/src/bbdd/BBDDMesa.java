package bbdd;

import java.sql.*;
import java.util.Vector;

import clases.Mesa;
import clases.Socio;


public class BBDDMesa {
	private static Statement s;
	private static Connection c;
	private static ResultSet reg;

	public static void añadir(Mesa mesa, Connection c){
		String cadena="INSERT INTO mesas VALUES('" + mesa.getN_mesa() + "','" + mesa.getTipo()+"','"+ mesa.getEstado() +"')"; 	
		/*
		 * damos de alta una mesa introduciendo sus datos principales.
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

	public static void borrar(Mesa mesa, Connection c){
		String cadena="DELETE FROM mesas WHERE n_mesa='" +  mesa.getN_mesa() + "'";	
		/*
		 * borramos una de nuestras mesas mediante su numero identificador.
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

	public static String buscarMesa(Mesa mesa, Connection c){
		String cadena="SELECT tipo, estado FROM mesas WHERE n_mesa='" + mesa.getN_mesa() +"'";
		/*
		 * hacemos consulta mediante el numero de mesa, para saber la info de las mismas.
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
	//Metodo listar mesas
	public static Vector<Mesa> listarMesa(Connection c){
		String cadena="SELECT * FROM mesas "; //Select para listar las mesas
		Vector <Mesa> listarMesa=new Vector<Mesa>();
		try{
			s=c.createStatement();
			reg=s.executeQuery(cadena);
			while ( reg.next()){
				Mesa mes=new Mesa(reg.getInt("n_mesa"),reg.getString("tipo"),reg.getString("estado"));
				listarMesa.add(mes);
			}
			s.close();
			return listarMesa;
		}
		catch ( SQLException e){
			//		System.out.println(e.getMessage());
			return null;
		}


	}
}
