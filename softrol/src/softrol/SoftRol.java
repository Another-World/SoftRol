package softrol;
//clases del programa
import clases.*;
import bbdd.*;
//tratar excepciones
import java.io.IOException;
//fechas
import java.time.*;
//lectura de teclado
import java.util.*;
//ficheros
import java.nio.*;
import java.nio.charset.Charset;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import static java.nio.file.StandardOpenOption.APPEND;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.NoSuchFileException;
import java.util.Scanner;
import java.util.concurrent.SynchronousQueue;

import org.omg.Messaging.SyncScopeHelper;

public class SoftRol {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		BaseDatosC mibase = new BaseDatosC("mysql-properties.xml");
		Scanner sc = new Scanner(System.in);
		//	Objetos de clases
		Alquiler alq;
		Cuota cu;
		Empleado emp;
		Libro lib;
		Mesa mesa;
		Reserva res;
		Sancion san;
		Socio soc;

		//control de ficheros
		BufferedReader input = null;
		BufferedWriter output = null;
		Path plantillaCuota=Paths.get("recibos/plantillaCuota.txt");
		Path plantillaMesa=Paths.get("recibos/plantillaMesa.txt");
		Path plantillaLibro=Paths.get("recibos/plantillaLibro.txt"); 
		// Path salidaCuota=Paths.get("recibos/cuota/ticketCuota-dniSocio-"+enumerarTicket()+".txt"); // copiado abajo para que funcione en la parte de alta
		Path salidaLibro=Paths.get("recibos/libro");
		Path salidaMesa=Paths.get("recibos/mesa");
		//variables
		int opc = 1, opc2, idLibro;
		String dniSocio, operacion, titulo, repetir, usuario, dniValidado,telefonoValidado, telefono="";
		LocalDate fechaValidada, fecha;
		boolean comprobar;
		// inicio de sesión
		do {
			System.out.println("--- Iniciar sesión ---");
			System.out.print("Usuario: ");
			usuario = sc.nextLine();
			System.out.print("Password: ");
			String password = sc.nextLine();
			System.out.println();

			emp = new Empleado(usuario, password);
			mibase.abrir();
			usuario = BBDDEmpleado.comprobarRango(emp, mibase.getConexion());
			mibase.cerrar();

			do {
				if (usuario.equals("administrador")) { // Menú principal para los usuarios que sean administradores
					do {
						System.out.println("       --- Menú ---");
						System.out.println("1. Salir de la aplicación");
						System.out.println("2. Gestión de biblioteca");
						System.out.println("3. Gestión de mesas");
						System.out.println("4. Gestión de socios");
						System.out.println("5. Gestión de cuotas");
						System.out.print("Introduce una opción: ");
						opc = sc.nextInt();
						System.out.println();
						if (opc < 1 || opc > 5) {
							System.out.println("Introduce una opción entre 1 y 5.");
						}
					} while (opc < 1 || opc > 5);
				} else {
					if (usuario.equals("empleado")) { // Menú principal para los usuarios que sean empleados
						do {
							System.out.println("--- Menú ---");
							System.out.println("1. Salir de la aplicación");
							System.out.println("2. Gestión de biblioteca");
							System.out.println("3. Gestión de mesas");
							System.out.print("Introduce una opción: ");
							opc = sc.nextInt();
							System.out.println();
							if (opc < 1 || opc > 3) {
								System.out.println("Introduce una opción entre 1 y 3.");
							}
						} while (opc < 1 || opc > 3);
					} else {
						System.out.println("Usuario o contraseña incorrectos.");
					}

				}
				//----------------------------------------- PARTES DEL MENÚ  -----------------------------------------
				switch (opc) {
				case 2: // Gestion de la biblioteca
					do {
						System.out.println("--- Gestión de biblioteca ---");
						System.out.println("1. Volver al menú");
						System.out.println("2. Alquilar libro");
						System.out.println("3. Devolver libro");
						System.out.println("4. Listar libros");
						System.out.print("Introduce una opción: ");
						opc2 = sc.nextInt();
						sc.nextLine();
						System.out.println();
					} while (opc2 < 1 || opc2 > 4);
					switch (opc2) {
					case 2:
						System.out.println("--- Alquilar libro ---");
						do {
							System.out.print("Introduce el DNI del socio: ");
							dniSocio = sc.nextLine();
							comprobar=Socio.validarDni(dniSocio);
							//System.out.println(comprobar);
						}while (comprobar == false);
						dniValidado = Socio.comprobarDni(dniSocio); // validar si el socio existe en la BBDD para poder tener acceso a los libros

						if (dniSocio.equals(dniValidado)) {

							int numeroSancion=Sancion.numeroSanciones(dniSocio);
							if(numeroSancion==0){
								System.out.print("Anota el título del libro: ");
								titulo = sc.nextLine();
								titulo=titulo.toUpperCase();
								String libroValidado=Libro.comprobarLibro(titulo); // comprobar si existe el libro solicitado

								if (titulo.equals(libroValidado)) {
									System.out.println("Ha seleccionado el libro " + titulo);
									
									//falta comprobar si el libro ya está reservado
									lib = new Libro(titulo);
									mibase.abrir();
									BBDDLibro.actualizarEstadoTrue(lib, mibase.getConexion());// cambiar el boolean a true para reservarlo
									mibase.cerrar();
									//falta crear ticket
									
									//aqui va ticket
									
								}
								else{
									System.out.println("El libro "+titulo+" no está disponible en nuestro repertorio.");
								}
							}
							else{
								System.out.println("El socio "+ dniSocio+" tiene "+ numeroSancion+" sanciones pendientes. No puede alquilar libros.");
								System.out.println();
							}



						} else {
							System.out.println("No existe el socio con dni: " + dniSocio);
						}

						break;

					case 3:
						System.out.println("--- Devolver libro ---");
						break;
					case 4:
						System.out.println("--- Listar libros ---");

						mibase.abrir();
						Vector<Libro>  listaLibros = BBDDLibro.listarLibros(mibase.getConexion());//listar libros con un vector
						mibase.cerrar();
						System.out.println(listaLibros);
						break;
					}
					break;//fin del menu gestion de libros
					//-------------------------------------------------------------------------------------------------------------------------------
				case 3: // Gestion de las mesas
					do{
						System.out.print("Introduce el DNI del socio: ");
						dniSocio = sc.nextLine();
						comprobar=Socio.validarDni(dniSocio);
					}while (comprobar == false);
					dniValidado = Socio.comprobarDni(dniSocio); //validar si el socio está en la BBDD para poder reservar una mesa

					if (dniSocio.equals(dniValidado)) {
						System.out.println("--- Gestión de mesas ---");
						System.out.println("1. Volver al menú");
						System.out.println("2. Mesa de: Rol");
						System.out.println("3. Mesa de: Estrategia");
						opc2 = sc.nextInt();
						switch (opc2) {
						case 2:

							break;
						case 3:

							break;
						}
					} else {
						System.out.println("No se puede reservar mesa sin ser socio.");
					}
					break;// fin de gestion de mesas
					//-------------------------------------------------------------------------------------------------------------------------------			
				case 4:// Gestion de los socios

					do {
						System.out.println("--- Gestión de socios ---");
						System.out.println("1. Volver al menú");
						System.out.println("2. Dar de alta");
						System.out.println("3. Dar de baja");
						System.out.println("4. Listar socios");
						System.out.print("Introduce una opción: ");
						opc2 = sc.nextInt();
						sc.nextLine();
						System.out.println();
					} while (opc2 < 1 || opc2 > 4);

					switch (opc2) { //Submenú de gestion de socios
					case 2:
						System.out.println("--- Dar de alta ---");
						// comprobación para validar existencia del socio
						do{
							System.out.print("Introduce el DNI del socio: ");
							dniSocio = sc.nextLine();
							comprobar=Socio.validarDni(dniSocio);
							if (comprobar == false) {
								System.out.println("El dni es incorrecto.");
								break;
							}
							else {

								dniValidado = Socio.comprobarDni(dniSocio);
								if (dniSocio.equals(dniValidado)) {
									System.out.println("ERROR. El socio ya existe.");
								} else {
									// registrar datos del socio
									System.out.println("Introduce los datos del socio. ");
									System.out.print("Nombre :");
									String nombre = sc.nextLine();

									do{

										System.out.print("Teléfono :");
										telefono = sc.nextLine();

										telefonoValidado=Socio.validarTelefono(telefono);
										//if(telefonoValidado==null){
										//	System.out.println("El teléfono debe ser válido.");
										//}
									}while(telefonoValidado==null);
									do {
										System.out.print("Fecha de nacimiento (año-mes-dia) :");
										String fechaNacimiento = sc.nextLine();
										fecha = LocalDate.parse(fechaNacimiento);
										fechaValidada = Socio.validarFechaNacimiento(fecha);
										if (fechaValidada == null) {
											System.out.println("El socio debe tener más de 14 años. ");
											repetir="no";
											break;
										}
										else{
											soc = new Socio(nombre, telefono, dniSocio, fecha);
											mibase.abrir();
											BBDDSocio.añadir(soc, mibase.getConexion());
											mibase.cerrar();
											System.out.println("El usuario "+ nombre +" ha sido dado de alta correctamente.");
											System.out.print("¿Desea un recibo de la operación? (si / no):");
											repetir = sc.nextLine();

										}
									} while (fechaValidada == null);
									// añadir socio a la BBDD

									if(repetir.equals("si")){
										Path salidaCuota=Paths.get("recibos/cuota/ticketCuota-"+dniSocio+"-"+enumerarTicket(1,dniSocio)+".txt"); //crear el nombre del ticket
										Socio soci=Socio.ticketCuota(dniSocio); // extraer los datos del socio para plasmarlos en el ticket
										String importeCuota=Cuota.comprobarCuota(dniSocio); //extraer el precio de la cuota
										String bufferIn = "";
										try{
											input = Files.newBufferedReader(plantillaCuota);
											output = Files.newBufferedWriter(salidaCuota);
											String fechaPago=LocalDate.now().toString();
											while ( (bufferIn = input.readLine()) != null){
												//aqui se forma el ticket, sustituimos los campos entre "<" y ">" por los datos pertinentes
												bufferIn=bufferIn.replaceAll("<nombre>",soci.getNombre());
												bufferIn=bufferIn.replaceAll("<dni>",soci.getDni_socio());
												bufferIn=bufferIn.replaceAll("<fpago>",fechaPago); 
												bufferIn=bufferIn.replaceAll("<importe>",importeCuota+" €");
												output.write(bufferIn);
												output.newLine();
												System.out.println(bufferIn);
											}
											input.close();
											output.close();

										}catch(IOException e){
											//e.printStackTrace();
											System.out.println("Error:"+e.getMessage());
										}
										String nombreTicket=salidaCuota.getFileName().toString();
										System.out.println("Se ha guardado el ticket: "+ nombreTicket +".");
									}
								}
							}

						}while (comprobar == false);


						break;
					case 3:
						System.out.println("--- Dar de baja ---");

						System.out.print("Introduce el DNI del socio: ");
						dniSocio = sc.nextLine();
						dniValidado = Socio.comprobarDni(dniSocio);

						if (dniSocio.equals(dniValidado)) {

							System.out.println("comprobar si posee libros."); // continuar por aqui


						} else {
							System.out.println("No existe el socio con dni: " + dniSocio);
						}
						break;
					case 4:
						System.out.println("--- Listar socios ---");

						break;
					}

					break; // fin de gestion de socios
					//-------------------------------------------------------------------------------------------------------------------------------
				case 5: // gestion de cuotas
					System.out.println("--- Gestión de cuotas ---");
					break; // fin de gestion de socios
				} // fin del case-menu principal

			} while (opc != 1);
		} while (usuario.equals("administrador") != true && usuario.equals("empleado") != true);

