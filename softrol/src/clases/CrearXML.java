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
		
		BaseDatosC mibase = new BaseDatosC("mysql-properties.xml");
		mibase.abrir();
		xstream.toXML(Socio.eliminarSocioMoroso(), Files.newBufferedWriter(Paths.get(nombre)));
		mibase.cerrar();
		
	}

	public static void crearRegistroMorosos(){
		try {

			BaseDatosC mibase = new BaseDatosC("mysql-properties.xml");
			mibase.abrir();
			
			CrearXML sol = new CrearXML();
			sol.toXML("socioEliminado.xml");
			mibase.cerrar();
		
		} catch (IOException e) {
			System.out.println("Error: " + e.getMessage());
		}
	}

}
