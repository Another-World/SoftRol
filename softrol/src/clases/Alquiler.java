package clases;
import java.sql.Date;
//clase con los datos de alquileres de los libros.
import java.time.*;
import bbdd.*;

import bbdd.*;
import bbdd.BaseDatosC;

public class Alquiler {

	private LocalDate fecha_inicio;
	private LocalDate fecha_final;

	private int id_libro;
	private String dni_socio;
	public Alquiler(LocalDate fecha_inicio, LocalDate fecha_final, int id_libro,
			String dni_socio) {

		this.fecha_inicio = fecha_inicio;
		this.fecha_final = fecha_final;
		this.id_libro = id_libro;
		this.dni_socio = dni_socio;
	}
	

	public Alquiler(int id_libro, String dni_socio) {
		super();
		this.id_libro = id_libro;
		this.dni_socio = dni_socio;
	}


	public Alquiler(int id_libro) {
		this.id_libro = id_libro;
	}

	public LocalDate getFecha_inicio() {
		return fecha_inicio;
	}
	public LocalDate getFecha_final() {
		return fecha_final;
	}
	public int getId_libro() {
		return id_libro;
	}
	public String getDni_socio() {
		return dni_socio;
	}
/*
	public static boolean compararIdLibro(int id_libro,Socio soc){
		BaseDatosC mibase = new BaseDatosC("mysql-properties.xml");
		mibase.abrir();
		int longitud=BBDDAlquiler.consultarIDLibro(soc, mibase.getConexion()).size();
		System.out.println(longitud);
		for(int i=0; i<longitud; i++) {
			if (id_libro==BBDDAlquiler.consultarIDLibro(soc, mibase.getConexion()).get(i).getId_libro()){
				return true;
			}

		}
		mibase.cerrar();
		return false;
		
	}
	*/
	public static boolean pasarDeTiempo(Alquiler alq){
		BaseDatosC mibase = new BaseDatosC("mysql-properties.xml");
		mibase.abrir();
		int año=LocalDate.now().getYear()-1900;
		int mes=LocalDate.now().getMonthValue();
		int dia=LocalDate.now().getDayOfMonth();
		@SuppressWarnings("deprecation")
		java.util.Date fecha = new Date(año, mes-1, dia);
		
		java.util.Date fechafinal= BBDDAlquiler.buscarFechaFinalAlquiler(alq, mibase.getConexion());
		if(fecha.compareTo(fechafinal)>0){
			System.out.println("Fecha actual: "+fecha);
			System.out.println("Fecha devolución: "+fechafinal);
			return true;
		}
		return false;
	}
	@Override
	public String toString() {
		return "Alquiler [fecha_inicio=" + fecha_inicio + ", fecha_final=" + fecha_final + ", id_libro=" + id_libro + ", dni_socio=" + dni_socio + "]";
	}

}
