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
            // 1 = Vehículo
            // 2 = Estado
            // 3 = ID Devolución
            // 4 = Cliente
            // 5 = Costo Daño  <-- NUEVO

            String idVeh = tabla.getValueAt(fila, 0).toString();
            String idDev = tabla.getValueAt(fila, 3) != null
                           ? tabla.getValueAt(fila, 3).toString()
                           : "";

            String costoDano = tabla.getValueAt(fila, 5) != null
                               ? tabla.getValueAt(fila, 5).toString()
                               : "";

            // ---- Asignar valores principales ----
            id_vehiculo.setText(idVeh);
            id_devolucion.setText(idDev);
            costo.setText(costoDano);

            // ---- Limpiar campos de ingreso ----
            descripcion1.setText("");
            descripcion2.setText("");
            fecha.setDate(null);

        });
    }
}
