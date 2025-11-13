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
 * Clase para mostrar los datos necesarios en las tablas del formulario de alquiler.
 * Proyecto: Sistema de Alquiler de Vehículos "Rueda Libre"
 * Autor: Sr. Gabriel
 */
public class mostrar_alquiler {

    /**
     * Muestra los vehículos con información relevante para el alquiler
     */
public void MostrarVehiculos(JTable tabla_autos) {
    Connection conn = null;
    Statement st = null;
    ResultSet rs = null;

    DefaultTableModel modelo = new DefaultTableModel();
    modelo.addColumn("ID");
    modelo.addColumn("Vehículo");
    modelo.addColumn("Tipo");
    modelo.addColumn("Costo por Día");
    modelo.addColumn("Estado");

    tabla_autos.setModel(modelo);

    // ✅ Solo mostrar vehículos con estado "Disponible"
    String sql = "SELECT id_vehiculo, CONCAT(marca, ' ', modelo) AS vehiculo, tipo, costo_por_dia, estado "
               + "FROM vehiculo "
               + "WHERE estado = 'Disponible' "
               + "ORDER BY marca ASC";

    try {
        conn = conectar();
        st = conn.createStatement();
        rs = st.executeQuery(sql);

        while (rs.next()) {
            modelo.addRow(new Object[]{
                rs.getInt("id_vehiculo"),
                rs.getString("vehiculo"),
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
     * Muestra los clientes con la información necesaria
     */
    public void MostrarClientes(JTable tabla_clientes) {
        Connection conn = null;
        Statement st = null;
        ResultSet rs = null;

        DefaultTableModel modelo = new DefaultTableModel();
        modelo.addColumn("ID");
        modelo.addColumn("Nombre Completo");
        modelo.addColumn("Licencia");

        tabla_clientes.setModel(modelo);

        String sql = "SELECT id_cliente, CONCAT(nombre, ' ', apellido) AS nombre_completo, numero_licencia "
                   + "FROM cliente ORDER BY nombre ASC";

        try {
            conn = conectar();
            st = conn.createStatement();
            rs = st.executeQuery(sql);

            while (rs.next()) {
                modelo.addRow(new Object[]{
                    rs.getInt("id_cliente"),
                    rs.getString("nombre_completo"),
                    rs.getString("numero_licencia")
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
