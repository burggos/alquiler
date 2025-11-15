/*
 * Clase para mostrar los clientes registrados en la base de datos
 * Proyecto: Sistema de Alquiler de Vehículos "Rueda Libre"
 * Autor: Adaptado por ChatGPT
 */
package scripts_clientes;

import static scripts_clientes.dbConnection.conectar;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JTable;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class mostrar_cliente {
    
    public void MostrarClientes(JTable tablaClientes) {
        Connection conn = null;
        Statement st = null;
        ResultSet rs = null;
        
        // Modelo de la tabla con tus variables y nombres correctos
        DefaultTableModel modelo = new DefaultTableModel();
        modelo.addColumn("ID");
        modelo.addColumn("Cédula");
        modelo.addColumn("Nombre");
        modelo.addColumn("Apellido");
        modelo.addColumn("Teléfono");
        modelo.addColumn("Dirección");
        modelo.addColumn("Licencia");
        
        tablaClientes.setModel(modelo);
        
        // Consulta SQL actualizada según tu estructura
        String sql = "SELECT id_cliente, cc, nombre, apellido, telefono, direccion, numero_licencia "
                   + "FROM cliente ORDER BY id_cliente ASC";
        
        try {
            conn = conectar();
            st = conn.createStatement();
            rs = st.executeQuery(sql);
            
            // Llenar tabla
            while (rs.next()) {
                int id = rs.getInt("id_cliente");
                String cc = rs.getString("cc");
                String nombre = rs.getString("nombre");
                String apellido = rs.getString("apellido");
                String telefono = rs.getString("telefono");
                String direccion = rs.getString("direccion");
                String licencia = rs.getString("numero_licencia");
                
                modelo.addRow(new Object[]{id, cc, nombre, apellido, telefono, direccion, licencia});
            }
            
            tablaClientes.setModel(modelo);
            
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, 
                "Error al mostrar los clientes:\n" + e.getMessage(), 
                "Error de conexión", 
                JOptionPane.ERROR_MESSAGE);
        } finally {
            // Cerrar conexiones
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
