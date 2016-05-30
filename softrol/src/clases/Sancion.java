package clases;
//esta es la clase con los datos de las sanciones.
import java.time.*;

import bbdd.BBDDSancion;
import bbdd.BaseDatosC;
public class Sancion {

	private int n_sancion;
	private LocalDate fecha_inicio;
	private LocalDate fecha_final;
	private String motivo;
	private String dni_socio;
	private String dni_emple;
	
	public Sancion(int n_sancion, LocalDate fecha_inicio, LocalDate fecha_final, String motivo, String dni_socio,
			String dni_emple) {
		
		this.n_sancion = n_sancion;
		this.fecha_inicio = fecha_inicio;
		this.fecha_final = fecha_final;
		this.motivo = motivo;
		this.dni_socio = dni_socio;
		this.dni_emple = dni_emple;
	}

	public Sancion(String dni_socio) {
		this.dni_socio = dni_socio;
	}

	
	public Sancion(LocalDate fecha_inicio, LocalDate fecha_final, String motivo, String dni_socio, String dni_emple) {
		this.fecha_inicio = fecha_inicio;
		this.fecha_final = fecha_final;
		this.motivo = motivo;
		this.dni_socio = dni_socio;
		this.dni_emple = dni_emple;
	}

	public static int numeroSanciones(String dni){
		Sancion san;
		BaseDatosC mibase = new BaseDatosC("mysql-properties.xml");
		san = new Sancion(dni);
		mibase.abrir();
		String sanciones = BBDDSancion.buscarSancion(san, mibase.getConexion());
		mibase.cerrar();
		int numero= Integer.parseInt(sanciones);
		return  numero;
	}
	
	
	public int getN_sancion() {
		return n_sancion;
	}

	public LocalDate getFecha_inicio() {
		return fecha_inicio;
	}

	public LocalDate getFecha_final() {
		return fecha_final;
	}

	public String getMotivo() {
		return motivo;
	}

	public String getDni_socio() {
		return dni_socio;
	}

	public String getDni_emple() {
		return dni_emple;
	}

	@Override
	public String toString() {
		return "Sancion [n_sancion=" + n_sancion + ", fecha_inicio=" + fecha_inicio + ", fecha_final=" + fecha_final
				+ ", motivo=" + motivo + ", dni_socio=" + dni_socio + ", dni_emple=" + dni_emple + "]";
	}
	
	

}
