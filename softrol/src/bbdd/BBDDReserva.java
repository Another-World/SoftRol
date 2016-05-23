package bbdd;

import java.sql.*;

import clases.Reserva;


public class BBDDReserva {
	private static Statement s;
	private static Connection c;
	private static ResultSet reg;
	
	public static void añadir(Reserva res, Connection c){
		String cadena="INSERT INTO reservas VALUES('" + res.getFecha_inicio() + "','" + res.getFecha_final()+"','" + res.getN_mesa()+"','"+ res.getDni_socio() +"')"; 	
		/*
		 * damos de alta una reserva,
		 * en la cual guardamos
		 * su horario de uso
		 * el numero de la misma y el dni del socio
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
	
	public static void borrar(Reserva res, Connection c){
		String cadena="DELETE FROM reservas WHERE n_mesa='" +  res.getN_mesa() + "' AND dni_socio='" + res.getDni_socio()+ "'";	
		/*
		 * borramos una reserva mediante el numero de esta
		 * y el dni del socio que habria reservado
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
	
	public static String buscarReservas(Reserva res, Connection c){
		String cadena="SELECT fecha_inicio, fecha_final, dni_socio FROM reservas WHERE n_mesa='" + res.getN_mesa() +"'";
		/*
		 * consultamos el estado de las mesas, para poder ofrecerles
		 * una informacion exacta a nuestros clientes
		 * de que mesas tenemos disponibles,
		 * por tanto buscamos el estado de las mesas mediante el numero de estas
		 * y establecemos parametros de uso.
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
