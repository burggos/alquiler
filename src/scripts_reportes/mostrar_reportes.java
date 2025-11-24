package scripts_reportes;

import static scripts_clientes.dbConnection.conectar;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.SQLException;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;

public class mostrar_reportes {

    public void MostrarReporte(JComboBox<String> combo, JTable tabla) {

        String opcion = combo.getSelectedItem().toString();
        String sql = "";

        // =============================
        // 1️⃣ Seleccionar la consulta SQL
        // =============================
        switch (opcion) {

            case "Vehículos más alquilados por modelo":
                sql = "SELECT CONCAT(v.marca, ' ', v.modelo) AS vehiculo, "
                    + "COUNT(a.id_alquiler) AS veces_alquilado "
                    + "FROM alquiler a "
                    + "INNER JOIN vehiculo v ON a.id_vehiculo = v.id_vehiculo "
                    + "GROUP BY v.id_vehiculo "
                    + "ORDER BY veces_alquilado DESC";
                break;

            case "Ingresos totales por mes":
                sql = "SELECT DATE_FORMAT(fecha_alquiler, '%Y-%m') AS mes, "
                    + "SUM(costo_total) AS ingresos "
                    + "FROM alquiler "
                    + "GROUP BY mes "
                    + "ORDER BY mes ASC";
                break;

            case "Vehículos que requieren mantenimiento":
                sql = "SELECT id_vehiculo, CONCAT(marca,' ',modelo) AS vehiculo, estado "
                    + "FROM vehiculo "
                    + "WHERE estado = 'Mantenimiento'";
                break;

            case "Vehículos disponibles":
                sql = "SELECT id_vehiculo, "
                    + "CONCAT(marca,' ',modelo) AS vehiculo, "
                    + "tipo, "
                    + "costo_por_dia, "
                    + "estado "
                    + "FROM vehiculo "
                    + "WHERE estado = 'Disponible' "
                    + "ORDER BY marca ASC";
                break;

            case "Lista de alquileres activos y vencidos":
                sql = "SELECT id_alquiler, id_cliente, id_vehiculo, duracion_semanas, costo_total, estado "
                    + "FROM alquiler "
                    + "WHERE estado IN ('Activo','Vencido')";
                break;

            case "Distribución de vehículos por tipo":
                sql = "SELECT tipo, COUNT(*) AS cantidad "
                    + "FROM vehiculo "
                    + "GROUP BY tipo";
                break;

            case "Ingresos por tipo de vehículo":
                sql = "SELECT v.tipo, SUM(a.costo_total) AS ingresos "
                    + "FROM alquiler a "
                    + "INNER JOIN vehiculo v ON a.id_vehiculo = v.id_vehiculo "
                    + "GROUP BY v.tipo";
                break;

            case "Historial de alquileres por cliente":
                sql = "SELECT a.id_alquiler, CONCAT(c.nombre,' ',c.apellido) AS cliente, "
                    + "CONCAT(v.marca,' ',v.modelo) AS vehiculo, "
                    + "a.duracion_semanas, a.costo_total, a.estado "
                    + "FROM alquiler a "
                    + "INNER JOIN cliente c ON a.id_cliente = c.id_cliente "
                    + "INNER JOIN vehiculo v ON a.id_vehiculo = v.id_vehiculo";
                break;

            case "Reporte de daños en devoluciones":
                sql = "SELECT d.id_devolucion, da.descripcion, da.costo_reparacion, "
                    + "CONCAT(v.marca,' ',v.modelo) AS vehiculo "
                    + "FROM dano da "
                    + "INNER JOIN devolucion d ON da.id_devolucion = d.id_devolucion "
                    + "INNER JOIN alquiler a ON d.id_alquiler = a.id_alquiler "
                    + "INNER JOIN vehiculo v ON a.id_vehiculo = v.id_vehiculo";
                break;

            case "Ingresos proyectados del próximo trimestre":
                sql = "SELECT SUM(costo_total) * 1.15 AS proyeccion_trimestre "
                    + "FROM alquiler";
                break;

            default:
                JOptionPane.showMessageDialog(null, "No hay consulta asignada.");
                return;
        }

        // =============================
        // 2️⃣ Ejecutar la consulta y llenar la tabla
        // =============================
        Connection conn = null;
        Statement st = null;
        ResultSet rs = null;

        try {
            conn = conectar();
            st = conn.createStatement();
            rs = st.executeQuery(sql);

            // Crear modelo dinámico según columnas
            DefaultTableModel modelo = new DefaultTableModel();

            int columnas = rs.getMetaData().getColumnCount();
            for (int i = 1; i <= columnas; i++) {
                modelo.addColumn(rs.getMetaData().getColumnLabel(i));
            }

            // Agregar filas
            while (rs.next()) {
                Object[] fila = new Object[columnas];
                for (int i = 1; i <= columnas; i++) {
                    fila[i - 1] = rs.getString(i);
                }
                modelo.addRow(fila);
            }

            tabla.setModel(modelo);

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al generar reporte:\n" + e.getMessage());

        } finally {
            try {
                if (rs != null) rs.close();
                if (st != null) st.close();
                if (conn != null) conn.close();
            } catch (SQLException ex) { }
        }
    }
}
