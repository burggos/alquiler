package scripts_clientes;

import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;

/**
 * Clase para seleccionar un cliente de la tabla y mostrar sus datos 
 * en los campos del formulario.
 * Proyecto: Sistema de Alquiler de Vehículos "Rueda Libre"
 * Autor: Adaptado por Andrés Rodríguez
 */
public class seleccionar_cliente {
    
    public void SeleccionarCliente(JTable tclientes, 
                                   JTextField txtid, 
                                   JTextField txtcc, 
                                   JTextField txtnombre, 
                                   JTextField txtapellido, 
                                   JTextField txtdireccion, 
                                   JTextField txtlicencia, 
                                   JTextField txttelefono) {
        
        int fila = tclientes.getSelectedRow();
        
        if (fila >= 0) {
            txtid.setText(tclientes.getValueAt(fila, 0).toString());
            txtcc.setText(tclientes.getValueAt(fila, 1).toString());
            txtnombre.setText(tclientes.getValueAt(fila, 2).toString());
            txtapellido.setText(tclientes.getValueAt(fila, 3).toString());
            txtdireccion.setText(tclientes.getValueAt(fila, 4).toString());
            txtlicencia.setText(tclientes.getValueAt(fila, 5).toString());
            txttelefono.setText(tclientes.getValueAt(fila, 6).toString());
        } else {
            JOptionPane.showMessageDialog(null, "Debe seleccionar una fila de la tabla.");
        }
    }
}
