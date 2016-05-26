package clases;
//clase con los datos de las mesas
public class Mesa {

	private int n_mesa;
	private String tipo;
	private String estado;
	
	public Mesa(int n_mesa, String tipo, String estado) {
		
		this.n_mesa = n_mesa;
		this.tipo = tipo;
		this.estado = estado;
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
	
	
}
