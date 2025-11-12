package scrips_clientes;

import static scrips_clientes.dbConnection.conectar;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

/**
 * Clase para modificar los datos de un cliente
 * Proyecto: Sistema de Alquiler de Vehiculos "Rueda Libre"
 * Autor: Sr. Gabriel
 */
public class modificar_cliente {
    
    public void ModificarCliente(JTextField id_cliente, JTextField cc, JTextField nombre, JTextField apellido,
                                 JTextField direccion, JTextField licencia, JTextField telefono) {
        Connection conn = null;
        PreparedStatement ps = null;

        try {
            conn = conectar();
            
            if (conn == null) {
                JOptionPane.showMessageDialog(null, "No se pudo conectar a la base de datos.");
                return;
            }

            // Validaciones básicas
            if (id_cliente.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Debe seleccionar un cliente para modificar.");
                return;
            }

            if (nombre.getText().trim().isEmpty() || apellido.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Los campos 'Nombre' y 'Apellido' son obligatorios.");
                return;
            }

            if (licencia.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(null, "El campo 'Numero de licencia' no puede estar vacio.");
                return;
            }

            // Consulta SQL adaptada
            String sql = "UPDATE cliente SET cc=?, nombre=?, apellido=?, direccion=?, numero_licencia=?, telefono=? "
                       + "WHERE id_cliente=?";

            ps = conn.prepareStatement(sql);
            ps.setString(1, cc.getText());
            ps.setString(2, nombre.getText());
            ps.setString(3, apellido.getText());
            ps.setString(4, direccion.getText());
            ps.setString(5, licencia.getText());
            ps.setString(6, telefono.getText());
            ps.setInt(7, Integer.parseInt(id_cliente.getText()));

            int filas = ps.executeUpdate();

            if (filas > 0) {
                JOptionPane.showMessageDialog(null, "Cliente modificado correctamente.");
            } else {
                JOptionPane.showMessageDialog(null, "No se encontro el cliente con ese ID.");
            }

        } catch (NumberFormatException nfe) {
            JOptionPane.showMessageDialog(null, "El ID debe ser un numero valido.");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error SQL al modificar cliente: " + e.getMessage());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error inesperado: " + e.getMessage());
        } finally {
            try {
                if (ps != null) ps.close();
                if (conn != null) conn.close();
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "Error al cerrar conexion: " + ex.getMessage());
            }
        }
    }
}
