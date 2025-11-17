package scripts_daños;

import static scripts_clientes.dbConnection.conectar;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class guardar {

    public void agregarDano(JTextField txtid, JTextField txtdevolucion, 
                            JTextField txtdescipcion, JTextField txtcosto) {

        Connection conn = null;
        PreparedStatement ps = null;

        try {
            conn = conectar();

            // Consulta SQL para insertar daño
            String consulta = "INSERT INTO dano (id_devolucion, descripcion, costo_reparacion) VALUES (?, ?, ?)";
            ps = conn.prepareStatement(consulta);

            // Validaciones básicas
            if (txtdevolucion.getText().trim().isEmpty() || 
                txtdescipcion.getText().trim().isEmpty() ||
                txtcosto.getText().trim().isEmpty()) {

                throw new Exception("Por favor, completa todos los campos: Devolución, Descripción y Costo.");
            }

            // Conversión numérica segura
            int idDevolucion;
            double costo;

            try {
                idDevolucion = Integer.parseInt(txtdevolucion.getText().trim());
            } catch (Exception e) {
                throw new Exception("El campo ID Devolución debe ser un número entero.");
            }

            try {
                costo = Double.parseDouble(txtcosto.getText().trim());
            } catch (Exception e) {
                throw new Exception("El campo Costo debe ser un número válido.");
            }

            // Asignación de valores
            ps.setInt(1, idDevolucion);
            ps.setString(2, txtdescipcion.getText().trim());
            ps.setDouble(3, costo);

            ps.executeUpdate();
            JOptionPane.showMessageDialog(null, "Daño registrado correctamente.");

            // Limpiar los campos
            txtid.setText("");
            txtdevolucion.setText("");
            txtdescipcion.setText("");
            txtcosto.setText("");

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al registrar daño: " + e.getMessage());
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

