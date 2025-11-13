package scrips_alquiler;

import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

/**
 * Clase para seleccionar un cliente desde la tabla de clientes
 * y mostrar los datos relevantes en el formulario de alquiler.
 * Proyecto: Sistema de Alquiler de Vehículos "Rueda Libre"
 * Autor: Sr. Gabriel
 */
public class seleccionar_alquiler_cliente {

    public void SeleccionarCliente(JTable tabla_clientes, JTextField cliente) {

        SwingUtilities.invokeLater(() -> {
            int fila = tabla_clientes.getSelectedRow();
            if (fila == -1) return; // Evita errores si no hay fila seleccionada

            // Obtener el nombre completo del cliente
            String nombreCompleto = tabla_clientes.getValueAt(fila, 1).toString();
            cliente.setText(nombreCompleto);
        });
    }
}

