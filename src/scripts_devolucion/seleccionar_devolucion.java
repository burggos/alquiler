package scripts_devolucion;

import javax.swing.JTable;
import javax.swing.JTextField;
import com.toedter.calendar.JDateChooser;
import javax.swing.SwingUtilities;
import javax.swing.JComboBox;

public class seleccionar_devolucion {

    public void SeleccionarDevolucion(
            JTable tabla,
            JTextField id,            // ID de la devolución (si lo usa)
            JTextField id_alquiler,   // ESTE es el que necesitamos
            JDateChooser entrega,
            JComboBox<String> estado,
            JTextField observacion) {

        SwingUtilities.invokeLater(() -> {

            int fila = tabla.getSelectedRow();
            if (fila == -1) return;

            // Tabla estructura:
            // 0 = id_alquiler
            // 1 = cliente
            // 2 = vehículo
            // 3 = semanas
            // 4 = costo
            // 5 = estado alquiler

            String idAlq = tabla.getValueAt(fila, 0).toString();

            // Asignar solo el ID del alquiler
            id_alquiler.setText(idAlq);

            // Limpiar campos del formulario
            if (entrega != null) entrega.setDate(null);
            if (estado != null) estado.setSelectedIndex(0);
            if (observacion != null) observacion.setText("");
        });
    }
}
