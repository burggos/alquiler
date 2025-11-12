/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scrips_clientes;

import static scrips_clientes.dbConnection.conectar;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

/**
 *
 * @author gabri
 */
public class agregar {
    
    public void agregarUsuario(JTextField usuario, JComboBox rol, JRadioButton si, JRadioButton no) {
            Connection conn = null;
            PreparedStatement ps = null;

            try {
                conn = conectar();
                String consulta = "INSERT INTO usuarios(nombre_usuario, rol, activo) VALUES(?, ?, ?)";
                ps = conn.prepareStatement(consulta);


                if (usuario.getText().trim().isEmpty()) {
                    throw new Exception("El campo 'Usuario' no puede estar vacío.");
                }

                String rolSeleccionado = rol.getSelectedItem().toString();

                String seleccion;
                if (si.isSelected()) {
                    seleccion = "Si";
                } else if (no.isSelected()) {
                    seleccion = "No";
                } else {
                    throw new Exception("Debe seleccionar una opción (Sí o No).");
                }


                ps.setString(1, usuario.getText());
                ps.setString(2, rolSeleccionado);
                ps.setString(3, seleccion);

                ps.executeUpdate();
                JOptionPane.showMessageDialog(null, "Usuario guardado correctamente.");

            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Error al guardar: " + e.getMessage());
            } finally {
                try {
                    if (ps != null) ps.close();
                    if (conn != null) conn.close();
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null, "Error al cerrar conexión: " + ex.getMessage());
                }
            }
        }
    
}
