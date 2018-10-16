/*
 * Crea un nuevo programa, llámalo U2_P10_2_Callable basado en el anterior para ejecutar el
 * procedimiento y la función (p_nombredep y f_nombredep respectivamente)
 */
package u2_p10_2_callable;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author mdfda
 */
public class U2_P10_2_Callable {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        int resp = 0;
        Scanner sc = new Scanner(System.in);
        
        System.out.println("Introduce departamento: ");
        resp = sc.nextInt();
        
        try {
            Class.forName("org.hsqldb.jdbc.JDBCDriver");

            Connection con = DriverManager.getConnection("jdbc:hsqldb:C://hsqldb-2.4.1//hsqldb//hsqldb//ejemplo1", "SA", "");

            CallableStatement cS = con.prepareCall("CALL p_nombredep(?, ?)");
            
            cS.setInt(1, resp);
            cS.registerOutParameter(2, Types.VARCHAR); //Se le pasa parámetro de salida para que podamos acceder al resultado del procedimiento
            
            //Ejecutar procedimiento
            cS.executeUpdate();
            
            System.out.println("***** PROCEDIMIENTO *****");
            System.out.print("El departamento nº: " + resp);
            
            if(cS.getString(2) == null){
                System.out.println(" NO EXISTE");
            }else{
                System.out.println(" se llama: " + cS.getString(2));
            }
            System.out.println("");
            
            ////////////////////////////////////////////////////////////////////////////////
            
            System.out.println("***** FUNCIÓN *****");
            
            cS = con.prepareCall("CALL f_nombredep(?)");
            
            cS.setInt(1, resp);
            
            //Ejecutar función
           cS.executeQuery();
            
            //Situamos el resultado sobre la primera fila
           
            
            System.out.print("El departamento nº: " + cS.getString(1));
            
            
                    
            cS.close();
            con.close();

        } catch (ClassNotFoundException ex) {
            Logger.getLogger(U2_P10_2_Callable.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            System.out.println("Código de error: " + ex.getErrorCode());
            System.out.println("Mensaje: " + ex.getMessage());
            System.out.println("Estado SQL: " + ex.getSQLState());
        }
    }
    
}
