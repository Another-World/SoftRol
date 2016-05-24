package clases;
//clase con los datos de alquileres de los libros.
import java.time.*;
public class Alquiler {

	private LocalDateTime fecha_inicio;
	private LocalDateTime fecha_final;
	private String estado_peticion;
	private int id_libro;
	private String dni_socio;
	public Alquiler(LocalDateTime fecha_inicio, LocalDateTime fecha_final, String estado_peticion, int id_libro,
			String dni_socio) {
		
		this.fecha_inicio = fecha_inicio;
		this.fecha_final = fecha_final;
		this.estado_peticion = estado_peticion;
		this.id_libro = id_libro;
		this.dni_socio = dni_socio;
	}
	public LocalDateTime getFecha_inicio() {
		return fecha_inicio;
	}
	public LocalDateTime getFecha_final() {
		return fecha_final;
	}
	public String getEstado_peticion() {
		return estado_peticion;
	}
	public int getId_libro() {
		return id_libro;
	}
	public String getDni_socio() {
		return dni_socio;
	}
	@Override
	public String toString() {
		return "Alquiler [fecha_inicio=" + fecha_inicio + ", fecha_final=" + fecha_final + ", estado_peticion="
				+ estado_peticion + ", id_libro=" + id_libro + ", dni_socio=" + dni_socio + "]";
	}
	
	

	
}
