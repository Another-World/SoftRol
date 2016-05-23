package bbdd;

import java.sql.*;
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
			/*
			  SELECT `cuotas`.`TIPO`, `cuotas`.`PRECIO`, `socios`.`dni_socio`
				FROM cuotas, socios
				WHERE `cuotas`.`TIPO`=`socios`.`TIPO_CUOTA`
				AND `dni_socio`='22222222B'	
			 */
	
	
	}
}
