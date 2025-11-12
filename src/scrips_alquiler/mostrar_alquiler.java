package scrips_alquiler;

import static scrips_clientes.dbConnection.conectar;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 * Clase para mostrar clientes y vehículos en las tablas del formulario principal
 * Proyecto: Sistema de Alquiler de Vehículos "Rueda Libre"
 * Autor: Sr. Gabriel
 */
public class mostrar_alquiler {

    /**
     * Muestra los vehículos disponibles en la tabla_autos
     */
    public void MostrarVehiculos(JTable tabla_autos) {
        Connection conn = null;
        Statement st = null;
        ResultSet rs = null;

        DefaultTableModel modelo = new DefaultTableModel();
        modelo.addColumn("ID");
        modelo.addColumn("Placa");
        modelo.addColumn("Marca");
        modelo.addColumn("Modelo");
        modelo.addColumn("Año");
        modelo.addColumn("Tipo");
        modelo.addColumn("Costo por Día");
        modelo.addColumn("Estado");

        tabla_autos.setModel(modelo);

        String sql = "SELECT id_vehiculo, placa, marca, modelo, anio, tipo, costo_por_dia, estado "
                   + "FROM vehiculo ORDER BY id_vehiculo ASC";

        try {
            conn = conectar();
            st = conn.createStatement();
            rs = st.executeQuery(sql);

            while (rs.next()) {
                modelo.addRow(new Object[]{
                    rs.getInt("id_vehiculo"),
                    rs.getString("placa"),
                    rs.getString("marca"),
                    rs.getString("modelo"),
                    rs.getInt("anio"),
                    rs.getString("tipo"),
                    rs.getDouble("costo_por_dia"),
                    rs.getString("estado")
                });
            }

            tabla_autos.setModel(modelo);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al mostrar vehículos: " + e.getMessage());
        } finally {
            try {
                if (rs != null) rs.close();
                if (st != null) st.close();
                if (conn != null) conn.close();
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "Error al cerrar conexión: " + ex.getMessage());
            }
        }
    }

    /**
     * Muestra los clientes registrados en la tabla_clientes
     */
    public void MostrarClientes(JTable tabla_clientes) {
        Connection conn = null;
        Statement st = null;
        ResultSet rs = null;

        DefaultTableModel modelo = new DefaultTableModel();
        modelo.addColumn("ID");
        modelo.addColumn("Cédula");
        modelo.addColumn("Nombre");
        modelo.addColumn("Apellido");
        modelo.addColumn("Dirección");
        modelo.addColumn("Licencia");
        modelo.addColumn("Teléfono");

        tabla_clientes.setModel(modelo);

        String sql = "SELECT id_cliente, cc, nombre, apellido, direccion, numero_licencia, telefono "
                   + "FROM cliente ORDER BY id_cliente ASC";

        try {
            conn = conectar();
            st = conn.createStatement();
            rs = st.executeQuery(sql);

            while (rs.next()) {
                modelo.addRow(new Object[]{
                    rs.getInt("id_cliente"),
                    rs.getString("cc"),
                    rs.getString("nombre"),
                    rs.getString("apellido"),
                    rs.getString("direccion"),
                    rs.getString("numero_licencia"),
                    rs.getString("telefono")
                });
            }

            tabla_clientes.setModel(modelo);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al mostrar clientes: " + e.getMessage());
        } finally {
            try {
                if (rs != null) rs.close();
                if (st != null) st.close();
                if (conn != null) conn.close();
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "Error al cerrar conexión: " + ex.getMessage());
            }
        }
    }
}
