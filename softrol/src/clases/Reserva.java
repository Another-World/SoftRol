package clases;
//esta es la clase con los datos de las reservas.
import java.time.*;
public class Reserva {

	private LocalDate fecha_inicio;
	private LocalDate fecha_final;
	private int n_mesa;
	private String dni_socio;
	public Reserva(LocalDate fecha_inicio, LocalDate fecha_final, int n_mesa, String dni_socio) {
		
		this.fecha_inicio = fecha_inicio;
		this.fecha_final = fecha_final;
		this.n_mesa = n_mesa;
		this.dni_socio = dni_socio;
	}
	public LocalDate getFecha_inicio() {
		return fecha_inicio;
	}
	public LocalDate getFecha_final() {
		return fecha_final;
	}
	public int getN_mesa() {
		return n_mesa;
	}
	public String getDni_socio() {
		return dni_socio;
	}
	@Override
	public String toString() {
		return "Reserva [fecha_inicio=" + fecha_inicio + ", fecha_final=" + fecha_final + ", n_mesa=" + n_mesa
				+ ", dni_socio=" + dni_socio + "]";
	}
	
	
}
