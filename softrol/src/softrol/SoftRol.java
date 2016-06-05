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
import java.sql.Connection;

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
		int opc = 1, opc2=1, idLibro=0,mesasLibres=0,numeroMesa=0,nHoras=0, Ndias=0;
		String dniSocio, operacion, titulo,rango, repetir, usuario, dniValidado,telefonoValidado,tipo, telefono="", tematica, nombreTicket;
		LocalDate fechaValidada, fecha;
		boolean comprobar=false,validar=false,comprobarLibros=false, except=false;

		// inicio de sesión
		Socio.eliminarSocioMoroso();
		do {
			System.out.println("--- Iniciar sesión ---");
			System.out.print("Usuario: ");
			usuario = sc.nextLine();
			System.out.print("Password: ");
			String password = sc.nextLine();
			System.out.println();

			emp = new Empleado(usuario, password);
			mibase.abrir();
			rango = BBDDEmpleado.comprobarRango(emp, mibase.getConexion());
			mibase.cerrar();

			do {
				if (rango.equals("administrador")) { // Menú principal para los usuarios que sean administradores
					do {
						except=false;
						try{
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
						}catch(InputMismatchException ie){
							System.out.println("Debe introducir un valor numérico.");
							except=true;
							sc.nextLine();
						}
					} while (opc < 1 || opc > 5 || except==true);
				} else {
					if (rango.equals("empleado")) { // Menú principal para los usuarios que sean empleados
						do {
							except=false;
							try{
								System.out.println("       --- Menú ---");
								System.out.println("1. Salir de la aplicación");
								System.out.println("2. Gestión de biblioteca");
								System.out.println("3. Gestión de mesas");
								System.out.print("Introduce una opción: ");
								opc = sc.nextInt();
								System.out.println();
								if (opc < 1 || opc > 3) {
									System.out.println("Introduce una opción entre 1 y 3.");
								}
							}catch(InputMismatchException ie){
								System.out.println("Debe introducir un valor numérico.");
								except=true;
								sc.nextLine();
								
							}
						} while (opc < 1 || opc > 3 || except==true);
					} else {
						System.out.println("Usuario o contraseña incorrectos.");
					}

				}
				//----------------------------------------- PARTES DEL MENÚ  -----------------------------------------
				switch (opc) {
				case 2: // Gestion de la biblioteca
					do {
						except=false;
						try{
							
							System.out.println("--- Gestión de biblioteca ---");
							System.out.println("1. Volver al menú");
							System.out.println("2. Alquilar libro");
							System.out.println("3. Devolver libro");
							System.out.println("4. Listar libros");
							System.out.print("Introduce una opción: ");
							opc2 = sc.nextInt();
							if(opc2<1 || opc2>4){
								System.out.println("Introduce una opción entre 1 y 4");
							}
						}catch(InputMismatchException ie){
							System.out.println("Debe introducir un valor numérico.");
							except=true;
							sc.nextLine();
						}
						
						System.out.println();
					} while (opc2 < 1 || opc2 > 4 || except==true);
					sc.nextLine();
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
									lib = new Libro(titulo);
									mibase.abrir();
									String estadoLibro =BBDDLibro.buscarEstadoLibro(lib, mibase.getConexion()); // comprobar si el libro ya esta reservado
									mibase.cerrar();

									if(estadoLibro.equals("0")){
										do{
											except=false;
											try{
											System.out.println("Desea llevar el libro a casa o usarlo en el local?");
											System.out.println("1. Cancelar.");
											System.out.println("2. Usar en local.");
											System.out.println("3. Llevar a casa.");
											System.out.print("Introduce opción: ");
											opc2=sc.nextInt();
											if(opc2<1 || opc2>3){
												System.out.println("Introduce una opción entre 1 y 3");
											}
											}catch(InputMismatchException ie){
												System.out.println("Debe introducir un valor numérico.");
												except=true;
												sc.nextLine();
											}
										}while(opc2<1 || opc2>3 || except==true);
										switch(opc2){
										case 2:
											System.out.println("Ha seleccionado usar en el local.");
											LocalDate DiaReservado=LocalDate.now();
											System.out.println("debe devolver el libro el día: "+ DiaReservado);

											mibase.abrir();
											BBDDLibro.actualizarEstadoLibroTrue(lib, mibase.getConexion());// cambiar el boolean a true para reservarlo
											mibase.cerrar();

											idLibro =Libro.buscarIdLibro(titulo); //buscar el id del libro para añadirlo a la tabla de reservas

											alq=new Alquiler(DiaReservado,DiaReservado,idLibro,dniSocio);
											mibase.abrir();
											BBDDAlquiler.añadir(alq, mibase.getConexion()); // añadir la reserva a la BBDD
											mibase.cerrar();
											//creacion del ticket del libro
											Path salidaticketLibro=Paths.get("recibos/libro/ticketLibro-"+dniSocio+"-"+enumerarTicket(2,dniSocio)+".txt"); //crear el nombre del ticket
											String bufferIn = "";
											try{
												input = Files.newBufferedReader(plantillaLibro);
												output = Files.newBufferedWriter(salidaticketLibro);
												String fechaFinal=DiaReservado.toString();
												while ( (bufferIn = input.readLine()) != null){
													//aqui se forma el ticket, sustituimos los campos entre "<" y ">" por los datos pertinentes
													bufferIn=bufferIn.replaceAll("<titulo>",titulo);
													bufferIn=bufferIn.replaceAll("<dni>",dniSocio);
													bufferIn=bufferIn.replaceAll("<falquiler>",fechaFinal); 
													bufferIn=bufferIn.replaceAll("<ffinal>",fechaFinal);
													bufferIn=bufferIn.replaceAll("<tiempo>",1+" Dia");
													output.write(bufferIn);
													output.newLine();
													System.out.println(bufferIn);
												}
												input.close();
												output.close();

											}catch(IOException e){
												//e.printStackTrace();
												System.out.println("Error:"+e.getMessage());
											}// fin del ticket
											nombreTicket=salidaticketLibro.getFileName().toString();
											System.out.println("Se ha guardado el ticket: "+ nombreTicket +".");

											break;
										case 3:
											System.out.println("Ha seleccionado llevar a casa.");
											do {
												except=false;
												try{
												System.out.print("Introduce el número de dias que desea reservar: ");
												Ndias=sc.nextInt();
												if (Ndias>14) {
													System.out.println("El libro no puede ser reservado más de 14 días. ");
												}
												}catch(InputMismatchException ie){
													System.out.println("Debe introducir un valor numérico.");
													except=true;
													sc.nextLine();
												}
											} while(Ndias>14 || except==true);
											DiaReservado=LocalDate.now();
											System.out.println("El dia actual es: "+ DiaReservado);
											LocalDate DiaDevolucion=LocalDate.now().plusDays(Ndias);
											System.out.println("debe devolver el libro el día: "+ DiaDevolucion);
											mibase.abrir();
											BBDDLibro.actualizarEstadoLibroTrue(lib, mibase.getConexion());// cambiar el boolean a true para reservarlo
											mibase.cerrar();

											idLibro =Libro.buscarIdLibro(titulo); //buscar el id del libro para añadirlo a la tabla de reservas

											alq=new Alquiler(DiaReservado,DiaDevolucion,idLibro,dniSocio);
											mibase.abrir();
											BBDDAlquiler.añadir(alq, mibase.getConexion()); // añadir la reserva a la BBDD
											mibase.cerrar();
											//creacion del ticket del libro
											salidaticketLibro=Paths.get("recibos/libro/ticketLibro-"+dniSocio+"-"+enumerarTicket(2,dniSocio)+".txt"); //crear el nombre del ticket
											bufferIn = "";
											try{
												input = Files.newBufferedReader(plantillaLibro);
												output = Files.newBufferedWriter(salidaticketLibro);
												String fechainicial=DiaReservado.toString();
												String fechaFinal=DiaDevolucion.toString();
												while ( (bufferIn = input.readLine()) != null){
													//aqui se forma el ticket, sustituimos los campos entre "<" y ">" por los datos pertinentes
													bufferIn=bufferIn.replaceAll("<titulo>",titulo);
													bufferIn=bufferIn.replaceAll("<dni>",dniSocio);
													bufferIn=bufferIn.replaceAll("<falquiler>",fechainicial); 
													bufferIn=bufferIn.replaceAll("<ffinal>",fechaFinal);
													bufferIn=bufferIn.replaceAll("<tiempo>",Ndias+" Dia(s)");
													output.write(bufferIn);
													output.newLine();
													System.out.println(bufferIn);
												}
												input.close();
												output.close();

											}catch(IOException e){
												//e.printStackTrace();
												System.out.println("Error:"+e.getMessage());
											}// fin del ticket
											nombreTicket=salidaticketLibro.getFileName().toString();
											System.out.println("Se ha guardado el ticket: "+ nombreTicket +".");


											break;
										}//fin de switch 
									}
									else{


										idLibro =Libro.buscarIdLibro(titulo); //buscar el id del libro para añadirlo a la tabla de reservas

										alq=new Alquiler(idLibro);
										mibase.abrir();
										java.sql.Date fechaMayor=BBDDAlquiler.buscarFechaFinalAlquiler(alq, mibase.getConexion()); // buscar fechaFinal del libro alquilado
										mibase.cerrar();

										System.out.println("El libro "+ titulo+ " ya está alquilado.");
										System.out.println("La fecha de devolución es: " +fechaMayor);

									}

								}
								else{
									System.out.println("El libro "+titulo+" no está disponible en nuestro repertorio.");
								}
							}
							else{
								System.out.println("El socio "+ dniSocio+" tiene "+ numeroSancion+" sancion(es) pendientes. No puede alquilar libros.");
								System.out.println();
							}



						} else {
							System.out.println("No existe el socio con dni: " + dniSocio);
						}

						break;

					case 3:
						System.out.println("--- Devolver libro ---");
						do{

							System.out.print("Introduce el DNI del socio: ");
							dniSocio = sc.nextLine();
							comprobar=Socio.validarDni(dniSocio);
						}while (comprobar == false);
						dniValidado = Socio.comprobarDni(dniSocio); //validar si el socio está en la BBDD para poder devolver el libro
						if (dniSocio.equals(dniValidado)) {

							System.out.print("Introduce el titulo del libro que deseas devolver: ");
							titulo=sc.nextLine();

							lib=new Libro(titulo);
							mibase.abrir();
							idLibro=BBDDLibro.buscarIdLibro(lib,  mibase.getConexion()); //busca el id del libro a partir del titulo
							mibase.cerrar();

							soc=new Socio(dniSocio);
							mibase.abrir();
							int numeroLibros=BBDDAlquiler.consultarIDLibro(soc, mibase.getConexion()); // devuelve el numero de libros que tiene el socio en su poder
							mibase.cerrar();

							mibase.abrir();
							int longitud=BBDDAlquiler.consultarIDLibro2(idLibro,numeroLibros, soc, mibase.getConexion()).size();
							for(int i=0; i<longitud; i++) {
								if (idLibro==BBDDAlquiler.consultarIDLibro2(idLibro,numeroLibros, soc, mibase.getConexion()).get(i).getId_libro()){//busca si el socio tiene el libro especificado en su poder
									comprobarLibros=true;
								}
							}
							mibase.cerrar();

							if(comprobarLibros==true){

								alq =new Alquiler(idLibro);
								mibase.abrir();
								boolean tiempoExcedido=Alquiler.pasarDeTiempo(alq);//ver si ha excedido el tiempo 
								mibase.cerrar();

								if(tiempoExcedido==true){
									System.out.println("El socio ha excedido el tiempo de préstamo. Se procede a poner una sanción de 7 días.");
									//insert sancion

									LocalDate fecha_inicio=LocalDate.now();
									LocalDate fecha_final=LocalDate.now().plusDays(7);
									System.out.println("Introduce motivo de sanción: ");
									String motivo=sc.nextLine();



									san=new Sancion(fecha_inicio,fecha_final,motivo,dniSocio,usuario); //consultar autoIncremental
									mibase.abrir();
									BBDDSancion.añadir(san,  mibase.getConexion()); 
									mibase.cerrar();

									lib=new Libro(titulo);
									mibase.abrir();
									BBDDLibro.actualizarEstadoLibroFalse(lib,  mibase.getConexion()); //cambiar estado de libro a 0 para devolverlo
									mibase.cerrar();


									alq=new Alquiler(idLibro, dniSocio);
									mibase.abrir();
									BBDDAlquiler.borrar(alq,  mibase.getConexion()); //borrar registro de la reserva una vez devuelto el libro
									mibase.cerrar();

								}else{
									//devolver libro aqui sin sancion


									lib=new Libro(titulo);
									mibase.abrir();
									BBDDLibro.actualizarEstadoLibroFalse(lib,  mibase.getConexion()); //cambiar estado de libro a 0 para devolverlo
									mibase.cerrar();


									alq=new Alquiler(idLibro, dniSocio);
									mibase.abrir();
									BBDDAlquiler.borrar(alq,  mibase.getConexion()); //borrar registro de la reserva una vez devuelto el libro
									mibase.cerrar();
									System.out.println("El libro "+ titulo+" ha sido devuelto correctamente.");

								}

							}else{
								System.out.println("El socio"+dniSocio+" no tiene en su poder el libro \""+ titulo+"\" o no existe.");
							}
						} else {
							System.out.println("El DNI no pertenece a un socio..");
						}
						break;//fin de devolver libro
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
					sc.nextLine(); // limpiar Scanner para que no salte el dni

					Reserva.recorrerReservas();
					do{
						except=false;
						try{
						System.out.println("--- Gestión de mesas ---");
						System.out.println("1. Volver al menú. ");
						System.out.println("2. Seleccionar el tipo de mesa.");
						System.out.println("3. Listar mesas");
						System.out.print("Introduce una opción: ");
						opc2 = sc.nextInt();
						if(opc2<1 || opc2>3){
							System.out.println("Introduce una opción entre 1 y 3");
						}
						}catch(InputMismatchException ie){
							System.out.println("Debe introducir un valor numérico.");
							except=true;
							sc.nextLine();
						}
					}while(opc2<1 || opc2>3 || except==true);
					sc.nextLine();
					switch (opc2) {
					case 2:
						do{

							System.out.print("Introduce el DNI del socio: ");
							dniSocio = sc.nextLine();
							comprobar=Socio.validarDni(dniSocio);
						}while (comprobar == false);
						dniValidado = Socio.comprobarDni(dniSocio); //validar si el socio está en la BBDD para poder reservar una mesa

						if (dniSocio.equals(dniValidado)) {
							int numeroSancion=Sancion.numeroSanciones(dniSocio);
							if(numeroSancion==0){
								do{
									System.out.println("introduce el tipo ( rol / estrategia ): ");
									tematica=sc.nextLine();
								}while(tematica.equals("rol")!=true && tematica.equals("estrategia")!=true);

								if(tematica.equals("rol")){

									mesa=new Mesa(tematica);
									mibase.abrir();
									mesasLibres=BBDDMesa.buscarMesaLibres(mesa, mibase.getConexion()); // buscar mesasLibres de tipo rol
									mibase.cerrar();
									if(mesasLibres==0){
										System.out.println("No quedan mesas disponibles de tipo rol.");
									}
									else{
										Mesa.listadoMesasDisponibles(tematica);

										do{
											except=false;
											try{
											System.out.print("Introduce el número de mesa que deseas elegir: ");
											numeroMesa=sc.nextInt();
											validar=Mesa.compararMesasDisponibles(tematica, numeroMesa);
											}catch(InputMismatchException ie){
												System.out.println("Debe introducir un valor numérico.");
												except=true;
												sc.nextLine();
											}
										}while(validar!=true || except==true); 
										mesa=new Mesa(numeroMesa);
										mibase.abrir();
										BBDDMesa.modificarEstadoMesaOcupado(mesa, mibase.getConexion()); // añadir la reserva a la BBDD
										mibase.cerrar();
										do{
											except=false;
											try{
											System.out.println("Introduce el número de horas que desea reservar la mesa: ");
											nHoras=sc.nextInt();
											if(nHoras>4){
												System.out.println("No se puede reservar más de 3 horas");
											}
											}catch(InputMismatchException ie){
												System.out.println("Debe introducir un valor numérico.");
												except=true;
												sc.nextLine();
											}
										}while(nHoras>4 || except==true); 
										res=new Reserva(LocalTime.now(),LocalTime.now().plusHours(nHoras),numeroMesa,dniSocio);
										mibase.abrir();
										BBDDReserva.añadir(res, mibase.getConexion()); // añadir la reserva a la BBDD
										mibase.cerrar();

										//creacion del ticket del libro
										Path salidaticketMesa=Paths.get("recibos/mesa/ticketMesa-"+dniSocio+"-"+enumerarTicket(3,dniSocio)+".txt"); //crear el nombre del ticket
										String bufferIn = "";
										try{
											input = Files.newBufferedReader(plantillaMesa);
											output = Files.newBufferedWriter(salidaticketMesa);
											int fechaInicio=LocalTime.now().getHour();
											int fechaInicio2=LocalTime.now().getMinute();
											String fechaInicioCompleta=fechaInicio+":"+fechaInicio2;
											int fechaFinal=LocalTime.now().plusHours(nHoras).getHour();
											int fechaFinal2=LocalTime.now().plusHours(nHoras).getMinute();
											String fechaFinalCompleta=fechaFinal+":"+fechaFinal2;
											while ( (bufferIn = input.readLine()) != null){
												//aqui se forma el ticket, sustituimos los campos entre "<" y ">" por los datos pertinentes
												bufferIn=bufferIn.replaceAll("<nmesa>",numeroMesa+"");
												bufferIn=bufferIn.replaceAll("<dni>",dniSocio);
												bufferIn=bufferIn.replaceAll("<finicio>",fechaInicioCompleta); 
												bufferIn=bufferIn.replaceAll("<ffinal>",fechaFinalCompleta);
												bufferIn=bufferIn.replaceAll("<tiempo>",nHoras+" hora(s)");
												output.write(bufferIn);
												output.newLine();
												System.out.println(bufferIn);
											}
											input.close();
											output.close();

										}catch(IOException e){
											//e.printStackTrace();
											System.out.println("Error:"+e.getMessage());
										}// fin del ticket
										nombreTicket=salidaticketMesa.getFileName().toString();
										System.out.println("Se ha guardado el ticket: "+ nombreTicket +".");
									}

								}
								if(tematica.equals("estrategia")){
									mesa=new Mesa(tematica);
									mibase.abrir();
									mesasLibres=BBDDMesa.buscarMesaLibres(mesa, mibase.getConexion()); // buscar mesasLibres de tipo rol
									mibase.cerrar();
									if(mesasLibres==0){
										System.out.println("No quedan mesas disponibles de tipo rol.");
									}

									else{
										Mesa.listadoMesasDisponibles(tematica);
										do{
											except=false;
											try{
											System.out.print("Introduce el número de mesa que deseas elegir: ");
											numeroMesa=sc.nextInt();
											validar=Mesa.compararMesasDisponibles(tematica, numeroMesa);
											}catch(InputMismatchException ie){
												System.out.println("Debe introducir un valor numérico.");
												except=true;
												sc.nextLine();
											}
										}while(validar!=true || except==true);
										mesa=new Mesa(numeroMesa);
										mibase.abrir();
										BBDDMesa.modificarEstadoMesaOcupado(mesa, mibase.getConexion()); // añadir la reserva a la BBDD
										mibase.cerrar();
										do{
											except=false;
											try{
											System.out.println("Introduce el número de horas que desea reservar la mesa: ");
											nHoras=sc.nextInt();
											if(nHoras>4){
												System.out.println("No se puede reservar más de 3 horas");
											}
											}catch(InputMismatchException ie){
												System.out.println("Debe introducir un valor numérico.");
												except=true;
												sc.nextLine();
											}
										}while(nHoras>4 || except==true);
										res=new Reserva(LocalTime.now(),LocalTime.now().plusHours(nHoras),numeroMesa,dniSocio);
										mibase.abrir();
										BBDDReserva.añadir(res, mibase.getConexion()); // añadir la reserva a la BBDD
										mibase.cerrar();

										//creacion del ticket del libro
										Path salidaticketMesa=Paths.get("recibos/mesa/ticketMesa-"+dniSocio+"-"+enumerarTicket(3,dniSocio)+".txt"); //crear el nombre del ticket
										String bufferIn = "";
										try{
											input = Files.newBufferedReader(plantillaMesa);
											output = Files.newBufferedWriter(salidaticketMesa);
											int fechaInicio=LocalTime.now().getHour();
											int fechaInicio2=LocalTime.now().getMinute();
											String fechaInicioCompleta=fechaInicio+":"+fechaInicio2;
											int fechaFinal=LocalTime.now().plusHours(nHoras).getHour();
											int fechaFinal2=LocalTime.now().plusHours(nHoras).getMinute();
											String fechaFinalCompleta=fechaFinal+":"+fechaFinal2;
											while ( (bufferIn = input.readLine()) != null){
												//aqui se forma el ticket, sustituimos los campos entre "<" y ">" por los datos pertinentes
												bufferIn=bufferIn.replaceAll("<nmesa>",numeroMesa+"");
												bufferIn=bufferIn.replaceAll("<dni>",dniSocio);
												bufferIn=bufferIn.replaceAll("<finicio>",fechaInicioCompleta); 
												bufferIn=bufferIn.replaceAll("<ffinal>",fechaFinalCompleta);
												bufferIn=bufferIn.replaceAll("<tiempo>",nHoras+" hora(s)");
												output.write(bufferIn);
												output.newLine();
												System.out.println(bufferIn);
											}
											input.close();
											output.close();

										}catch(IOException e){
											//e.printStackTrace();
											System.out.println("Error:"+e.getMessage());
										}// fin del ticket
										nombreTicket=salidaticketMesa.getFileName().toString();
										System.out.println("Se ha guardado el ticket: "+ nombreTicket +".");
									}
								}
							}
							else{
								System.out.println("El socio "+ dniSocio+" tiene "+ numeroSancion+" sancion(es) pendientes. No puede reservar pesas.");
								System.out.println();
							}
						} else {
							System.out.println("No se puede reservar mesa sin ser socio.");
						}

						break;

					case 3:
						System.out.println("--- Listar mesas ---");
						mibase.abrir();
						Vector<Mesa>  listarMesa = BBDDMesa.listarMesa(mibase.getConexion());//listar mesas con un vector
						mibase.cerrar();
						System.out.println(listarMesa);	
						break;
					}

					break;// fin de gestion de mesas
					//-------------------------------------------------------------------------------------------------------------------------------			
				case 4:// Gestion de los socios

					do {
						except=false;
						try{
						System.out.println("--- Gestión de socios ---");
						System.out.println("1. Volver al menú");
						System.out.println("2. Dar de alta");
						System.out.println("3. Dar de baja");
						System.out.println("4. Listar socios");
						System.out.print("Introduce una opción: ");
						opc2 = sc.nextInt();
						sc.nextLine();
						System.out.println();
						if(opc2<1 || opc2>4){
							System.out.println("Introduce una opción entre 1 y 4");
						}
						}catch(InputMismatchException ie){
							System.out.println("Debe introducir un valor numérico.");
							except=true;
							sc.nextLine();
						}
					} while (opc2 < 1 || opc2 > 4|| except==true);

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
											//añadir socio a la BBDD
											soc = new Socio(nombre, telefono, dniSocio, fecha);
											mibase.abrir();
											BBDDSocio.añadir(soc, mibase.getConexion());
											mibase.cerrar();
											System.out.println("El usuario "+ nombre +" ha sido dado de alta correctamente.");


										}
									} while (fechaValidada == null);

									//creacion de ticket del pago de la cuota
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
											bufferIn=bufferIn.replaceAll("<importe>",importeCuota+" ");
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
									nombreTicket=salidaCuota.getFileName().toString();
									System.out.println("Se ha guardado el ticket: "+ nombreTicket +".");

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

							soc = new Socio(dniSocio);
							mibase.abrir();
							int nLibros = BBDDAlquiler.consultarIDLibro(soc, mibase.getConexion());
							mibase.cerrar();

							if(nLibros==0){
								soc = new Socio(dniSocio);
								mibase.abrir();
								BBDDSocio.borrar(soc, mibase.getConexion());
								mibase.cerrar();
								System.out.println("El socio con "+dniSocio+" ha sido eliminado. ");
							}
							else{
								System.out.println("El socio con DNI: "+dniSocio+" No ha podido ser eliminado ya que tiene "+nLibros+" libro(s) en su poder");
							}



						} else {
							System.out.println("No existe el socio con dni: " + dniSocio);
						}
						break;
					case 4:
						System.out.println("--- Listar socios ---");
						mibase.abrir();
						Vector<Socio>  listarSocio = BBDDSocio.listarSocio(mibase.getConexion());//listar socios con un vector
						mibase.cerrar();
						System.out.println(listarSocio);	
						break;
					}

					break; // fin de gestion de socios
					//-------------------------------------------------------------------------------------------------------------------------------
				case 5: // gestion de cuotas
					do{
						except=false;
						try{
						System.out.println("--- Gestión de cuotas ---");
						System.out.println("1. Volver al menú");
						System.out.println("2. Pagar Cuota");
						System.out.println("3. Listar Cuotas");
						System.out.print("Introduce una opción: ");
						opc2 = sc.nextInt();
						sc.nextLine();
						if(opc2<1 || opc2>3){
							System.out.println("Introduce una opción entre 1 y 3");
						}
						}catch(InputMismatchException ie){
							System.out.println("Debe introducir un valor numérico.");
							except=true;
							sc.nextLine();
						}
					} while (opc2 < 1 || opc2 > 3 || except==true);
					System.out.println();
					switch(opc2){
					case 2://aqui se pagan las cuotas
						System.out.println("--- Pagar cuota ---");

						do {
							System.out.print("Introduce el DNI del socio: ");
							dniSocio = sc.nextLine();
							comprobar=Socio.validarDni(dniSocio);
							//System.out.println(comprobar);
						}while (comprobar == false);
						dniValidado = Socio.comprobarDni(dniSocio); // validar si el socio existe en la BBDD para realizar el pago de la cuota.


						do{
							System.out.println("¿Estás seguro de querer realizar la operación? (si / no)" );
							repetir=sc.nextLine();
						}while(repetir.equals("si")!=true && repetir.equals("no")!=true);
						if(repetir.equals("si")){
							if (dniSocio.equals(dniValidado)) {
								soc = new Socio(dniSocio);
								mibase.abrir();
								int cuotaPagada=BBDDSocio.comprobarCuotaPagada(soc, mibase.getConexion());
								mibase.cerrar();
								if (cuotaPagada==0){
									//creacion de ticket del pago de la cuota
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
											bufferIn=bufferIn.replaceAll("<importe>",importeCuota+" ");
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
									nombreTicket=salidaCuota.getFileName().toString();
									System.out.println("Se ha guardado el ticket: "+ nombreTicket +".");



									System.out.println("");
								}else{
									System.out.println("la cuota del socio " +dniSocio+" ya está pagada para el mes en curso. ");
								}

							} else {
								System.out.println("No existe el socio con dni: " + dniSocio);
							}
						}else{
							System.out.println("La operación ha sido cancelada.");
						}

						break;//fin de pago de cuota

					case 3:
						//aqui  listamos las cuotas
						System.out.println("--- Listar cuotas ---");
						mibase.abrir();
						Vector<Cuota>  listarCuota = BBDDCuota.listarCuota(mibase.getConexion());//listar cuotas con un vector
						mibase.cerrar();
						System.out.println(listarCuota);	
						break;
						//fin de listar cuotas
					}

					break; // fin de gestion de socios
				} // fin del case-menu principal

			} while (opc != 1);
		} while (rango.equals("administrador") != true && rango.equals("empleado") != true);


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