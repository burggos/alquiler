package scripts_mantenimiento;

import javax.swing.JTable;
import javax.swing.JTextField;
import com.toedter.calendar.JDateChooser;
import javax.swing.SwingUtilities;

public class seleccionar_mantenimiento {

    public void SeleccionarVehiculo(
            JTable tabla,
            JTextField id_vehiculo,
            JTextField id_devolucion,
            JTextField descripcion1,
            JTextField descripcion2,
            JDateChooser fecha,
            JTextField costo) {

        SwingUtilities.invokeLater(() -> {

            int fila = tabla.getSelectedRow();
            if (fila == -1) return;

            // Columnas esperadas:
            // 0 = ID Vehículo
            // 1 = Vehículo (marca + modelo)
            // 2 = Estado (Dañado / Mantenimiento)
            // 3 = ID Devolución
            // 4 = Cliente

            String idVeh = tabla.getValueAt(fila, 0).toString();
            String idDev = tabla.getValueAt(fila, 3).toString();

            // ---- Asignar valores principales ----
            id_vehiculo.setText(idVeh);

            // Si no hay devolución (vehículo solo en mantenimiento)
            if (idDev == null || idDev.trim().isEmpty()) {
                id_devolucion.setText("");
            } else {
                id_devolucion.setText(idDev);
            }

            // ---- Limpiar los campos ingresables ----
            descripcion1.setText("");
            descripcion2.setText("");
            costo.setText("");
            fecha.setDate(null);

        });
    }
}
