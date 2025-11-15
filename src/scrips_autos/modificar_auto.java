package scrips_autos;

import static scripts_clientes.dbConnection.conectar;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import com.toedter.calendar.JYearChooser;

/**
 * Clase para modificar los datos de un vehículo
 * Proyecto: Sistema de Alquiler de Vehículos "Rueda Libre"
 * Autor: Sr. Gabriel
 */
public class modificar_auto {

    public void ModificarVehiculo(JTextField id,
                                  JTextField placa,
                                  JTextField marca,
                                  JTextField modelo,
                                  JYearChooser anio,
                                  JComboBox<String> tipo,
                                  JTextField costo_por_dia,
                                  JComboBox<String> estado) {

        Connection conn = null;
        PreparedStatement ps = null;

        try {
            conn = conectar();

            if (conn == null) {
                JOptionPane.showMessageDialog(null, "No se pudo conectar a la base de datos.");
                return;
            }

            // ⚠️ Validaciones básicas
            if (id.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Debe seleccionar un vehículo para modificar.");
                return;
            }

            if (placa.getText().trim().isEmpty() || marca.getText().trim().isEmpty() || modelo.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Los campos 'Placa', 'Marca' y 'Modelo' son obligatorios.");
                return;
            }

            if (costo_por_dia.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(null, "El campo 'Costo por día' no puede estar vacío.");
                return;
            }

            if (tipo.getSelectedIndex() == -1 || estado.getSelectedIndex() == -1) {
                JOptionPane.showMessageDialog(null, "Debe seleccionar un valor para 'Tipo' y 'Estado'.");
                return;
            }

            // 🧩 Consulta SQL de actualización
            String sql = "UPDATE vehiculo SET placa=?, marca=?, modelo=?, anio=?, tipo=?, costo_por_dia=?, estado=? "
                       + "WHERE id_vehiculo=?";

            ps = conn.prepareStatement(sql);

            // Asignar valores al PreparedStatement
            ps.setString(1, placa.getText().trim());
            ps.setString(2, marca.getText().trim());
            ps.setString(3, modelo.getText().trim());
            ps.setObject(4, anio.getYear(), java.sql.Types.SMALLINT); // ✅ año tipo YEAR(4)
            ps.setString(5, tipo.getSelectedItem().toString());
            ps.setDouble(6, Double.parseDouble(costo_por_dia.getText().trim()));
            ps.setString(7, estado.getSelectedItem().toString());
            ps.setInt(8, Integer.parseInt(id.getText().trim()));

            int filas = ps.executeUpdate();

            if (filas > 0) {
                JOptionPane.showMessageDialog(null, "Vehículo modificado correctamente.");
            } else {
                JOptionPane.showMessageDialog(null, "No se encontró el vehículo con ese ID.");
            }

        } catch (NumberFormatException nfe) {
            JOptionPane.showMessageDialog(null, "El ID o el costo deben ser números válidos.");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error SQL al modificar vehículo: " + e.getMessage());
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
