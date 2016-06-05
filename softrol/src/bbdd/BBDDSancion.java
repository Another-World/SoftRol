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
	 *  método para añadir sanciones a los socios que no devuelvan los libros a tiempo
	 *  @param san se pasa el objeto sancion
	 *  @param c se pasa la conexion
	 */
	public static void anadir(Sancion san, Connection c){
		String cadena="INSERT INTO sanciones(fecha_inicio, fecha_final, motivo, dni_socio, dni_emple) VALUES('" +  san.getFecha_inicio()+"','" + san.getFecha_final()+"','" + san.getMotivo()+"','" + san.getDni_socio()+"','"+ san.getDni_emple() +"')";   
		/**
		 *  insertamos los valores de la sanción para darla de alta
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
	 *  método para borrar sanciones una vez cooncluidas
	 *  @param c se pasa la conexion
	 *  @param numero se pasa el numero de sancion para borrarla
	 *  
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
	 *  método para buscar sanciones de socios
	 *  @param san objeto sancion
	 *  @param c se pasa la conexion
	 *  @return t se devuelve el numero de sanciones que tiene el socio
	 */
	public static String buscarSancion(Sancion san, Connection c){
		String cadena="SELECT count(*) FROM sanciones WHERE dni_socio='" + san.getDni_socio() +"'";
		/**
		 *   buscamos la información de la sanción mediante el dni del socio
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
	 *  método para borrar sanciones
	 *  @param c se pasa la conexion
	 *  @return buscarSancion vector que busca la fecha final y numero de sancion de todas las sanciones
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