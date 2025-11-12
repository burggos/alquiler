package scrips_autos;

import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import com.toedter.calendar.JYearChooser;
import javax.swing.JComboBox;

/**
 * Clase para seleccionar un vehículo de la tabla y mostrar sus datos en los campos del formulario
 */
public class seleccionar_auto {

    public void SeleccionarAuto(JTable tabla,
                                JTextField id,
                                JTextField placa,
                                JTextField marca,
                                JTextField modelo,
                                JYearChooser anio,
                                JComboBox<String> tipo,
                                JTextField costo_por_dia,
                                JComboBox<String> estado) {

        int fila = tabla.getSelectedRow();

        if (fila >= 0) {
            try {
                // Se asume que la JTable tiene las columnas en este orden:
                // 0:id_vehiculo, 1:placa, 2:marca, 3:modelo, 4:anio, 5:tipo, 6:costo_por_dia, 7:estado
                Object val;

                val = tabla.getValueAt(fila, 0);
                id.setText(val != null ? val.toString() : "");

                val = tabla.getValueAt(fila, 1);
                placa.setText(val != null ? val.toString() : "");

                val = tabla.getValueAt(fila, 2);
                marca.setText(val != null ? val.toString() : "");

                val = tabla.getValueAt(fila, 3);
                modelo.setText(val != null ? val.toString() : "");

                val = tabla.getValueAt(fila, 4);
                if (val != null && !val.toString().isEmpty()) {
                    try {
                        int year = Integer.parseInt(val.toString());
                        anio.setYear(year);
                    } catch (NumberFormatException nfe) {
                        JOptionPane.showMessageDialog(null, "Valor de año inválido en la tabla.");
                    }
                }

                val = tabla.getValueAt(fila, 5);
                if (val != null) tipo.setSelectedItem(val.toString());
                else tipo.setSelectedIndex(-1);

                val = tabla.getValueAt(fila, 6);
                costo_por_dia.setText(val != null ? val.toString() : "");

                val = tabla.getValueAt(fila, 7);
                if (val != null) estado.setSelectedItem(val.toString());
                else estado.setSelectedIndex(-1);

            } catch (ArrayIndexOutOfBoundsException aibe) {
                JOptionPane.showMessageDialog(null, "La tabla no tiene el número de columnas esperado.");
            }
        } else {
            JOptionPane.showMessageDialog(null, "Debe seleccionar una fila.");
        }
    }
}
