package scripts_daños;

import javax.swing.JTextField;

/**
 * Clase para limpiar los campos del formulario de daños.
 * Proyecto: Sistema de Alquiler de Vehículos "Rueda Libre"
 * Autor: Adaptado por Andrés Rodríguez
 */
public class Limpiar {
    
    public void LimpiarCampos(JTextField txtid,
                              JTextField txtdescipcion,
                              JTextField txtcosto,
                              JTextField txtdevolucion) {
        
        txtid.setText("");
        txtdescipcion.setText("");
        txtcosto.setText("");
        txtdevolucion.setText("");
    }
}

