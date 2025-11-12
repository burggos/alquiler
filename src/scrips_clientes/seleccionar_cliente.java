package scrips_clientes;

import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;

/**
 * Clase para seleccionar un cliente de la tabla y mostrar sus datos en los campos del formulario
 */
public class seleccionar_cliente {
    
    public void SeleccionarCliente(JTable tabla_clientes, 
                                   JTextField id_cliente, 
                                   JTextField cc, 
                                   JTextField nombre, 
                                   JTextField apellido, 
                                   JTextField direccion, 
                                   JTextField licencia, 
                                   JTextField telefono) {
        
        int fila = tabla_clientes.getSelectedRow();
        
        if (fila >= 0) {
            id_cliente.setText(tabla_clientes.getValueAt(fila, 0).toString());
            cc.setText(tabla_clientes.getValueAt(fila, 1).toString());
            nombre.setText(tabla_clientes.getValueAt(fila, 2).toString());
            apellido.setText(tabla_clientes.getValueAt(fila, 3).toString());
            direccion.setText(tabla_clientes.getValueAt(fila, 4).toString());
            licencia.setText(tabla_clientes.getValueAt(fila, 5).toString());
            telefono.setText(tabla_clientes.getValueAt(fila, 6).toString());
        } else {
            JOptionPane.showMessageDialog(null, "Debe seleccionar una fila.");
        }
    }
}
