package bbdd;


import java.sql.*;
import java.time.LocalDate;
import java.util.Vector;
import clases.Sancion;
import clases.Sancion;
import clases.Sancion;



public class BBDDSancion {
	private static Statement s;
	private static Connection c;
	private static ResultSet reg;
	/**
	 * @see  método para añadir sanciones a los socios que no devuelvan los libros a tiempo
	 */
	public static void anadir(Sancion san, Connection c){
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
	public static void borrar(int numero, Connection c){
		String cadena="DELETE FROM sanciones WHERE n_sancion=" + numero;    

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
	/**
	 * @see  método para borrar sanciones
	 */
	public static Vector<Sancion> buscarSancion2(Connection c){

		String cadena="SELECT fecha_final, n_sancion FROM sanciones";
		Vector <Sancion> buscarSancion=new Vector<Sancion>();
		try{
			s=c.createStatement();
			reg=s.executeQuery(cadena);
			while ( reg.next()){
				java.sql.Date f=reg.getDate("fecha_final");
				int d=f.getDay();
				int m=f.getMonth();
				int a=f.getYear();
				LocalDate fecha_final=LocalDate.of(a,m,d);
				Sancion san=new Sancion(fecha_final,reg.getInt("n_sancion"));
				buscarSancion.add(san);
			}
			s.close();
			return buscarSancion;
		}
		catch ( SQLException e){
			//  System.out.println(e.getMessage());
			return null;
		}
	}
}