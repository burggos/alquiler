package scripts_devolucion;

import static scripts_clientes.dbConnection.conectar;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JTable;
import com.toedter.calendar.JDateChooser;
import java.util.Date;
import java.text.SimpleDateFormat;

public class procesar_devolucion {

    public void Procesar(
            JTextField id,
            JTextField id_alquiler,
            JDateChooser entrega,
            JComboBox<String> estadoFisico,
            JTextField observacion,
            JTable tabla) {

        Connection conn = null;
        PreparedStatement psInsert = null;
        PreparedStatement psUpdAlquiler = null;
        PreparedStatement psUpdVehiculo = null;

        try {

            conn = conectar();
            if (conn == null) {
                JOptionPane.showMessageDialog(null, "Error: No se pudo conectar a la base de datos.");
                return;
            }

            // Validaciones
            if (id_alquiler.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Debe seleccionar un alquiler.");
                return;
            }

            Date fechaEntrega = entrega.getDate();
            if (fechaEntrega == null) {
                JOptionPane.showMessageDialog(null, "Debe seleccionar la fecha de entrega.");
                return;
            }

            // Convertir fecha
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String fechaSQL = sdf.format(fechaEntrega);

            int idAlq = Integer.parseInt(id_alquiler.getText());
            String estadoVehiculoFisico = estadoFisico.getSelectedItem().toString();
            String observ = observacion.getText().trim();

            // 1. Insertar en devolución
            String sqlInsert =
                "INSERT INTO devolucion (id_alquiler, fecha_devolucion, estado_vehiculo, observaciones) "
                + "VALUES (?, ?, ?, ?)";

            psInsert = conn.prepareStatement(sqlInsert);
            psInsert.setInt(1, idAlq);
            psInsert.setString(2, fechaSQL);
            psInsert.setString(3, estadoVehiculoFisico);
            psInsert.setString(4, observ);
            psInsert.executeUpdate();

            // 2. Actualizar alquiler → Finalizado
            String sqlUpdAlquiler =
                "UPDATE alquiler SET estado = 'Finalizado' WHERE id_alquiler = ?";

            psUpdAlquiler = conn.prepareStatement(sqlUpdAlquiler);
            psUpdAlquiler.setInt(1, idAlq);
            psUpdAlquiler.executeUpdate();

            // 3. Actualizar estado del vehículo EXACTAMENTE como lo seleccione el usuario
            String sqlUpdVehiculo =
                "UPDATE vehiculo SET estado = ? "
                + "WHERE id_vehiculo = (SELECT id_vehiculo FROM alquiler WHERE id_alquiler = ?)";

            psUpdVehiculo = conn.prepareStatement(sqlUpdVehiculo);
            psUpdVehiculo.setString(1, estadoVehiculoFisico);  // <-- Valor directo desde formulario
            psUpdVehiculo.setInt(2, idAlq);
            psUpdVehiculo.executeUpdate();


            JOptionPane.showMessageDialog(null, "Devolución procesada correctamente.");

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error SQL:\n" + e.getMessage());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error inesperado:\n" + e.getMessage());
        } finally {
            try {
                if (psInsert != null) psInsert.close();
                if (psUpdAlquiler != null) psUpdAlquiler.close();
                if (psUpdVehiculo != null) psUpdVehiculo.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "Error al cerrar conexión:\n" + e.getMessage());
            }
        }
    }
}
