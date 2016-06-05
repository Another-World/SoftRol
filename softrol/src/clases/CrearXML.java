package clases;


import java.time.*;
import java.util.Vector;
import java.util.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import com.thoughtworks.xstream.persistence.XmlArrayList;
import com.thoughtworks.xstream.XStream;

import clases.*;
import bbdd.*;

public class CrearXML {
	
	private XStream xstream;

	public CrearXML() {
		xstream = new XStream(new DomDriver());
	}

	
	public void toXML(String nombre) throws IOException {
		xstream.alias("Socio", Socio.class);
		BaseDatosC mibase = new BaseDatosC("mysql-properties.xml");
		mibase.abrir();
		xstream.toXML(Socio.eliminarSocioMoroso(), Files.newBufferedWriter(Paths.get(nombre)));
		mibase.cerrar();
		
	}

	public static void crearRegistroMorosos(){
		try {
			int cont=0;
			BaseDatosC mibase = new BaseDatosC("mysql-properties.xml");
			mibase.abrir();
			
			
			for(int i=0; i< BBDDSocio.EliminarSocio2(mibase.getConexion()).size(); i++) {
				Socio soc;
				Socio socXML;
				
				soc = new Socio( BBDDSocio.EliminarSocio2(mibase.getConexion()).get(i).getDni_socio());


				int cuotaComprobada = BBDDSocio.comprobarCuotaPagada(soc, mibase.getConexion());

				if (cuotaComprobada == 0) {

					if (LocalDate.now().getDayOfMonth() > 4) { // CAMBIAR A 7
						cont++;
					}
				}
			}
			
			if(cont>0){
				CrearXML sol = new CrearXML();
				sol.toXML("registroSociosEliminados/RegistroSocios-"+LocalDate.now().getYear()+"-"+LocalDate.now().getMonthValue()+".xml");
			}
			
			mibase.cerrar();
		
		} catch (IOException e) {
			System.out.println("Error: " + e.getMessage());
		}
	}

}
