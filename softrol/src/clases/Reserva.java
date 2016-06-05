package clases;

import java.time.*;
import java.util.Date;

import bbdd.BBDDReserva;
import bbdd.BBDDSancion;
import bbdd.BaseDatosC;
/**
 * @see esta es la clase con los datos de las reservas.
 */
public class Reserva {

	private LocalTime fecha_inicio;
	private LocalTime fecha_final;
	private int n_mesa;
	private String dni_socio;
	public Reserva(LocalTime fecha_inicio, LocalTime fecha_final, int n_mesa, String dni_socio) {

		this.fecha_inicio = fecha_inicio;
		this.fecha_final = fecha_final;
		this.n_mesa = n_mesa;
		this.dni_socio = dni_socio;
	}


	public static void recorrerReservas(){
		int cont=0;
		BaseDatosC mibase = new BaseDatosC("mysql-properties.xml");
		mibase.abrir();
		int numero;
		for (int i = 0; i <BBDDReserva.buscarReserva(mibase.getConexion()).size(); i++) {
			if(BBDDReserva.buscarReserva(mibase.getConexion()).get(i).getFecha_final().compareTo(LocalTime.now())<0){
				cont++;
			}
		}
		if(cont>0){
		System.out.print("Las siguientes mesas pasan a estar disponibles: ");
		for (int i = 0; i <BBDDReserva.buscarReserva(mibase.getConexion()).size(); i++) {
			if(BBDDReserva.buscarReserva(mibase.getConexion()).get(i).getFecha_final().compareTo(LocalTime.now())<0){
				numero= BBDDReserva.buscarReserva(mibase.getConexion()).get(i).getN_mesa();
				BBDDReserva.borrar(numero, mibase.getConexion());
				i--;
				System.out.print(numero+ " ");
			}
		}
	}
		System.out.println();
		mibase.cerrar();
	}


	public Reserva(int n_mesa) {
		super();
		this.n_mesa = n_mesa;
	}


	public Reserva(LocalTime fecha_final, int n_mesa) {
		this.fecha_final = fecha_final;
		this.n_mesa = n_mesa;
	}

	public LocalTime getFecha_inicio() {
		return fecha_inicio;
	}
	public LocalTime getFecha_final() {
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
