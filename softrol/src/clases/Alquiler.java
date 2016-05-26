package clases;
//clase con los datos de alquileres de los libros.
import java.time.*;
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
	@Override
	public String toString() {
		return "Alquiler [fecha_inicio=" + fecha_inicio + ", fecha_final=" + fecha_final + ", id_libro=" + id_libro + ", dni_socio=" + dni_socio + "]";
	}
		
}
