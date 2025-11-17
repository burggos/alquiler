package scripts_daños;

import static scripts_clientes.dbConnection.conectar;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JTable;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class mostrar {
    
    public void MostrarDanos(JTable tabla) {
        Connection conn = null;
        Statement st = null;
        ResultSet rs = null;

        // Columnas incluyendo la observación de la devolución
        DefaultTableModel modelo = new DefaultTableModel();
        modelo.addColumn("ID Vehículo");
        modelo.addColumn("Vehículo");
        modelo.addColumn("Estado Vehículo");
        modelo.addColumn("ID Devolución");
        modelo.addColumn("Cliente");
        modelo.addColumn("Observación Dev.");
        tabla.setModel(modelo);

        // Consulta SQL con Observaciones
        String sql =
            "SELECT v.id_vehiculo, " +
            "       CONCAT(v.marca, ' ', v.modelo) AS vehiculo, " +
            "       v.estado, " +
            "       d.id_devolucion, " +
            "       CONCAT(c.nombre, ' ', c.apellido) AS cliente, " +
            "       d.observaciones, " +
            "       da.descripcion, " +
            "       da.costo_reparacion " +
            "FROM vehiculo v " +
            "LEFT JOIN alquiler a ON v.id_vehiculo = a.id_vehiculo " +
            "LEFT JOIN devolucion d ON a.id_alquiler = d.id_alquiler " +
            "LEFT JOIN dano da ON d.id_devolucion = da.id_devolucion " +
            "LEFT JOIN cliente c ON a.id_cliente = c.id_cliente " +
            "WHERE v.estado IN ('Mantenimiento', 'Dañado') " +
            "ORDER BY v.id_vehiculo ASC";

        try {
            conn = conectar();
            st = conn.createStatement();
            rs = st.executeQuery(sql);

            while (rs.next()) {
                modelo.addRow(new Object[]{
                    rs.getInt("id_vehiculo"),
                    rs.getString("vehiculo"),
                    rs.getString("estado"),
                    rs.getString("id_devolucion"),
                    rs.getString("cliente"),
                    rs.getString("observaciones"),
                    rs.getString("descripcion"),
                    rs.getString("costo_reparacion")
                });
            }

            tabla.setModel(modelo);

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null,
                "Error al mostrar vehículos con daños o mantenimiento:\n" + e.getMessage(),
                "Error SQL",
                JOptionPane.ERROR_MESSAGE);
        } finally {
            try {
                if (rs != null) rs.close();
                if (st != null) st.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null,
                    "Error al cerrar conexión:\n" + e.getMessage());
            }
        }
    }
}
