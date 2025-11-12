package scrips_autos;

import static scrips_clientes.dbConnection.conectar;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import com.toedter.calendar.JYearChooser;

/**
 * Clase para agregar nuevos vehículos a la base de datos
 */
public class agregar_auto {

    // Método para guardar un vehículo
    public void guardarVehiculo(JTextField placa,
                                JTextField marca,
                                JTextField modelo,
                                JYearChooser anio,
                                JComboBox<String> tipo,
                                JTextField costo_por_dia,
                                JComboBox<String> estado) {

        Connection conn = null;
        PreparedStatement ps = null;

        try {
            conn = conectar(); // Conexión a la base de datos rueda_libre

            String sql = "INSERT INTO vehiculo (placa, marca, modelo, anio, tipo, costo_por_dia, estado) "
                       + "VALUES (?, ?, ?, ?, ?, ?, ?)";

            ps = conn.prepareStatement(sql);

            // Validar campos
            if (placa.getText().trim().isEmpty() ||
                marca.getText().trim().isEmpty() ||
                modelo.getText().trim().isEmpty() ||
                costo_por_dia.getText().trim().isEmpty() ||
                tipo.getSelectedIndex() == -1 ||
                estado.getSelectedIndex() == -1) {
                throw new Exception("Por favor completa todos los campos obligatorios.");
            }

            // Obtener valores
            String placaTxt = placa.getText().trim();
            String marcaTxt = marca.getText().trim();
            String modeloTxt = modelo.getText().trim();
            int anioSeleccionado = anio.getYear();
            String tipoTxt = tipo.getSelectedItem().toString();
            double costoDia = Double.parseDouble(costo_por_dia.getText().trim());
            String estadoTxt = estado.getSelectedItem().toString();

            // 👇 Impresión para verificar que los datos sean correctos
            System.out.println("DEBUG → Placa: " + placaTxt);
            System.out.println("DEBUG → Marca: " + marcaTxt);
            System.out.println("DEBUG → Modelo: " + modeloTxt);
            System.out.println("DEBUG → Año: " + anioSeleccionado);
            System.out.println("DEBUG → Tipo: " + tipoTxt);
            System.out.println("DEBUG → Costo: " + costoDia);
            System.out.println("DEBUG → Estado: " + estadoTxt);

            // Asignar parámetros en el mismo orden del INSERT
            ps.setString(1, placaTxt);
            ps.setString(2, marcaTxt);
            ps.setString(3, modeloTxt);
            ps.setObject(4, anioSeleccionado, java.sql.Types.SMALLINT);
            ps.setString(5, tipoTxt);
            ps.setDouble(6, costoDia);
            ps.setString(7, estadoTxt);

            // Ejecutar
            ps.executeUpdate();
            JOptionPane.showMessageDialog(null, "Vehículo guardado correctamente.");


                        limpiarCampos(placa, marca, modelo, costo_por_dia, tipo, estado, anio);

                    } catch (NumberFormatException e) {
                        JOptionPane.showMessageDialog(null, "El campo 'Costo por Día' debe ser un número válido.");
                    } catch (Exception e) {
                        JOptionPane.showMessageDialog(null, "Error al guardar vehículo: " + e.getMessage());
                    } finally {
                        try {
                            if (ps != null) ps.close();
                            if (conn != null) conn.close();
                        } catch (SQLException ex) {
                            JOptionPane.showMessageDialog(null, "Error al cerrar conexión: " + ex.getMessage());
                        }
                    }
                }

                // Método para limpiar los campos del formulario después de guardar
                private void limpiarCampos(JTextField placa,
                                           JTextField marca,
                                           JTextField modelo,
                                           JTextField costo_por_dia,
                                           JComboBox<String> tipo,
                                           JComboBox<String> estado,
                                           JYearChooser anio) {

                    placa.setText("");
                    marca.setText("");
                    modelo.setText("");
                    costo_por_dia.setText("");
                    tipo.setSelectedIndex(0);
                    estado.setSelectedIndex(0);
                    anio.setYear(2025); // año por defecto o actual
                }
}
