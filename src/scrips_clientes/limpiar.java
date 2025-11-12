/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scrips_clientes;

import javax.swing.JComboBox;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

/**
 *
 * @author gabri
 */
public class limpiar {
            public void LimpiarCampos(JTextField id, JTextField usuario, JComboBox rol, JRadioButton si, JRadioButton no) {
           
            id.setText("");
            usuario.setText("");

            
            if (rol.getItemCount() > 0) {
                rol.setSelectedIndex(0);
            }

            
            if (si.isSelected()){
                si.setSelected(false);
            }else if(no.isSelected()){
                 no.setSelected(false);
            }
            
           
        }
}
