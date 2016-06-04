package bbdd;

import java.sql.*;
import java.time.LocalTime;
import java.util.Vector;

import clases.Mesa;
import clases.Reserva;
import clases.Socio;


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

	public static void borrar(int numero, Connection c){
		String cadena="DELETE FROM reservas WHERE n_mesa=" +  numero;	
		/*
		 * borramos una reserva mediante el numero de esta
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

	@SuppressWarnings("deprecation")
	public static Vector<Reserva> buscarReserva(Connection c){
		
		String cadena="SELECT fecha_final, n_mesa FROM reservas";
		Vector <Reserva> buscarReserva=new Vector<Reserva>();
		try{
			s=c.createStatement();
			reg=s.executeQuery(cadena);
			while ( reg.next()){
				java.sql.Time f=reg.getTime("fecha_final");
				int h=f.getHours();
				int m=f.getMinutes();
				int s=f.getSeconds();
				LocalTime fecha_final=LocalTime.of(h,m,s);
				Reserva res=new Reserva(fecha_final,reg.getInt("n_mesa"));
				buscarReserva.add(res);
			}
			s.close();
			return buscarReserva;
		}
		catch ( SQLException e){
			//		System.out.println(e.getMessage());
			return null;
		}


	}

}
