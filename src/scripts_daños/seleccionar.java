package scripts_daños;

import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;

public class seleccionar {

    public void SeleccionarDano(JTable tabla,
                                JTextField txtDevolucion) {

        int fila = tabla.getSelectedRow();

        if (fila >= 0) {
            // Solo tomar la columna donde está el ID de la devolución
            txtDevolucion.setText(tabla.getValueAt(fila, 3).toString());
            //                   ^ columna donde está id_devolucion según tu mostrar
        } else {
            JOptionPane.showMessageDialog(null, "Debe seleccionar una fila.");
        }
    }
}
