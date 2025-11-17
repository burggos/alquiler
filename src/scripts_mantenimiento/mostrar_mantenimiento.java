package scripts_mantenimiento;

import static scripts_clientes.dbConnection.conectar;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.SQLException;
import javax.swing.JTable;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class mostrar_mantenimiento {

    public void MostrarVehiculos(JTable tabla) {

        Connection conn = null;
        Statement st = null;
        ResultSet rs = null;

        DefaultTableModel modelo = new DefaultTableModel();

        modelo.addColumn("ID Vehículo");
        modelo.addColumn("Vehículo");
        modelo.addColumn("Estado Vehículo");
        modelo.addColumn("ID Devolución");
        modelo.addColumn("Cliente");
        modelo.addColumn("Costo Daño");

        tabla.setModel(modelo);

        // Mostrar solo autos con daño que requieran mantenimiento REAL
        String sql =
            "SELECT v.id_vehiculo, " +
            "       CONCAT(v.marca, ' ', v.modelo) AS vehiculo, " +
            "       v.estado, " +
            "       d.id_devolucion, " +
            "       CONCAT(c.nombre, ' ', c.apellido) AS cliente, " +
            "       da.costo_reparacion " +
            "FROM vehiculo v " +
            "INNER JOIN alquiler a ON v.id_vehiculo = a.id_vehiculo " +
            "INNER JOIN devolucion d ON a.id_alquiler = d.id_alquiler " +
            "INNER JOIN dano da ON d.id_devolucion = da.id_devolucion " +
            "INNER JOIN cliente c ON a.id_cliente = c.id_cliente " +
            "WHERE v.estado IN ('Dañado', 'Mantenimiento') " + // Solo autos dañados o en mantenimiento
            "  AND da.costo_reparacion > 0 " +                // Solo si el daño tiene costo
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
                    rs.getString("costo_reparacion")
                });
            }

            tabla.setModel(modelo);

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null,
                "Error al mostrar vehículos con daños:\n" + e.getMessage());
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
