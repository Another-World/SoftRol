package clases; 

import java.time.*;

import bbdd.BBDDReserva;
import bbdd.BBDDSancion;
import bbdd.BaseDatosC;
/**
 * @see esta es la clase con los datos de las sanciones.
 *
 */

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




	public Sancion( LocalDate fecha_final,int n_sancion) {
		super();
		this.n_sancion = n_sancion;
		this.fecha_final = fecha_final;
	}

	public Sancion(LocalDate fecha_inicio, LocalDate fecha_final, String motivo, String dni_socio, String dni_emple) {
		this.fecha_inicio = fecha_inicio;
		this.fecha_final = fecha_final;
		this.motivo = motivo;
		this.dni_socio = dni_socio;
		this.dni_emple = dni_emple;
	}


	public Sancion(LocalDate fecha_final,int n_sancion, String dni_socio) {
		super();
		this.n_sancion = n_sancion;
		this.fecha_final = fecha_final;
		this.dni_socio = dni_socio;
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

	/**
	 * @see metodo para recorrer sancion.
	 *
	 */
	public static void recorrerSancion(){
		int cont=0;
		BaseDatosC mibase = new BaseDatosC("mysql-properties.xml");
		mibase.abrir();
		int numero;
		for (int i = 0; i <BBDDSancion.buscarSancion2(mibase.getConexion()).size(); i++) {
			if(BBDDSancion.buscarSancion2(mibase.getConexion()).get(i).getFecha_final().compareTo(LocalDate.now())<0){
				cont++;
			}
		}
		if(cont>0){
			System.out.println("---Historial de sanciones---");
			for (int i = 0; i <BBDDSancion.buscarSancion2(mibase.getConexion()).size(); i++) {
				if(BBDDSancion.buscarSancion2(mibase.getConexion()).get(i).getFecha_final().compareTo(LocalDate.now())<0){
					numero= BBDDSancion.buscarSancion2(mibase.getConexion()).get(i).getN_sancion();
					//BBDDSancion.buscarSancion2(mibase.getConexion());
					BBDDSancion.borrar(numero, mibase.getConexion());
					
					System.out.println("La sancion "+numero+" ha concluido");
					i--;
				}
			}
		}
		System.out.println();
		mibase.cerrar();

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
