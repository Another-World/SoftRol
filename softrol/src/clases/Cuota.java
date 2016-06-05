package clases;

import bbdd.BBDDCuota;
import bbdd.BBDDLibro;
import bbdd.BaseDatosC;


/**
 *@see clase con los datos de las cuotas.
 */
public class Cuota {

	private String tipo;
	private float precio;
	
	public Cuota(String tipo, float precio) {
		
		this.tipo = tipo;
		this.precio = precio;
	}
	public String getTipo() {
		return tipo;
	}
	public float getPrecio() {
		return precio;
	}
	@Override
	public String toString() {
		return "Cuota \n\t tipo=" + tipo + ",\n\t precio=" + precio + "\n\n";
	}
	
	public Cuota(String tipo) {
		this.tipo = tipo;
	}
	
	  
	 
	public static String comprobarCuota(String dni){
		Cuota cu;
		Socio so;
		BaseDatosC mibase = new BaseDatosC("mysql-properties.xml");
		cu = new Cuota(dni);
		so=new Socio(dni);
		mibase.abrir();
		String importe = BBDDCuota.buscarPrecioCuota(cu,so, mibase.getConexion());
		
		mibase.cerrar();
		return importe;
	}
	

}
