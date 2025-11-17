/*
 * Clase para mostrar los daños registrados en la base de datos
 * Proyecto: Sistema de Alquiler de Vehículos "Rueda Libre"
 * Autor: Adaptado por Andrés Rodríguez
 */
package scripts_daños;

import static scripts_clientes.dbConnection.conectar;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JTable;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class mostrar {
    
    public void MostrarDanos(JTable Tdaños) {
        Connection conn = null;
        Statement st = null;
        ResultSet rs = null;
        
        // Modelo de la tabla usando tus columnas
        DefaultTableModel modelo = new DefaultTableModel();
        modelo.addColumn("ID Daño");
        modelo.addColumn("ID Devolución");
        modelo.addColumn("Descripción");
        modelo.addColumn("Costo Reparación");
        
        Tdaños.setModel(modelo);
        
        // Consulta SQL basada en la estructura real de tu tabla dano
        String sql = "SELECT id_dano, id_devolucion, descripcion, costo_reparacion "
                   + "FROM dano ORDER BY id_dano ASC";
        
        try {
            conn = conectar();
            st = conn.createStatement();
            rs = st.executeQuery(sql);
            
            // Llenar la tabla con los datos de daños
            while (rs.next()) {
                int id_dano = rs.getInt("id_dano");
                int id_devolucion = rs.getInt("id_devolucion");
                String descripcion = rs.getString("descripcion");
                String costo = rs.getString("costo_reparacion");
                
                modelo.addRow(new Object[]{id_dano, id_devolucion, descripcion, costo});
            }
            
            Tdaños.setModel(modelo);
            
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, 
                "Error al mostrar los daños:\n" + e.getMessage(), 
                "Error de conexión", 
                JOptionPane.ERROR_MESSAGE);
        } finally {
            try {
                if (rs != null) rs.close();
                if (st != null) st.close();
                if (conn != null) conn.close();
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, 
                    "Error al cerrar la conexión:\n" + ex.getMessage(), 
                    "Error", 
                    JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}

