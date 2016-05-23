package clases;
//esta es la clase con los datos de las sanciones.
import java.time.*;
public class Sancion {

	private int n_sancion;
	private String fecha_inicio;
	private LocalDate fecha_final;
	private LocalDate motivo;
	private String dni_socio;
	private String dni_emple;
	
	public Sancion(int n_sancion, String fecha_inicio, LocalDate fecha_final, LocalDate motivo, String dni_socio,
			String dni_emple) {
		
		this.n_sancion = n_sancion;
		this.fecha_inicio = fecha_inicio;
		this.fecha_final = fecha_final;
		this.motivo = motivo;
		this.dni_socio = dni_socio;
		this.dni_emple = dni_emple;
	}

	public int getN_sancion() {
		return n_sancion;
	}

	public String getFecha_inicio() {
		return fecha_inicio;
	}

	public LocalDate getFecha_final() {
		return fecha_final;
	}

	public LocalDate getMotivo() {
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
