package scripts_daños;

import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;

/**
 * Clase para seleccionar un daño de la tabla y mostrar sus datos
 * en los campos del formulario.
 * Proyecto: Sistema de Alquiler de Vehículos "Rueda Libre"
 * Autor: Adaptado por Andrés Rodríguez
 */
public class seleccionar {

    public void SeleccionarDano(JTable Tdaños,
                                JTextField txtid,
                                JTextField txtdescipcion,
                                JTextField txtcosto,
                                JTextField txtdevolucion) {

        int fila = Tdaños.getSelectedRow();

        if (fila >= 0) {
            txtid.setText(Tdaños.getValueAt(fila, 0).toString());
            txtdevolucion.setText(Tdaños.getValueAt(fila, 1).toString());
            txtdescipcion.setText(Tdaños.getValueAt(fila, 2).toString());
            txtcosto.setText(Tdaños.getValueAt(fila, 3).toString());
        } else {
            JOptionPane.showMessageDialog(null, "Debe seleccionar una fila de la tabla.");
        }
    }
}

