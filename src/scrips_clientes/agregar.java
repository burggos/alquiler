package scrips_clientes;

import static scrips_clientes.dbConnection.conectar;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class agregar {

    public void agregarCliente(JTextField txtid, JTextField txtnombre, JTextField txtapellido, 
                               JTextField txtcc, JTextField txttelefono, 
                               JTextField txtdireccion, JTextField txtlicencia) {
        Connection conn = null;
        PreparedStatement ps = null;

        try {
            conn = conectar();
            String consulta = "INSERT INTO cliente (cc, nombre, apellido, direccion, numero_licencia, telefono) VALUES (?, ?, ?, ?, ?, ?)";
            ps = conn.prepareStatement(consulta);

            // Validaciones básicas
            if (txtcc.getText().trim().isEmpty() || txtnombre.getText().trim().isEmpty() || 
                txtapellido.getText().trim().isEmpty() || txtlicencia.getText().trim().isEmpty()) {
                throw new Exception("Por favor, completa todos los campos obligatorios: CC, Nombre, Apellido y Licencia.");
            }

            // Asignación de valores al INSERT
            ps.setString(1, txtcc.getText().trim());
            ps.setString(2, txtnombre.getText().trim());
            ps.setString(3, txtapellido.getText().trim());
            ps.setString(4, txtdireccion.getText().trim());
            ps.setString(5, txtlicencia.getText().trim());
            ps.setString(6, txttelefono.getText().trim());

            ps.executeUpdate();
            JOptionPane.showMessageDialog(null, "Cliente agregado correctamente.");

            // Limpia los campos del formulario
            txtid.setText("");
            txtcc.setText("");
            txtnombre.setText("");
            txtapellido.setText("");
            txtdireccion.setText("");
            txtlicencia.setText("");
            txttelefono.setText("");

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al guardar cliente: " + e.getMessage());
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

