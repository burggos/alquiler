package scripts_alquiler;

import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.SwingUtilities;
import javax.swing.JOptionPane; 

public class seleccionar_alquiler_auto {

    public void SeleccionarAuto(JTable tabla_autos, 
                                JTextField auto, 
                                JTextField costo) {

        SwingUtilities.invokeLater(() -> {
            int fila = tabla_autos.getSelectedRow();
            if (fila == -1) return; 

            try {
                // Paso 1: Obtener el ID del vehículo (Columna 0)
                Object idVehiculoObj = tabla_autos.getValueAt(fila, 0);
                
                // Convertir a String y luego a INT para validar que es un número
                // y que se puede enviar como INT al DAO (aunque lo guardemos como String en el JTextField)
                int idVehiculoInt = Integer.parseInt(idVehiculoObj.toString());
                
                // Lo asignamos al JTextField 'auto' como String
                String vehiculoIdStr = String.valueOf(idVehiculoInt); 

                // Paso 2: Obtener Costo y Estado (asumiendo índices 3 y 4)
                String costoDia = tabla_autos.getValueAt(fila, 3).toString();
                String estadoVehiculo = tabla_autos.getValueAt(fila, 4).toString();

                // Paso 3: Asignar a los campos del formulario
                auto.setText(vehiculoIdStr); // El ID ahora está validado como INT
                costo.setText(costoDia);
                
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, 
                    "Error: El ID del vehículo o el Costo no son números enteros válidos.", 
                    "Error de Conversión", 
                    JOptionPane.ERROR_MESSAGE);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, 
                    "Error al seleccionar datos del auto: " + e.getMessage(), 
                    "Error de Tabla", 
                    JOptionPane.ERROR_MESSAGE);
            }
        });
    }
}