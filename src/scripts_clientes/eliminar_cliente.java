package scripts_clientes;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

/**
 * Clase para eliminar un cliente del sistema.
 * Proyecto: Sistema de Alquiler de Vehículos "Rueda Libre"
 * Autor: Adaptado por Andrés Rodríguez
 */
public class eliminar_cliente {
    
    public void EliminarCliente(JTextField txtid) {
        dbConnection objetoConexion = new dbConnection();
        Connection conn = null;
        CallableStatement cs = null;

        try {
            // Validar que el ID no esté vacío
            if (txtid.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Debe seleccionar un cliente para eliminar.");
                return;
            }

            // Conectar a la base de datos
            conn = objetoConexion.conectar();

            // Consulta SQL adaptada a la tabla cliente
            String consulta = "DELETE FROM cliente WHERE id_cliente = ?";
            cs = conn.prepareCall(consulta);
            cs.setInt(1, Integer.parseInt(txtid.getText()));

            // Ejecutar eliminación
            int filasAfectadas = cs.executeUpdate();

            if (filasAfectadas > 0) {
                JOptionPane.showMessageDialog(null, "Cliente eliminado correctamente.");
            } else {
                JOptionPane.showMessageDialog(null, "No se encontró un cliente con ese ID.");
            }

        } catch (NumberFormatException nfe) {
            JOptionPane.showMessageDialog(null, "El ID debe ser un número válido.");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al eliminar cliente: " + e.getMessage());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error inesperado: " + e.getMessage());
        } finally {
            try {
                if (cs != null) cs.close();
                objetoConexion.cerrarConexion();
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "⚠️ Error al cerrar conexión: " + ex.getMessage());
            }
        }
    }
}

