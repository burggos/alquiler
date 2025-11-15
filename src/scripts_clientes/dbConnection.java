/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scripts_clientes;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.JOptionPane;

/**
 *
 * @author Vallegrande
 */
public class dbConnection {
    static Connection con = null;

    static String url = "jdbc:mysql://localhost:3306/rueda_libre";
;
    static String user = "root";
    static String pass = "";

    public static Connection conectar(){

        try{
            con = DriverManager.getConnection(url,user,pass);
            System.out.println("Conexión Exitosa");
            JOptionPane.showMessageDialog(null, "Se conectó a la base de datos");
        }
        catch(SQLException e){
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al conectar a la base de datos");
        }
        return con;
    }

    public void cerrarConexion(){
       try {
           if (con!=null && !con.isClosed()) {
               con.close();
               JOptionPane.showMessageDialog(null, "Se cerró la conexión");
           }
       } catch (Exception e) {
           JOptionPane.showMessageDialog(null, "No se pudo cerrar la conexión" + e.toString());
       }
    }
}
