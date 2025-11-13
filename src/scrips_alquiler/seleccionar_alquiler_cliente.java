package scrips_alquiler;

import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.JOptionPane; 

public class seleccionar_alquiler_cliente {

    public void SeleccionarCliente(JTable tabla_clientes, JTextField cliente) {

        SwingUtilities.invokeLater(() -> {
            int fila = tabla_clientes.getSelectedRow();
            if (fila == -1) return; 

            try {
                // Paso 1: Obtener el ID del cliente (Columna 0)
                Object idClienteObj = tabla_clientes.getValueAt(fila, 0);
                
                // Convertir a String y luego a INT para validar que es un número
                int idClienteInt = Integer.parseInt(idClienteObj.toString());
                
                // Lo asignamos al JTextField 'cliente' como String
                String idClienteStr = String.valueOf(idClienteInt); 
                
                // Paso 2: Asignar el ID (como texto) al campo del formulario
                cliente.setText(idClienteStr);
                
            } catch (NumberFormatException e) {
                 JOptionPane.showMessageDialog(null, 
                    "Error: El ID del cliente no es un número entero válido.", 
                    "Error de Conversión", 
                    JOptionPane.ERROR_MESSAGE);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, 
                    "Error al seleccionar el ID del cliente: " + e.getMessage(), 
                    "Error de Tabla", 
                    JOptionPane.ERROR_MESSAGE);
            }
        });
    }
}