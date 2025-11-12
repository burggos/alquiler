package scrips_clientes;

import static scrips_clientes.dbConnection.conectar;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

/**
 * Clase para modificar los datos de un cliente
 * Proyecto: Sistema de Alquiler de Vehículos "Rueda Libre"
 * Autor: Adaptado para variables txt...
 */
public class modificar_cliente {
    
    public void ModificarCliente(JTextField txtid, JTextField txtcc, JTextField txtnombre, JTextField txtapellido,
                                 JTextField txtdireccion, JTextField txtlicencia, JTextField txttelefono) {
        Connection conn = null;
        PreparedStatement ps = null;

        try {
            conn = conectar();
            
            if (conn == null) {
                JOptionPane.showMessageDialog(null, "No se pudo conectar a la base de datos.");
                return;
            }

            // Validaciones básicas
            if (txtid.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Debe seleccionar un cliente para modificar.");
                return;
            }

            if (txtnombre.getText().trim().isEmpty() || txtapellido.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Los campos 'Nombre' y 'Apellido' son obligatorios.");
                return;
            }

            if (txtlicencia.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(null, "El campo 'Número de licencia' no puede estar vacío.");
                return;
            }

            // Consulta SQL adaptada
            String sql = "UPDATE cliente SET cc=?, nombre=?, apellido=?, direccion=?, numero_licencia=?, telefono=? "
                       + "WHERE id_cliente=?";

            ps = conn.prepareStatement(sql);
            ps.setString(1, txtcc.getText().trim());
            ps.setString(2, txtnombre.getText().trim());
            ps.setString(3, txtapellido.getText().trim());
            ps.setString(4, txtdireccion.getText().trim());
            ps.setString(5, txtlicencia.getText().trim());
            ps.setString(6, txttelefono.getText().trim());
            ps.setInt(7, Integer.parseInt(txtid.getText().trim()));

            int filas = ps.executeUpdate();

            if (filas > 0) {
                JOptionPane.showMessageDialog(null, "Cliente modificado correctamente.");
            } else {
                JOptionPane.showMessageDialog(null, "No se encontró el cliente con ese ID.");
            }

        } catch (NumberFormatException nfe) {
            JOptionPane.showMessageDialog(null, "El ID debe ser un número válido.");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error SQL al modificar cliente: " + e.getMessage());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error inesperado: " + e.getMessage());
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
