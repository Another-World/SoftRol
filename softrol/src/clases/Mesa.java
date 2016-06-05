package clases;

import java.time.LocalDate;

import bbdd.BBDDMesa;
import bbdd.BBDDSocio;
import bbdd.BaseDatosC;


/**
 * @see clase con los datos de las mesas
 *
 */
public class Mesa {

	private int n_mesa;
	private String tipo;
	private String estado;

	public Mesa(int n_mesa, String tipo, String estado) {

		this.n_mesa = n_mesa;
		this.tipo = tipo;
		this.estado = estado;
	}


	public Mesa(int n_mesa) {
		super();
		this.n_mesa = n_mesa;
	}


	public Mesa(String tipo) {
		super();
		this.tipo = tipo;
	}


	public int getN_mesa() {
		return n_mesa;
	}

	public String getTipo() {
		return tipo;
	}

	public String getEstado() {
		return estado;
	}

	@Override
	public String toString() {
		return "Mesa \n\t n_mesa=" + n_mesa + ",\n\t tipo=" + tipo + ",\n\t estado=" + estado + "\n\n";
	}

	public static void listadoMesasDisponibles(String tipo){
		Mesa mesa;
		BaseDatosC mibase = new BaseDatosC("mysql-properties.xml");
		mesa=new Mesa(tipo);
		mibase.abrir();
		int longitud=BBDDMesa.listarMesaLibres(mesa, mibase.getConexion()).size();
		System.out.print("Estas son las mesas disponibles: ");
		for(int i=0; i<longitud; i++) {
			int numeroMesa=(BBDDMesa.listarMesaLibres(mesa, mibase.getConexion()).get(i).getN_mesa());
			System.out.print(numeroMesa+" ");
		}
		System.out.println("");
		mibase.cerrar();



	}
	public static boolean compararMesasDisponibles(String tipo, int nMesa){
		Mesa mesa;
		BaseDatosC mibase = new BaseDatosC("mysql-properties.xml");
		mesa=new Mesa(tipo);
		mibase.abrir();
		int longitud=BBDDMesa.listarMesaLibres(mesa, mibase.getConexion()).size();
		for(int i=0; i<longitud; i++) {
			int numeroMesa=(BBDDMesa.listarMesaLibres(mesa, mibase.getConexion()).get(i).getN_mesa());
			if(nMesa==numeroMesa){
				return true;
			}
		}
		mibase.cerrar();

		return false;

	}
	
}
