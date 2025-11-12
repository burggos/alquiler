package scrips_autos;

import static scrips_clientes.dbConnection.conectar;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 * Clase para mostrar los vehículos registrados en la base de datos
 * Proyecto: Sistema de Alquiler de Vehículos "Rueda Libre"
 * Autor: Sr. Gabriel
 */
public class mostrar_auto {

    public void MostrarVehiculos(JTable tablaVehiculos) {
        Connection conn = null;
        Statement st = null;
        ResultSet rs = null;

        // Modelo de la tabla para mostrar vehículos
        DefaultTableModel modelo = new DefaultTableModel();
        modelo.addColumn("ID");
        modelo.addColumn("Placa");
        modelo.addColumn("Marca");
        modelo.addColumn("Modelo");
        modelo.addColumn("Año");
        modelo.addColumn("Tipo");
        modelo.addColumn("Costo por Día");
        modelo.addColumn("Estado");

        tablaVehiculos.setModel(modelo);

        // Consulta SQL para obtener los vehículos
        String sql = "SELECT id_vehiculo, placa, marca, modelo, anio, tipo, costo_por_dia, estado "
                   + "FROM vehiculo ORDER BY id_vehiculo ASC";

        try {
            conn = conectar(); // Conexión desde tu clase dbConnection
            st = conn.createStatement();
            rs = st.executeQuery(sql);

            // Llenar la tabla con los resultados
            while (rs.next()) {
                int id = rs.getInt("id_vehiculo");
                String placa = rs.getString("placa");
                String marca = rs.getString("marca");
                String modeloVeh = rs.getString("modelo");

                // ✅ Corrección: obtener año como entero, no como string
                int anio = rs.getInt("anio");

                String tipo = rs.getString("tipo");
                double costo = rs.getDouble("costo_por_dia");
                String estado = rs.getString("estado");

                modelo.addRow(new Object[]{id, placa, marca, modeloVeh, anio, tipo, costo, estado});
            }

            tablaVehiculos.setModel(modelo);

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null,
                "Error al mostrar vehículos:\n" + e.getMessage(),
                "Error de conexión", JOptionPane.ERROR_MESSAGE);
        } finally {
            // Cerrar recursos correctamente
            try {
                if (rs != null) rs.close();
                if (st != null) st.close();
                if (conn != null) conn.close();
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null,
                    "Error al cerrar conexión:\n" + ex.getMessage());
            }
        }
    }
}
