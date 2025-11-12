package scrips_clientes;

import static scrips_clientes.dbConnection.conectar;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 * Clase para mostrar los clientes registrados en la base de datos
 * Proyecto: Sistema de Alquiler de Vehiculos "Rueda Libre"
 * Autor: Sr. Gabriel
 */
public class mostrar_cliente {
    
    public void MostrarClientes(JTable tablaClientes) {
        Connection conn = null;
        Statement st = null;
        ResultSet rs = null;

        // Definir el modelo de la tabla
        DefaultTableModel modelo = new DefaultTableModel();
        modelo.addColumn("ID");
        modelo.addColumn("CC");
        modelo.addColumn("Nombre");
        modelo.addColumn("Apellido");
        modelo.addColumn("Direccion");
        modelo.addColumn("Licencia");
        modelo.addColumn("Telefono");

        tablaClientes.setModel(modelo);

        // Consulta SQL (ajustada a la tabla cliente)
        String sql = "SELECT id_cliente, cc, nombre, apellido, direccion, numero_licencia, telefono "
                   + "FROM cliente ORDER BY id_cliente ASC";

        try {
            conn = conectar(); // Conexión desde su clase dbConnection
            st = conn.createStatement();
            rs = st.executeQuery(sql);

            // Llenar tabla con los resultados
            while (rs.next()) {
                int id = rs.getInt("id_cliente");
                String cc = rs.getString("cc");
                String nombre = rs.getString("nombre");
                String apellido = rs.getString("apellido");
                String direccion = rs.getString("direccion");
                String licencia = rs.getString("numero_licencia");
                String telefono = rs.getString("telefono");

                modelo.addRow(new Object[]{id, cc, nombre, apellido, direccion, licencia, telefono});
            }

            tablaClientes.setModel(modelo);

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, 
                "Error al mostrar clientes:\n" + e.getMessage(),
                "Error de conexion", JOptionPane.ERROR_MESSAGE);
        } finally {
            // Cerrar recursos correctamente
            try {
                if (rs != null) rs.close();
                if (st != null) st.close();
                if (conn != null) conn.close();
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, 
                    "Error al cerrar conexion:\n" + ex.getMessage());
            }
        }
    }
}
