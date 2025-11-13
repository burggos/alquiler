package scrips_alquiler;

import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.SwingUtilities;

/**
 * Clase para seleccionar un vehículo desde la tabla de autos
 * y mostrar los datos relevantes en el formulario de alquiler.
 * Proyecto: Sistema de Alquiler de Vehículos "Rueda Libre"
 * Autor: Sr. Gabriel
 */
public class seleccionar_alquiler_auto {

    public void SeleccionarAuto(JTable tabla_autos, 
                                JTextField auto, 
                                JTextField costo, 
                                JComboBox<String> estado) {

        // Esperar a que Swing actualice la selección de la tabla
        SwingUtilities.invokeLater(() -> {
            int fila = tabla_autos.getSelectedRow();
            if (fila == -1) return; // Si no hay fila seleccionada, salir silenciosamente

            // Obtener los valores relevantes de la tabla
            String vehiculo = tabla_autos.getValueAt(fila, 1).toString();  // Columna "Vehículo"
            String costoDia = tabla_autos.getValueAt(fila, 3).toString();  // Columna "Costo por Día"
            String estadoVehiculo = tabla_autos.getValueAt(fila, 4).toString(); // Columna "Estado"

            // Asignar los valores directamente
            auto.setText(vehiculo);
            costo.setText(costoDia);
            estado.setSelectedItem(estadoVehiculo);
        });
    }
}
