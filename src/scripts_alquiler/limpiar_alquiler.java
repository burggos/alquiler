package scripts_alquiler;

import javax.swing.JComboBox;
import javax.swing.JTextField;

/**
 * Clase para limpiar los campos del formulario de Alquiler.
 * Recibe todos los componentes de entrada de datos.
 */
public class limpiar_alquiler {

    public void LimpiarCamposAlquiler(JTextField id,
                                      JTextField cliente,
                                      JTextField auto,
                                      JTextField costo,
                                      JTextField total,
                                      JComboBox<String> duracion) {

        // Limpieza de campos de texto
        id.setText("");
        cliente.setText("");
        auto.setText("");
        costo.setText(""); // El costo base se limpia
        total.setText(""); // El costo total se limpia

        // Restablecer los JComboBox a la primera opción (generalmente "Seleccione..." o valor por defecto)
        
        // JComboBox Duración
        if (duracion.getItemCount() > 0) {
            duracion.setSelectedIndex(0);
        }

    }
}