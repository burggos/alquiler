package scripts_mantenimiento;

import static scripts_clientes.dbConnection.conectar;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import com.toedter.calendar.JDateChooser;

public class procesar_mantenimiento {

    public void Procesar(
            JTextField id_vehiculo,
            JTextField id_devolucion,
            JTextField descripcion1,
            JTextField descripcion2,
            JDateChooser fecha,
            JTextField costo) {

        // Validaciones básicas
        if (id_vehiculo.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Debe seleccionar un vehículo.");
            return;
        }
        if (fecha.getDate() == null) {
            JOptionPane.showMessageDialog(null, "Debe seleccionar una fecha de mantenimiento.");
            return;
        }
        if (costo.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Debe ingresar un costo.");
            return;
        }

        Connection conn = null;
        PreparedStatement psInsert = null;
        PreparedStatement psUpdVehiculo = null;
        PreparedStatement psUpdDevolucion = null;

        try {
            conn = conectar();

            // Convertir fecha
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String fechaTxt = sdf.format(fecha.getDate());

            // Descripción final
            String descripcionFinal =
                descripcion1.getText().trim() + " " + descripcion2.getText().trim();

            // INSERTAR mantenimiento
            String sqlInsert =
                "INSERT INTO mantenimiento (id_vehiculo, id_devolucion, fecha, descripcion, costo) "
                + "VALUES (?, ?, ?, ?, ?)";

            psInsert = conn.prepareStatement(sqlInsert);
            psInsert.setInt(1, Integer.parseInt(id_vehiculo.getText()));

            if (id_devolucion.getText().trim().isEmpty()) {
                psInsert.setNull(2, java.sql.Types.INTEGER);
            } else {
                psInsert.setInt(2, Integer.parseInt(id_devolucion.getText()));
            }

            psInsert.setString(3, fechaTxt);
            psInsert.setString(4, descripcionFinal);
            psInsert.setDouble(5, Double.parseDouble(costo.getText().trim()));

            psInsert.executeUpdate();

            // ACTUALIZAR estado del vehículo
            String sqlUpdVehiculo =
                "UPDATE vehiculo SET estado = 'Mantenimiento' WHERE id_vehiculo = ?";

            psUpdVehiculo = conn.prepareStatement(sqlUpdVehiculo);
            psUpdVehiculo.setInt(1, Integer.parseInt(id_vehiculo.getText()));
            psUpdVehiculo.executeUpdate();

            // SI HAY devolución → marcarla como procesada (opcional)
            if (!id_devolucion.getText().trim().isEmpty()) {

                String sqlUpdDevolucion =
                    "UPDATE devolucion SET estado_material = 'Procesado' "
                    + "WHERE id_devolucion = ?";

                psUpdDevolucion = conn.prepareStatement(sqlUpdDevolucion);
                psUpdDevolucion.setInt(1, Integer.parseInt(id_devolucion.getText()));
                psUpdDevolucion.executeUpdate();
            }

            JOptionPane.showMessageDialog(null, "Mantenimiento registrado correctamente.");

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error SQL en mantenimiento: " + e.getMessage());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
        } finally {
            try {
                if (psInsert != null) psInsert.close();
                if (psUpdVehiculo != null) psUpdVehiculo.close();
                if (psUpdDevolucion != null) psUpdDevolucion.close();
                if (conn != null) conn.close();
            } catch (Exception e) {}
        }
    }
}
