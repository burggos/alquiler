package scripts_autos;

import javax.swing.JComboBox;
import javax.swing.JTextField;
import com.toedter.calendar.JYearChooser;

/**
 * Clase para limpiar los campos del formulario de vehículos
 * Proyecto: Sistema de Alquiler de Vehículos "Rueda Libre"
 * Autor: Sr. Gabriel
 */
public class limpiar_auto {

    public void LimpiarCampos(JTextField id,
                              JTextField placa,
                              JTextField marca,
                              JTextField modelo,
                              JYearChooser anio,
                              JComboBox<String> tipo,
                              JTextField costo_por_dia,
                              JComboBox<String> estado) {

        // Limpieza de campos de texto
        id.setText("");
        placa.setText("");
        marca.setText("");
        modelo.setText("");
        costo_por_dia.setText("");

        // Restablecer año al actual
        anio.setYear(java.time.Year.now().getValue());

        // Restablecer los JComboBox si tienen elementos
        if (tipo.getItemCount() > 0) {
            tipo.setSelectedIndex(0);
        }

        if (estado.getItemCount() > 0) {
            estado.setSelectedIndex(0);
        }
    }
}
