package bbdd;

import java.sql.*;
import java.util.Vector;

import clases.Libro;
import clases.Socio;



public class BBDDSocio {
	private static Statement s;
	private static Connection c;
	private static ResultSet reg;


	public static void añadir(Socio soc, Connection c){
		String cadena="INSERT INTO socios VALUES('" + soc.getNombre() + "','" + soc.getDni_socio()+"','" + soc.getTelefono()+"','" + soc.getFecha_nacimiento()+"','" + soc.getFecha_alta()+"','" + soc.getTipo_cuota()+"','"+ soc.getCuota_pagada() +"')"; 	
		/*
		 * insertamos cada uno de los datos correspondientes a los socios
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

	public static void borrar(Socio soc, Connection c){
		String cadena="DELETE FROM socios WHERE nombre='" +  soc.getNombre() + "' AND dni_socio='" + soc.getDni_socio()+"' AND telefono='" + soc.getTelefono()+ "'";	
		/*
		 * borramos a los socios mediantes los campos
		 * nombre,dni_socio y telefono
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

	public static String validarSocio(Socio soc, Connection c){
		String cadena="SELECT dni_socio FROM socios WHERE dni_socio='" + soc.getDni_socio() +"'";
		/*
		 * buscamos los datos del socio mediante su dni
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

	public static Socio datosTicketPago(Socio soc, Connection c){
		String cadena="SELECT nombre, dni_socio FROM socios WHERE dni_socio='" + soc.getDni_socio() +"'";
		/*
		 * buscamos los datos del socio mediante su dni
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
			//		System.out.println(e.getMessage());
			return null;

		}
	}
	//Metodo listar socios
	public static Vector<Socio> listarSocio(Connection c){
		String cadena="SELECT * FROM socios "; //Select para listar los socios
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
			//		System.out.println(e.getMessage());
			return null;
		}


	}
	
	public static Vector<Socio> EliminarSocio(Connection c){
		String cadena="SELECT dni_socio FROM socios"; //Select para eliminar los socios
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
			//		System.out.println(e.getMessage());
			return null;
		}


	}
	
	public static int comprobarCuotaPagada(Socio soc, Connection c){
		String cadena="SELECT cuota_pagada FROM socios WHERE dni_socio='" + soc.getDni_socio() +"'";
		/*
		 * buscamos los datos del socio mediante su dni
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
			//		System.out.println(e.getMessage());
			return -1;
		}
	}
}
