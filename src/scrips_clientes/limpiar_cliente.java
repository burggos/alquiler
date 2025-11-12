package scrips_clientes;

import javax.swing.JTextField;

/**
 * Clase para limpiar los campos del formulario de clientes.
 * Proyecto: Sistema de Alquiler de Vehículos "Rueda Libre"
 * Autor: Adaptado por Andrés Rodríguez
 */
public class limpiar_cliente {
    
    public void LimpiarCampos(JTextField txtid, 
                              JTextField txtcc, 
                              JTextField txtnombre, 
                              JTextField txtapellido, 
                              JTextField txtdireccion, 
                              JTextField txtlicencia, 
                              JTextField txttelefono) {
        
        txtid.setText("");
        txtcc.setText("");
        txtnombre.setText("");
        txtapellido.setText("");
        txtdireccion.setText("");
        txtlicencia.setText("");
        txttelefono.setText("");
    }
}

