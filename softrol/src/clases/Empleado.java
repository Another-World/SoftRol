package clases;

/**
 *  Esta es la clase con los datos de los empleados.
 */
public class Empleado {

	private String nombre;
	private String dni_emple;
	private String telefono;
	private String contrase�a;
	private String rango;
	
	public Empleado(String nombre, String dni_emple, String telefono, String contrase�a,
			String rango) {
		
		this.nombre = nombre;
		this.dni_emple = dni_emple;
		this.telefono = telefono;
		this.contrase�a = contrase�a;
		this.rango = rango;
	}
	
	public Empleado(String dni_emple, String contrase�a) {
		this.dni_emple = dni_emple;
		this.contrase�a = contrase�a;
	}

	public String getNombre() {
		return nombre;
	}
	public String getDni_emple() {
		return dni_emple;
	}
	public String getTelefono() {
		return telefono;
	}
	public String getContrase�a() {
		return contrase�a;
	}
	public String getRango() {
		return rango;
	}
	
	@Override
	public String toString() {
		return "Empleado [nombre=" + nombre + ", dni_emple=" + dni_emple + ", telefono="
				+ telefono + ", contrase�a=" + contrase�a + ", rango=" + rango + "]";
	}
	
	
}
