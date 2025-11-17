package scripts_alquiler;

import static scripts_clientes.dbConnection.conectar;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class guardar_alquiler {

    // Consulta para registrar el nuevo alquiler
    private static final String SQL_INSERT_ALQUILER =
        "INSERT INTO alquiler (id_cliente, id_vehiculo, duracion_semanas, costo_total, estado) VALUES (?, ?, ?, ?, ?)";

    // Consulta para cambiar el estado del vehículo a 'Alquilado'
    private static final String SQL_UPDATE_VEHICULO =
        "UPDATE vehiculo SET estado = 'Alquilado' WHERE id_vehiculo = ?";

    public void guardarAlquiler(JTextField cliente,
                                JTextField auto,
                                JComboBox<String> duracion,
                                JTextField total) {

        Connection conn = null;
        PreparedStatement psAlquiler = null;
        PreparedStatement psVehiculo = null;

        // Variables para almacenar los valores convertidos
        int idCliente;
        int idVehiculo;
        int duracionSemanas;
        double costoTotal;

        try {
            // =============================================================
            // VALIDACIONES
            // =============================================================

            String clienteText = cliente.getText().trim();
            if (clienteText.isEmpty()) throw new Exception("Debe seleccionar un Cliente.");
            idCliente = Integer.parseInt(clienteText);

            String autoText = auto.getText().trim();
            if (autoText.isEmpty()) throw new Exception("Debe seleccionar un Vehículo.");
            idVehiculo = Integer.parseInt(autoText);

            String totalText = total.getText().trim().replace(",", ".");
            if (totalText.isEmpty() || totalText.equals("Error en Costo")) {
                throw new Exception("El Costo Total no puede estar vacío o tener un error.");
            }
            costoTotal = Double.parseDouble(totalText);

            String duracionSeleccionada = (String) duracion.getSelectedItem();
            if (duracionSeleccionada == null || duracionSeleccionada.isEmpty() || duracion.getSelectedIndex() <= 0) {
                throw new Exception("Debe seleccionar una Duración válida.");
            }
            duracionSemanas = Integer.parseInt(duracionSeleccionada.split(" ")[0]);

            // =============================================================
            // ESTADO DEL ALQUILER SIEMPRE SERÁ "Activo"
            // =============================================================
            String estadoTxt = "Activo";

            // =============================================================
            // TRANSACCIÓN
            // =============================================================

            conn = conectar();
            conn.setAutoCommit(false);

            // 1️⃣ Insertar el alquiler
            psAlquiler = conn.prepareStatement(SQL_INSERT_ALQUILER);
            psAlquiler.setInt(1, idCliente);
            psAlquiler.setInt(2, idVehiculo);
            psAlquiler.setInt(3, duracionSemanas);
            psAlquiler.setDouble(4, costoTotal);
            psAlquiler.setString(5, estadoTxt);
            psAlquiler.executeUpdate();

            // 2️⃣ Cambiar estado del vehículo a "Alquilado"
            psVehiculo = conn.prepareStatement(SQL_UPDATE_VEHICULO);
            psVehiculo.setInt(1, idVehiculo);
            int filasActualizadas = psVehiculo.executeUpdate();

            if (filasActualizadas == 0) {
                throw new Exception("No se pudo actualizar el estado del vehículo.");
            }

            // Confirmar transacción
            conn.commit();

            JOptionPane.showMessageDialog(null, "Alquiler registrado correctamente. El vehículo ahora está 'Alquilado'.");

            limpiarCampos(cliente, auto, total);

        } catch (Exception e) {

            // Error → rollback
            try {
                if (conn != null) conn.rollback();
            } catch (SQLException ex) {
                System.err.println("Error al hacer rollback: " + ex.getMessage());
            }

            JOptionPane.showMessageDialog(
                null,
                "Error al guardar el alquiler: " + e.getMessage(),
                "Error",
                JOptionPane.ERROR_MESSAGE
            );

        } finally {
            try {
                if (psAlquiler != null) psAlquiler.close();
                if (psVehiculo != null) psVehiculo.close();
                if (conn != null) {
                    conn.setAutoCommit(true);
                    conn.close();
                }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "Error al cerrar conexión: " + ex.getMessage());
            }
        }
    }

    private void limpiarCampos(JTextField cliente, JTextField auto, JTextField total) {
        cliente.setText("");
        auto.setText("");
        total.setText("");
    }
}
