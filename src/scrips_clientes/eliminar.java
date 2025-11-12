    /*
     * To change this license header, choose License Headers in Project Properties.
     * To change this template file, choose Tools | Templates
     * and open the template in the editor.
     */
    package scrips_clientes;

    import java.sql.CallableStatement;
    import java.sql.Connection;
    import java.sql.SQLException;
    import javax.swing.JOptionPane;
    import javax.swing.JTextField;

    /**
     *
     * @author gabri
     */
    public class eliminar {
                public void EliminarUsuario(JTextField id) {
                dbConnection objetoConexion = new dbConnection();
                Connection conn = null;
                CallableStatement cs = null;

                try {

                    if (id.getText().trim().isEmpty()) {
                        JOptionPane.showMessageDialog(null, "Debe seleccionar un usuario para eliminar.");
                        return;
                    }


                    conn = objetoConexion.conectar();

                    String consulta = "DELETE FROM usuarios WHERE id_usuario = ?";
                    cs = conn.prepareCall(consulta);
                    cs.setInt(1, Integer.parseInt(id.getText()));

                    int filasAfectadas = cs.executeUpdate();

                    if (filasAfectadas > 0) {
                        JOptionPane.showMessageDialog(null, "Usuario eliminado correctamente.");
                    } else {
                        JOptionPane.showMessageDialog(null, "No se encontró un usuario con ese ID.");
                    }

                } catch (NumberFormatException nfe) {
                    JOptionPane.showMessageDialog(null, "El ID debe ser un número válido.");
                } catch (SQLException e) {
                    JOptionPane.showMessageDialog(null, "Error al eliminar usuario: " + e.getMessage());
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, "Error inesperado: " + e.getMessage());
                } finally {
                    try {
                        if (cs != null) cs.close();
                        objetoConexion.cerrarConexion();
                    } catch (SQLException ex) {
                        JOptionPane.showMessageDialog(null, "⚠️ Error al cerrar conexión: " + ex.getMessage());
                    }
                }
            }
    }

