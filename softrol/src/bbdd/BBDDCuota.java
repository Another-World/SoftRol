package bbdd;

import java.sql.*;
import java.util.Vector;

import clases.*;


public class BBDDCuota {
	private static Statement s;
	private static Connection c;
	private static ResultSet reg;

	public static String buscarPrecioCuota(Cuota cu, Socio so, Connection c){
		String cadena="SELECT precio FROM cuotas,socios WHERE cuotas.tipo=socios.tipo_cuota and dni_socio='"+so.getDni_socio() +"'";
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
	//Metodo listar cuotas
	public static Vector<Cuota> listarCuota(Connection c){
		String cadena="SELECT * FROM cuotas "; //Select para listar las cuotas
		Vector <Cuota> listarCuota=new Vector<Cuota>();
		try{
			s=c.createStatement();
			reg=s.executeQuery(cadena);
			while ( reg.next()){
				Cuota cuo=new Cuota(reg.getString("tipo"),reg.getFloat("precio"));
				listarCuota.add(cuo);
			}
			s.close();
			return listarCuota;
		}
		catch ( SQLException e){
			//		System.out.println(e.getMessage());
			return null;
		}


	}
}
