package bbdd;
 
import java.sql.*;
import java.util.Vector;
 
import clases.Mesa;
import clases.Socio;
 
 
public class BBDDMesa {
    private static Statement s;
    private static Connection c;
    private static ResultSet reg;
    /**
     * @see  m�todo para a�adir una mesa
     */
    public static void a�adir(Mesa mesa, Connection c){
        String cadena="INSERT INTO mesas VALUES('" + mesa.getN_mesa() + "','" + mesa.getTipo()+"','"+ mesa.getEstado() +"')";   
        /**
         * @see  damos de alta una mesa a�adiendo sus datos principales
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
     * @see  m�todo borrar una mesa
     */
    public static void borrar(Mesa mesa, Connection c){
        String cadena="DELETE FROM mesas WHERE n_mesa='" +  mesa.getN_mesa() + "'"; 
        /**
         * @see  Se borra la mesa mediante su n�mero identificador
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
     * @see  m�todo buscar una mesa
     */
    public static String buscarMesa(Mesa mesa, Connection c){
        String cadena="SELECT tipo, estado FROM mesas WHERE n_mesa='" + mesa.getN_mesa() +"'";
        /**
         * @see  consulta para saber a informaci�n de las mesas a partir de su n�mero
         */
        try{
            s=c.createStatement();
            reg=s.executeQuery(cadena);
            if ( reg.next()){
                String t=reg.getString(1);
                s.close();
                return t;
            }
            s.close();
            return "";
        }
        catch ( SQLException e){
            //      System.out.println(e.getMessage());
            return null;
 
        }
    }
    /**
     * @see  m�todo para listar las mesas
     */
    public static Vector<Mesa> listarMesa(Connection c){
        String cadena="SELECT * FROM mesas "; //Select para listar las mesas
        Vector <Mesa> listarMesa=new Vector<Mesa>();
        try{
            s=c.createStatement();
            reg=s.executeQuery(cadena);
            while ( reg.next()){
                Mesa mes=new Mesa(reg.getInt("n_mesa"),reg.getString("tipo"),reg.getString("estado"));
                listarMesa.add(mes);
            }
            s.close();
            return listarMesa;
        }
        catch ( SQLException e){
            //      System.out.println(e.getMessage());
            return null;
        }
 
 
    }
    /**
     * @see  m�todo para buscar las mesas que est�n libres
     */
    public static int buscarMesaLibres(Mesa mesa, Connection c){
        String cadena="SELECT count(estado) FROM mesas WHERE estado='libre' and tipo='" + mesa.getTipo() +"'";
        /**
         * @see  consulta para saber la informaci�n de las que est�n libres mediante el tipo de mesa
         */
        try{
            s=c.createStatement();
            reg=s.executeQuery(cadena);
            if ( reg.next()){
                int t=reg.getInt(1);
                s.close();
                return t;
            }
            s.close();
            return 0;
        }
        catch ( SQLException e){
    //      System.out.println(e.getMessage());
            return 0;
             
        }
                 
     
     
    }
    /**
     * @see  m�todo para listar las mesas libres
     */
    public static Vector<Mesa> listarMesaLibres(Mesa mesa,Connection c){
        String cadena="SELECT n_mesa FROM mesas WHERE estado='libre' and tipo='"+mesa.getTipo() +"'"; //Select para listar las mesas
        Vector <Mesa> mostrarLibres=new Vector<Mesa>();
        try{
            s=c.createStatement();
            reg=s.executeQuery(cadena);
            while ( reg.next()){
                Mesa mes=new Mesa(reg.getInt("n_mesa"));
                mostrarLibres.add(mes);
            }
            s.close();
            return mostrarLibres;
        }
        catch ( SQLException e){
            //      System.out.println(e.getMessage());
            return null;
        }
 
 
    }
    /**
     * @see  m�todo para modificar el estado de la libre a ocupada
     */
    public static void modificarEstadoMesaOcupado(Mesa mesa, Connection c){
        String cadena="UPDATE mesas SET estado='ocupado' WHERE n_mesa='"+mesa.getN_mesa()+"'";  
        /*
         * damos de alta una reserva introduciendo su estado.
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
     * @see  m�todo para modificar el estado de la mesa a libre
     */
    public static void modificarEstadoLibre(Mesa mesa, Connection c){
        String cadena="UPDATE mesas SET estado='libre' WHERE n_mesa='"+mesa.getN_mesa()+"'";    
        /*
         * damos de alta una reserva introduciendo su estado.
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
     
}