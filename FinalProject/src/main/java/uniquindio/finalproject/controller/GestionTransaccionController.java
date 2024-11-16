package uniquindio.finalproject.controller;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import uniquindio.finalproject.Model.Transaccion;
import uniquindio.finalproject.Model.TipoTransaccion;
import uniquindio.finalproject.Model.Cuenta;
import uniquindio.finalproject.Model.Categoria;
import uniquindio.finalproject.exceptions.CuentaNoAsociadaException;
import uniquindio.finalproject.exceptions.MontoInvalidoException;
import uniquindio.finalproject.global.SesionGlobal;

import java.io.IOException;
import java.time.LocalDate;

import static uniquindio.finalproject.global.SesionGlobal.usuarioActual;

public class GestionTransaccionController {

    @FXML
    private Button btnEnviarDinero;

    @FXML
    private Button btnTablaDeTransacciones;

    @FXML
    private MenuButton btnTipoTransaccion;

    @FXML
    private TextField txtDescripcionCategoria;

    @FXML
    private TextField txtDescripcionTransaccion;

    @FXML
    private DatePicker txtFecha;

    @FXML
    private TextField txtIdCategoria;

    @FXML
    private TextField txtIdCuenta;

    @FXML
    private TextField txtIdCuentaOrigen;

    @FXML
    private TextField txtIdTransaccion;

    @FXML
    private TextField txtMonto;

    @FXML
    private TextField txtNombreBanco;

    @FXML
    private TextField txtNombreBancoOrigen;

    @FXML
    private TextField txtNombreCategoria;

    @FXML
    private TextField txtNumeroCuenta;

    @FXML
    private TextField txtNumeroCuentaOrigen;

    @FXML
    private TextField txtUsuario;

    @FXML
    public void initialize() {
        for (TipoTransaccion tipo : TipoTransaccion.values()) {
            MenuItem item = new MenuItem(tipo.toString());
            item.setOnAction(e -> btnTipoTransaccion.setText(tipo.toString()));
            btnTipoTransaccion.getItems().add(item);
        }
    }

    // Método para enviar (crear) una nueva transacción
    @FXML
    void ClickEnviarDinero(ActionEvent event) {

        try {
            // Obtención de datos de los campos
            String idTransaccion = txtIdTransaccion.getText();
            LocalDate fecha = txtFecha.getValue();
            String tipoTransaccionStr = btnTipoTransaccion.getText();
            String descripcion = txtDescripcionTransaccion.getText();
            String idCuentaOrigen = txtIdCuentaOrigen.getText();
            String idCuentaDestino = txtIdCuenta.getText();
            String idCategoria = txtIdCategoria.getText();

            // Validación de campos vacíos
            if (idTransaccion.isEmpty() || tipoTransaccionStr.equals("Seleccione tipo de transacción") || descripcion.isEmpty()) {
                mostrarMensaje("Error", "Campos Vacíos", "Por favor llene todos los campos correctamente", Alert.AlertType.ERROR);
                return;
            }

            // Validación del monto
            Double monto;
            try {
                monto = Double.parseDouble(txtMonto.getText());
            } catch (NumberFormatException e) {
                throw new MontoInvalidoException("El monto debe ser un número válido.");
            }

            if (monto <= 0) {
                throw new MontoInvalidoException("El monto ingresado no es válido. Debe ser mayor a 0.");
            }

            // Validación de cuentas asociadas
            Cuenta cuentaOrigen = usuarioActual.getCuentaPorId(idCuentaOrigen);
            Cuenta cuentaDestino = usuarioActual.getCuentaPorId(idCuentaDestino);

            if (cuentaOrigen == null || !usuarioActual.getCuentasAsociadas().contains(cuentaOrigen)) {
                throw new CuentaNoAsociadaException("La cuenta origen con ID " + idCuentaOrigen + " no está asociada al usuario actual.");
            }

            if (cuentaDestino == null || !usuarioActual.getCuentasAsociadas().contains(cuentaDestino)) {
                throw new CuentaNoAsociadaException("La cuenta destino con ID " + idCuentaDestino + " no está asociada al usuario actual.");
            }

            // Crear la transacción
            TipoTransaccion tipoTransaccion = TipoTransaccion.valueOf(tipoTransaccionStr);
            Categoria categoria = usuarioActual.getCategoriaPorId(idCategoria);
            Transaccion nuevaTransaccion = new Transaccion(idTransaccion, fecha, tipoTransaccion, monto, descripcion, cuentaOrigen, cuentaDestino, categoria);

            // Añadir la transacción al usuario
            usuarioActual.añadirTransaccion(nuevaTransaccion);

            // Limpiar campos y mostrar mensaje de éxito
            limpiarCampos();
            mostrarMensaje("Éxito", "Transacción Enviada", "La transacción ha sido registrada correctamente", Alert.AlertType.INFORMATION);

        } catch (MontoInvalidoException e) {
            mostrarMensaje("Error", "Monto Inválido", e.getMessage(), Alert.AlertType.ERROR);
        } catch (CuentaNoAsociadaException e) {
            mostrarMensaje("Error", "Cuenta No Asociada", e.getMessage(), Alert.AlertType.ERROR);
        } catch (Exception e) {
            mostrarMensaje("Error", "Error inesperado", "Ocurrió un error al procesar la transacción.", Alert.AlertType.ERROR);
            e.printStackTrace();
        }
    }

    // Método para limpiar los campos de texto
    private void limpiarCampos() {
        txtIdTransaccion.clear();
        txtFecha.setValue(null);
        txtMonto.clear();
        txtDescripcionTransaccion.clear();
        txtIdCuentaOrigen.clear();
        txtIdCuenta.clear();
        txtIdCategoria.clear();
        btnTipoTransaccion.setText("Seleccione tipo de transacción");
    }

    // Método para mostrar mensajes de alerta
    private void mostrarMensaje(String titulo, String encabezado, String contenido, Alert.AlertType tipo) {
        Alert alerta = new Alert(tipo);
        alerta.setTitle(titulo);
        alerta.setHeaderText(encabezado);
        alerta.setContentText(contenido);
        alerta.showAndWait();
    }

    // Método para ir a la vista de tabla de transacciones
    @FXML
    void ClickTablaTransacciones(ActionEvent event) {
        abrirVista("/uniquindio/finalproject/VistaTablaTransacciones.fxml", event);
    }

    // Método para abrir una vista diferente
    private void abrirVista(String vista, ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource(vista));
            Stage newStage = new Stage();
            newStage.setTitle("Nueva Ventana");
            newStage.setScene(new Scene(root));
            newStage.show();
            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            currentStage.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void clickVolver(ActionEvent event) {
        abrirVista("/uniquindio/finalproject/VistaCuentaDeUsuario.fxml", event);
    }

    @FXML
    public void ClickTipoTransaccion(ActionEvent event) {
    }
}
