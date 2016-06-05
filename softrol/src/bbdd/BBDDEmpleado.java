package bbdd;

import java.sql.*;

import clases.Empleado;


public class BBDDEmpleado {
	private static Statement s;
	private static Connection c;
	private static ResultSet reg;
	/**
	 * @see m�todo para a�adir empleados
	 */
	public static void a�adir(Empleado emp, Connection c){
		String cadena="INSERT INTO empleados VALUES('" + emp.getNombre() + "','" + emp.getDni_emple()+"','" + emp.getTelefono()+"','"+ emp.getContrase�a() +"')";   

		try{
			s=c.createStatement();
			s.executeUpdate(cadena);
			s.close();
		}
		catch ( SQLException e){
			System.out.println(e.getMessage());
		}
	}
	/**
	 * @see m�todo para borrar empleados
	 */
	public static void borrar(Empleado emp, Connection c){
		String cadena="DELETE FROM empleados WHERE nombre='" +  emp.getNombre() + "' AND dni_emple='" + emp.getDni_emple()+"' AND contrase�a='" + emp.getContrase�a()+ "'"; 
		/**
		 * @see  A la hora de borrar a un empleado, nos aseguramos de realizar el borrado con los datos de m�s importancia sobre este, tales como nombre, dni y su contrase�a
		 */
		try{
			s=c.createStatement();
			s.executeUpdate(cadena);
			s.close();
		}
		catch ( SQLException e){
			System.out.println(e.getMessage());
		}
	}
	/**
	 * @see  m�todo para buscar empleados
	 */
	public static String buscarEmpleado(Empleado emp, Connection c){
		String cadena="SELECT nombre, dni_emple FROM empleados WHERE telefono='" + emp.getTelefono() +"'";
		/**
		 * @see Realizamos la consulta para saber los datos m�s importantes del empleado como son su nombre y dni utilizando solamente su telefono.
		 */
		try{
			s=c.createStatement();
			reg=s.executeQuery(cadena);
			if ( reg.next()){
				String t=reg.getString(1);
				s.close();
				return t;
			}
			//return reg.getString("telefono");
			s.close();
			return "";
		}
		catch ( SQLException e){
			//      System.out.println(e.getMessage());
			return null;
		}
	}
	/**
	 * @see  m�todo para comprobrar el rango del empleado
	 */
	public static String comprobarRango(Empleado emp, Connection c){
		String cadena="SELECT rango FROM empleados WHERE dni_emple='" + emp.getDni_emple() +"' AND contrase�a='" + emp.getContrase�a()+ "'";
		try{
			s=c.createStatement();
			reg=s.executeQuery(cadena);
			if ( reg.next()){
				String t=reg.getString(1);
				s.close();
				return t;
			}
			//return reg.getString("telefono");
			s.close();
			return "";
		}
		catch ( SQLException e){
			//      System.out.println(e.getMessage());
			return null;

		}
	}
}