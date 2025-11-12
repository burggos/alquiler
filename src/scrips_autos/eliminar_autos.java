package scrips_autos;

import static scrips_clientes.dbConnection.conectar;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

/**
 * Clase para eliminar vehículos de la base de datos
 * Proyecto: Sistema de Alquiler de Vehículos "Rueda Libre"
 * Autor: Sr. Gabriel
 */
public class eliminar_autos {

    // Método para eliminar un vehículo según su ID
    public void eliminarVehiculo(JTextField id) {
        Connection conn = null;
        PreparedStatement ps = null;

        try {
            // Validar que el campo ID no esté vacío
            if (id.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Debe seleccionar un vehículo para eliminar.");
                return;
            }

            conn = conectar();

            String sql = "DELETE FROM vehiculo WHERE id_vehiculo = ?";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, Integer.parseInt(id.getText().trim()));

            int filasAfectadas = ps.executeUpdate();

            if (filasAfectadas > 0) {
                JOptionPane.showMessageDialog(null, "Vehículo eliminado correctamente.");
            } else {
                JOptionPane.showMessageDialog(null, "No se encontró un vehículo con ese ID.");
            }

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "El ID debe ser un número válido.");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al eliminar vehículo: " + e.getMessage());
        } finally {
            try {
                if (ps != null) ps.close();
                if (conn != null) conn.close();
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "Error al cerrar conexión: " + ex.getMessage());
            }
        }
    }
}