		System.out.println("Programa finalizado.");
	}//cierre del Main


	// --------------------------------------------------------- METHODS -------------------------------------------------------------


	public static int enumerarTicket(int tipo, String dni){
		String nombre="";
		int valor=0,numEntero=0,cadena,devolver=0;
		if(tipo==1){ //introducir el tipo 1 al llamar a la funcion para los recibos de CUOTA
			Path salidaCuota=Paths.get("recibos/cuota");

			try{

				DirectoryStream<Path> stream = Files.newDirectoryStream(salidaCuota);
				for (Path path : stream) { // recorrer la carpeta para encontrar los archivos
					nombre=path.getFileName().toString();

					if(nombre.contains(dni)){ // coger solo los archivos con ese DNI para poder llevar un contador por cada socio y tipo de ticket
						nombre=nombre.substring(22); // quitar los primeros caracteres del nombre del archivo para quedarnos con el numero y la extension
						cadena=nombre.length()-4; // quitar la extension .txt
						nombre=nombre.substring(0,cadena); 
						numEntero = Integer.parseInt(nombre);// transformar el string a numero para poder sumarle 1

						if(numEntero>valor){
							valor=numEntero;
						}

						devolver=valor+1;
					}
				}
			}catch(IOException e){
				//e.printStackTrace();
				System.out.println("Error:"+e.getMessage());
			}
		}


		if(tipo==2){//introducir el tipo 2 al llamar a la funcion para los recibos de LIBRO
			Path salidaLibro=Paths.get("recibos/libro");

			try{

				DirectoryStream<Path> stream = Files.newDirectoryStream(salidaLibro);
				for (Path path : stream) {
					nombre=path.getFileName().toString();

					if(nombre.contains(dni)){
						nombre=nombre.substring(22);
						cadena=nombre.length()-4;
						nombre=nombre.substring(0,cadena);
						numEntero = Integer.parseInt(nombre);

						if(numEntero>valor){
							valor=numEntero;
						}

						devolver=valor+1;
					}
				}
			}catch(IOException e){
				//e.printStackTrace();
				System.out.println("Error:"+e.getMessage());
			}
		}

		if(tipo==3){//introducir el tipo 3 al llamar a la funcion para los recibos de MESA
			Path salidaMesa=Paths.get("recibos/mesa");

			try{

				DirectoryStream<Path> stream = Files.newDirectoryStream(salidaMesa);
				for (Path path : stream) {
					nombre=path.getFileName().toString();

					if(nombre.contains(dni)){
						nombre=nombre.substring(21);
						cadena=nombre.length()-4; 
						nombre=nombre.substring(0,cadena); 
						numEntero = Integer.parseInt(nombre);

						if(numEntero>valor){
							valor=numEntero;
						}

						devolver=valor+1;
					}
				}
			}catch(IOException e){
				//e.printStackTrace();
				System.out.println("Error:"+e.getMessage());
			}
		}
		if(devolver==0){
			devolver=1;
		}

		return devolver;
	}//cierre metodo enumerarTicket



} // fin del programa
