package scripts_devolucion;

import static scripts_clientes.dbConnection.conectar;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.SQLException;
import javax.swing.JTable;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class mostrar_devolucion {

    public void MostrarAlquileres(JTable tabla) {

        Connection conn = null;
        Statement st = null;
        ResultSet rs = null;

        DefaultTableModel modelo = new DefaultTableModel();

        modelo.addColumn("ID Alquiler");
        modelo.addColumn("Cliente");
        modelo.addColumn("Vehículo");
        modelo.addColumn("Semanas");
        modelo.addColumn("Costo Total");
        modelo.addColumn("Estado Alquiler");
        modelo.addColumn("Estado Vehículo"); // SOLO PARA VERIFICAR

        tabla.setModel(modelo);

        // ******************************************************************
        //  FILTRAR SOLO LOS AUTOS QUE ESTÁN ALQUILADOS (vehiculo.estado = 'Alquilado')
        // ******************************************************************
        String sql =
            "SELECT a.id_alquiler, " +
            "       CONCAT(c.nombre, ' ', c.apellido) AS cliente, " +
            "       CONCAT(v.marca, ' ', v.modelo) AS vehiculo, " +
            "       a.duracion_semanas, " +
            "       a.costo_total, " +
            "       a.estado AS estado_alquiler, " +
            "       v.estado AS estado_vehiculo " +
            "FROM alquiler a " +
            "INNER JOIN cliente c ON a.id_cliente = c.id_cliente " +
            "INNER JOIN vehiculo v ON a.id_vehiculo = v.id_vehiculo " +
            "WHERE v.estado = 'Alquilado' " +    // 🟢 FILTRO IMPORTANTE
            "ORDER BY a.id_alquiler ASC";

        try {
            conn = conectar();
            st = conn.createStatement();
            rs = st.executeQuery(sql);

            while (rs.next()) {
                modelo.addRow(new Object[]{
                    rs.getInt("id_alquiler"),
                    rs.getString("cliente"),
                    rs.getString("vehiculo"),
                    rs.getInt("duracion_semanas"),
                    rs.getDouble("costo_total"),
                    rs.getString("estado_alquiler"),
                    rs.getString("estado_vehiculo")
                });
            }

            tabla.setModel(modelo);

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al mostrar datos:\n" + e.getMessage());
        } finally {
            try {
                if (rs != null) rs.close();
                if (st != null) st.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "Error al cerrar conexión:\n" + e.getMessage());
            }
        }
    }
}
