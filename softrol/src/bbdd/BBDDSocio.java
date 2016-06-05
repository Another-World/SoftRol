package bbdd;

import java.sql.*;
import java.util.Vector;

import clases.Libro;
import clases.Socio;



public class BBDDSocio {
	private static Statement s;
	private static Connection c;
	private static ResultSet reg;

	/**
	 *   método para añadir socios
	 *    @param c se pasa la conexion de la bbdd
	 *     @param soc objeto socio
	 */
	public static void anadir(Socio soc, Connection c){
		String cadena="INSERT INTO socios VALUES('" + soc.getNombre() + "','" + soc.getDni_socio()+"','" + soc.getTelefono()+"','" + soc.getFecha_nacimiento()+"','" + soc.getFecha_alta()+"','" + soc.getTipo_cuota()+"','"+ soc.getCuota_pagada() +"')";  
		/**
		 *  insertamos cada uno de los datos correspondientes a los socios
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
	 * método para borrar socios
	 *  @param c se pasa la conexion de la bbdd
	 *   @param soc objeto socio
	 */
	public static void borrar(Socio soc, Connection c){
		String cadena="DELETE FROM socios WHERE dni_socio='" + soc.getDni_socio()+ "'"; 
		/**
		 *  borramos los socios mediante los campos del nombre, dni_socio y teléfono
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
	 *   método para validar el socio
	 *    @param c se pasa la conexion de la bbdd
	 *     @param soc objeto socio
	 *     @return t devuelve el dni del socio
	 */
	public static String validarSocio(Socio soc, Connection c){
		String cadena="SELECT dni_socio FROM socios WHERE dni_socio='" + soc.getDni_socio() +"'";
		/**
		 *   buscamos los datos del socio mediante su dni
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
	 *   método para sacar los datos y realizar el ticket de pago de ese determinado socio
	 *    @param c se pasa la conexion de la bbdd
	 *     @param soc objeto socio
	 *     @return soci devuelve el objeto socio con nombre y dni
	 */
	public static Socio datosTicketPago(Socio soc, Connection c){
		String cadena="SELECT nombre, dni_socio FROM socios WHERE dni_socio='" + soc.getDni_socio() +"'";
		/**
		 *  buscamos los datos del socio mediante el dni del socio
		 */
		Socio soci;
		try{
			s=c.createStatement();
			reg=s.executeQuery(cadena);
			if ( reg.next()){
				soci=new Socio(reg.getString("nombre"),reg.getString("dni_socio"));
				s.close();
				return soci;
			}

			s.close();
			return null;
		}
		catch ( SQLException e){
			//      System.out.println(e.getMessage());
			return null;

		}
	}
	/**
	 *  método para listar el socio
	 *   @param c se pasa la conexion de la bbdd
	 *   @return listarSocio devuelve vector con la lista de los socios con todos sus datos
	 */
	public static Vector<Socio> listarSocio(Connection c){
		String cadena="SELECT * FROM socios ";
		Vector <Socio> listarSocio=new Vector<Socio>();
		try{
			s=c.createStatement();
			reg=s.executeQuery(cadena);
			while ( reg.next()){
				Socio soc=new Socio(reg.getString("nombre"),reg.getString("telefono"),reg.getString("dni_socio"),reg.getInt("cuota_pagada"));
				listarSocio.add(soc);
			}
			s.close();
			return listarSocio;
		}
		catch ( SQLException e){
			//      System.out.println(e.getMessage());
			return null;
		}


	}
	/**
	 *  método para eliminar socios
	 *   @param c se pasa la conexion de la bbdd
	 *   @return EliminarSocio vector con todos los dni de los socios
	 */
	public static Vector<Socio> EliminarSocio(Connection c){
		String cadena="SELECT dni_socio FROM socios";
		Vector <Socio> EliminarSocio=new Vector<Socio>();
		try{
			s=c.createStatement();
			reg=s.executeQuery(cadena);
			while ( reg.next()){
				Socio soc=new Socio(reg.getString("dni_socio"));
				EliminarSocio.add(soc);
			}
			s.close();
			return EliminarSocio;
		}
		catch ( SQLException e){
			//      System.out.println(e.getMessage());
			return null;
		}


	}
	/**
	 *  método para borrar socios morosos y utilizarlo para xml
	 *  @return devuelve un vector con los socios que se van a eliminar
	 *  @param c se pasa la conexion de la bbdd
	 */
	public static Vector<Socio> EliminarSocio2(Connection c){
		String cadena="SELECT nombre,dni_socio,telefono FROM socios";
		Vector <Socio> EliminarSocio=new Vector<Socio>();
		try{
			s=c.createStatement();
			reg=s.executeQuery(cadena);
			while ( reg.next()){
				Socio soc=new Socio(reg.getString("nombre"),reg.getString("dni_socio"),reg.getString("telefono"));
				EliminarSocio.add(soc);
			}
			s.close();
			return EliminarSocio;
		}
		catch ( SQLException e){
			//      System.out.println(e.getMessage());
			return null;
		}


	}
	/**
	 *  método para comprobrar si la cuota está pagada de un determinado socio
	 *  @param c se pasa la conexion de la bbdd
	 *  @param soc objeto socio
	 *  @return t devuelve si la cuota está pagada o no
	 */
	public static int comprobarCuotaPagada(Socio soc, Connection c){
		String cadena="SELECT cuota_pagada FROM socios WHERE dni_socio='" + soc.getDni_socio() +"'";
		/**
		 *  buscamos los datos del socio mediante su dni
		 */
		try{
			s=c.createStatement();
			reg=s.executeQuery(cadena);
			if ( reg.next()){
				int t=reg.getInt(1);
				s.close();
				return t;
			}

			s.close();
			return -1;
		}
		catch ( SQLException e){
			//      System.out.println(e.getMessage());
			return -1;
		}
	}
	/**
	 * método para actualizar el estado de la cuota y ponerla a 'pagada'
	 *  @param c se pasa la conexion de la bbdd
	 *   @param soc objeto socio
	 */
	public static void actualizarEstadoCuotaTrue(Socio soc, Connection c){
		String cadena="UPDATE socios SET cuota_pagada=1 WHERE dni_socio='"+soc.getDni_socio()+"'";  

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
	 *  método para actualizar el estado de la cuota y ponerla a 'no pagado'
	 *   @param c se pasa la conexion de la bbdd
	 *    @param soc objeto socio
	 */
	public static void actualizarEstadoCuotaFalse(Socio soc, Connection c){
		String cadena="UPDATE socios SET cuota_pagada=0 WHERE dni_socio='"+soc.getDni_socio()+"'";  

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
	 *  método para cambiar todas las cuotas a 0
	 *   @param c se pasa la conexion de la bbdd
	 */
	public static void cancelarPagos(Connection c){
		String cadena="UPDATE socios SET cuota_pagada=0";  

		try{
			s=c.createStatement();
			s.executeUpdate(cadena);
			s.close();
		}
		catch ( SQLException e){
			System.out.println(e.getMessage());
		}
	}
}