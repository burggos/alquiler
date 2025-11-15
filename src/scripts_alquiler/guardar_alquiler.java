package scripts_alquiler;

import static scripts_clientes.dbConnection.conectar;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class guardar_alquiler {

    // Consulta para registrar el nuevo alquiler
    private static final String SQL_INSERT_ALQUILER = 
        "INSERT INTO alquiler (id_cliente, id_vehiculo, duracion_semanas, costo_total, estado) VALUES (?, ?, ?, ?, ?)";
    
    // Consulta para cambiar el estado del vehículo a 'Alquilado'
    private static final String SQL_UPDATE_VEHICULO =
        "UPDATE vehiculo SET estado = 'Alquilado' WHERE id_vehiculo = ?";

    
    public void guardarAlquiler(JTextField cliente,
                                JTextField auto,
                                JComboBox<String> duracion,
                                JTextField total,
                                JComboBox<String> estado) {

        Connection conn = null;
        PreparedStatement psAlquiler = null;
        PreparedStatement psVehiculo = null;
        
        // Variables para almacenar los valores convertidos
        int idCliente;
        int idVehiculo;
        int duracionSemanas;
        double costoTotal;
        String estadoTxt;

        try {
            // =============================================================
            // PASO 1: Validación y Conversión de Valores
            // =============================================================
            // Nota: Este código de validación es el mismo que definimos en el paso anterior.
            // Se mantiene para garantizar que no haya errores de formato o campos vacíos.
            
            String clienteText = cliente.getText().trim();
            if (clienteText.isEmpty()) throw new Exception("Debe seleccionar un Cliente.");
            idCliente = Integer.parseInt(clienteText);
            
            String autoText = auto.getText().trim();
            if (autoText.isEmpty()) throw new Exception("Debe seleccionar un Vehículo.");
            idVehiculo = Integer.parseInt(autoText);
            
            String totalText = total.getText().trim().replace(",", ".");
            if (totalText.isEmpty() || totalText.equals("Error en Costo")) throw new Exception("El Costo Total no puede estar vacío o tener un error.");
            costoTotal = Double.parseDouble(totalText);
            
            String duracionSeleccionada = (String) duracion.getSelectedItem();
            if (duracionSeleccionada == null || duracionSeleccionada.isEmpty() || duracion.getSelectedIndex() <= 0) {
                 throw new Exception("Debe seleccionar una Duración válida.");
            }
            duracionSemanas = Integer.parseInt(duracionSeleccionada.split(" ")[0]);
            
            estadoTxt = ((String) estado.getSelectedItem()).trim();
            // Validamos que el estado del alquiler sea válido (Activo/Vencido/Finalizado)
            if (!(estadoTxt.equalsIgnoreCase("Activo") || 
                  estadoTxt.equalsIgnoreCase("Vencido") || 
                  estadoTxt.equalsIgnoreCase("Finalizado"))) {
                 throw new Exception("Debe seleccionar un Estado de Alquiler válido.");
            }
            
            // =============================================================
            // PASO 2: Inserción en Transacción
            // =============================================================
            
            conn = conectar();
            // Deshabilitar AutoCommit para iniciar la transacción
            conn.setAutoCommit(false); 

            // 1. Ejecutar INSERT en la tabla 'alquiler'
            psAlquiler = conn.prepareStatement(SQL_INSERT_ALQUILER);
            psAlquiler.setInt(1, idCliente);
            psAlquiler.setInt(2, idVehiculo);
            psAlquiler.setInt(3, duracionSemanas);
            psAlquiler.setDouble(4, costoTotal);
            psAlquiler.setString(5, estadoTxt);
            psAlquiler.executeUpdate();
            
            // 2. Ejecutar UPDATE en la tabla 'vehiculo'
            psVehiculo = conn.prepareStatement(SQL_UPDATE_VEHICULO);
            psVehiculo.setInt(1, idVehiculo);
            int filasActualizadas = psVehiculo.executeUpdate();
            
            if (filasActualizadas == 0) {
                // Si por alguna razón no se actualizó el vehículo (ej. ya estaba 'Alquilado' o no existe el ID)
                // Lanzamos una excepción para forzar el ROLLBACK
                throw new Exception("Advertencia: No se pudo actualizar el estado del vehículo. Revise si el ID existe.");
            }
            
            // 3. Confirmar la Transacción (COMMIT)
            conn.commit(); 
            
            JOptionPane.showMessageDialog(null, "Alquiler registrado y Vehículo marcado como 'Alquilado'.");

            // Opcional: Limpiar los campos
            limpiarCampos(cliente, auto, total); 

        } catch (Exception e) {
            // Si ocurre cualquier error (SQL, Formato, o validación de filasActualizadas)
            try {
                if (conn != null) {
                    conn.rollback(); // Deshacer todas las operaciones
                }
            } catch (SQLException ex) {
                // Error al intentar deshacer
                System.err.println("Error al realizar rollback: " + ex.getMessage());
            }
            
            JOptionPane.showMessageDialog(null, "Error al guardar el alquiler (Transacción deshecha): " + e.getMessage(), 
                                                "Error de Inserción", JOptionPane.ERROR_MESSAGE);
            
        } finally {
            // 4. CERRAR RECURSOS y restaurar AutoCommit
            try {
                if (psAlquiler != null) psAlquiler.close();
                if (psVehiculo != null) psVehiculo.close();
                if (conn != null) {
                    conn.setAutoCommit(true); // Restaurar al modo normal
                    conn.close();
                }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "Error al cerrar recursos: " + ex.getMessage());
            }
        }
    }

    private void limpiarCampos(JTextField cliente, JTextField auto, JTextField total) {
        cliente.setText("");
        auto.setText("");
        total.setText("");
    }
}