package clases;

import java.time.*;
import java.util.Vector;
import java.util.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import com.thoughtworks.xstream.persistence.XmlArrayList;
import com.thoughtworks.xstream.XStream;

import clases.*;
import bbdd.*;
/**
 * Esta es la clase con los datos de los socios.
 * @author david, alex, marcelo
 */

public class Socio {

	private String nombre;
	private String telefono;
	private String dni_socio;
	private LocalDate fecha_nacimiento;
	private LocalDate fecha_alta;
	private int cuota_pagada;
	private String tipo_cuota;

	public Socio(String nombre, String telefono, String dni_socio, LocalDate fecha_nacimiento) {

		this.nombre = nombre;
		this.telefono = telefono;
		this.dni_socio = dni_socio;
		this.fecha_nacimiento = fecha_nacimiento;
		this.fecha_alta = LocalDate.now();
		this.cuota_pagada = 1;
		Period tiempo=Period.between(this.fecha_nacimiento,this.fecha_alta);
		if(tiempo.getYears()>=18){
			this.tipo_cuota = "adulto";
		}
		else{
			this.tipo_cuota = "juvenil";
		}

	}


	public Socio(String nombre, String telefono, String dni_socio,int cuota_pagada) {

		this.nombre = nombre;
		this.telefono = telefono;
		this.dni_socio = dni_socio;
		this.cuota_pagada = cuota_pagada;
	}




	public Socio(String nombre,  String dni_socio, String telefono) {
		super();
		this.nombre = nombre;
		this.telefono = telefono;
		this.dni_socio = dni_socio;
	}


	public Socio(String dni_socio, int cuota_pagada) {
		super();
		this.dni_socio = dni_socio;
		this.cuota_pagada = cuota_pagada;
	}


	public Socio(String dni_socio) {
		this.dni_socio = dni_socio;
	}


	public Socio(String nombre, String dni_socio) {
		this.nombre = nombre;
		this.dni_socio = dni_socio;
		this.fecha_alta = LocalDate.now();


	}
	
	/**
	 *  metodo para comprobar el dni
	 * @param dni se pasa el dni para validarlo
	 * @return dniValidado devuelve el dni validado
	 */
	public static String comprobarDni(String dni){
		Socio soc;
		BaseDatosC mibase = new BaseDatosC("mysql-properties.xml");
		soc = new Socio(dni);
		mibase.abrir();
		String dniValidado = BBDDSocio.validarSocio(soc, mibase.getConexion());
		mibase.cerrar();
		return dniValidado;
	}

	/**
	 * metodo para contruir tickect cuota.
	 * @param dni se pasa el dni para validarlo
	 * @return soc devuelve el objeto socio
	 */
	public static Socio ticketCuota(String dni){ 
		Socio soc;
		BaseDatosC mibase = new BaseDatosC("mysql-properties.xml");
		soc = new Socio(dni);
		mibase.abrir();
		soc = BBDDSocio.datosTicketPago(soc, mibase.getConexion());
		mibase.cerrar();
		return soc;
	}

	public String getNombre() {
		return nombre;
	}

	public String getTelefono() {
		return telefono;
	}

	public String getDni_socio() {
		return dni_socio;
	}

	public LocalDate getFecha_nacimiento() {
		return fecha_nacimiento;
	}

	public LocalDate getFecha_alta() {
		return fecha_alta;
	}

	public int getCuota_pagada() {
		return cuota_pagada;
	}

	public String getTipo_cuota() {
		return tipo_cuota;
	}



	public static boolean validarDni(String dni){ // Validación del DNI

		String letradni="TRWAGMYFPDXBNJZSQVHLCKE";
		int l=dni.length();
		if (l!=9)
			return false;

		dni=dni.toUpperCase();
		char letra= dni.charAt(8);
		if (letra<'A' || letra>'Z')
			return false;
		//hay que comprobar que los 8 primeros son digitos//
		String numero=dni.substring(0,8);
		long n=Long.parseLong(numero);
		int pos=(int)(n%23);
		if (letradni.charAt(pos)!=letra)
			return false;
		return true;

	}



	public static String validarTelefono(String telefono) {  // Validación de telefono
		if (telefono.length() != 9){
			System.out.println("No tiene la longitud adecuada.");

			return null;
		}
		try {

			int numeros = Integer.parseInt(telefono.substring(0, 9));
			int condicion = Integer.parseInt(telefono.substring(0, 1));

			if (numeros<600000000 && numeros>999999999) {
				return null;
			}
			if (condicion == 6 || condicion == 7 || condicion == 9) {

				return telefono;
			}
			return null;
		}catch(NumberFormatException e){
			System.out.println("El teléfono debe ser válido.");
		}
		return null;
	}

	public static LocalDate validarFechaNacimiento(LocalDate fechanacimiento){  //validación de fecha de nacimiento
		Period fecha=Period.between(fechanacimiento,LocalDate.now());
		if(fecha.getYears()<=14){
			return null;
		}
		else{
			return fechanacimiento;
		}
	}
	
	

	public static void eliminarSocioMoroso() {
		//public static final int numeraso=1;
		//private XStream xstream;
		BaseDatosC mibase = new BaseDatosC("mysql-properties.xml");
		
		mibase.abrir();
		for(int i=0; i< BBDDSocio.EliminarSocio(mibase.getConexion()).size(); i++) {
			
		}
		/*for(int i=0; i< BBDDSocio.EliminarSocio(mibase.getConexion()).size(); i++) {
			Socio soc;

			soc = new Socio( BBDDSocio.EliminarSocio(mibase.getConexion()).get(i).getDni_socio());
			

			int cuotaComprobada = BBDDSocio.comprobarCuotaPagada(soc, mibase.getConexion());
			
			if (cuotaComprobada == 0) {

				if (LocalDate.now().getDayOfMonth() > 7) {
					System.out.println("socio eliminado por moroso");
				}
			}
		}*/
		BBDDSocio.EliminarSocio(mibase.getConexion());
		mibase.cerrar();
	}
	//Listar socios
	@Override
	public String toString() {
		return "Socio nombre=" + nombre + "\n\t telefono=" + telefono + "\n\t  dni_socio=" + dni_socio + "\n\t cuota_pagada=" + cuota_pagada + "\n\n";
	}


}
