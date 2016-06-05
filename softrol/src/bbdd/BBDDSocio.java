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
	 * @see  método para añadir socios
	 */
	public static void añadir(Socio soc, Connection c){
		String cadena="INSERT INTO socios VALUES('" + soc.getNombre() + "','" + soc.getDni_socio()+"','" + soc.getTelefono()+"','" + soc.getFecha_nacimiento()+"','" + soc.getFecha_alta()+"','" + soc.getTipo_cuota()+"','"+ soc.getCuota_pagada() +"')";  
		/**
		 * @see  insertamos cada uno de los datos correspondientes a los socios
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
	 * @see  método para borrar socios
	 */
	public static void borrar(Socio soc, Connection c){
		String cadena="DELETE FROM socios WHERE dni_socio='" + soc.getDni_socio()+ "'"; 
		/**
		 * @see borramos los socios mediante los campos del nombre, dni_socio y teléfono
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
	 * @see  método para validar el socio
	 */
	public static String validarSocio(Socio soc, Connection c){
		String cadena="SELECT dni_socio FROM socios WHERE dni_socio='" + soc.getDni_socio() +"'";
		/**
		 * @see  buscamos los datos del socio mediante su dni
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
	 * @see  método para sacar los datos y realizar el ticket de pago de ese determinado socio
	 */
	public static Socio datosTicketPago(Socio soc, Connection c){
		String cadena="SELECT nombre, dni_socio FROM socios WHERE dni_socio='" + soc.getDni_socio() +"'";
		/**
		 * @see  buscamos los datos del socio mediante el dni del socio
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
	 * @see  método para listar el socio
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
	 * @see  método para eliminar socios
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
	 public static Vector<Socio> EliminarSocio2(Connection c){
	        String cadena="SELECT nombre,dni_socio,telefono,fecha_nacimiento,fecha_alta,tipo_cuota,cuota_pagada FROM socios"; //Select para eliminar los socios
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

	public static int comprobarCuotaPagada(Socio soc, Connection c){
		String cadena="SELECT cuota_pagada FROM socios WHERE dni_socio='" + soc.getDni_socio() +"'";
		/**
		 * @see buscamos los datos del socio mediante su dni
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
	 * @see  método para actualizar el estado de la cuota y ponerla a 'pagada'
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
	 * @see  método para actualizar el estado de la cuota y ponerla a 'no pagado'
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
}