package clases;
//Esta es la clase con los datos de los socios.
import java.time.*;
import clases.*;
import bbdd.*;
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

	public Socio(String dni_socio) {
		this.dni_socio = dni_socio;
	}


	public Socio(String nombre, String dni_socio) {
		this.nombre = nombre;
		this.dni_socio = dni_socio;
		this.fecha_alta = LocalDate.now();


	}

	public static String comprobarDni(String dni){
		Socio soc;
		BaseDatosC mibase = new BaseDatosC("mysql-properties.xml");
		soc = new Socio(dni);
		mibase.abrir();
		String dniValidado = BBDDSocio.validarSocio(soc, mibase.getConexion());
		mibase.cerrar();
		return dniValidado;
	}

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

	

	public static String validarLongitudDni(String dniSocio){ // Validación del DNI
		    if (dniSocio.length()!=9)
		    	return null;
			int numeros= Integer.parseInt(dniSocio.substring(0,8));
			String letra= dniSocio.substring(8,9);
		
			char letra2=letra.charAt(0);
			letra2=Character.toUpperCase(letra2);
		if (numeros>0 && numeros<99999999 && letra2>='A' && letra2<='Z'){
			
			return dniSocio;
		}
		return null;
	}
	
	public static String validarTelefono(String telefono) {  // Validación de telefono
		if (telefono.length() != 9)
			return null;
		int numeros = Integer.parseInt(telefono.substring(0, 9));
		int condicion = Integer.parseInt(telefono.substring(0, 1));
		
		if (condicion == 6 || condicion == 7 || condicion == 9) {

			return telefono;
		}
		return null;
	}
	
	public  LocalDate validarFechaNacimiento(LocalDate fechanacimiento){  //validación de fecha de nacimiento
		Period fecha=Period.between(this.fecha_nacimiento,LocalDate.now());
		if(fecha.getYears()<=14){
			return null;
		}
		else{
			return fechanacimiento;
		}

	}
	

	@Override
	public String toString() {
		return "Socio [nombre=" + nombre + ", telefono=" + telefono + ", dni_socio=" + dni_socio + ", fecha_nacimiento="
				+ fecha_nacimiento + ", fecha_alta=" + fecha_alta + ", cuota_pagada=" + cuota_pagada + ", tipo_cuota="
				+ tipo_cuota + "]";
	}


}
