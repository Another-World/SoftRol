package clases;

/**
 *  Esta es la clase con los datos de los empleados.
 */
public class Empleado {

	private String nombre;
	private String dni_emple;
	private String telefono;
	private String contraseña;
	private String rango;
	
	public Empleado(String nombre, String dni_emple, String telefono, String contraseña,
			String rango) {
		
		this.nombre = nombre;
		this.dni_emple = dni_emple;
		this.telefono = telefono;
		this.contraseña = contraseña;
		this.rango = rango;
	}
	
	public Empleado(String dni_emple, String contraseña) {
		this.dni_emple = dni_emple;
		this.contraseña = contraseña;
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
	public String getContraseña() {
		return contraseña;
	}
	public String getRango() {
		return rango;
	}
	
	@Override
	public String toString() {
		return "Empleado [nombre=" + nombre + ", dni_emple=" + dni_emple + ", telefono="
				+ telefono + ", contraseña=" + contraseña + ", rango=" + rango + "]";
	}
	
	
}
