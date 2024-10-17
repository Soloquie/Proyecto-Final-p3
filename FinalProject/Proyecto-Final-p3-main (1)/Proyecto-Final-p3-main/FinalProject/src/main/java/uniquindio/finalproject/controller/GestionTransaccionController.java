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
import uniquindio.finalproject.global.SesionGlobal;
import uniquindio.finalproject.persistencia.ArchivoUtil;

import java.io.IOException;
import java.time.LocalDate;

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
        String idTransaccion = txtIdTransaccion.getText();
        LocalDate fecha = txtFecha.getValue();
        String tipoTransaccionStr = btnTipoTransaccion.getText();
        Double monto = Double.parseDouble(txtMonto.getText());
        String descripcion = txtDescripcionTransaccion.getText();
        String idCuentaOrigen = txtIdCuentaOrigen.getText();
        String idCuentaDestino = txtIdCuenta.getText();
        String idCategoria = txtIdCategoria.getText();
        if (idTransaccion.isEmpty() || tipoTransaccionStr.equals("Seleccione tipo de transacción") || monto <= 0) {
            mostrarMensaje("Error", "Campos Vacíos", "Por favor llene todos los campos correctamente", Alert.AlertType.ERROR);
            return;
        }
        Cuenta cuentaOrigen = SesionGlobal.usuarioActual.getCuentaPorId(idCuentaOrigen);
        Cuenta cuentaDestino = SesionGlobal.usuarioActual.getCuentaPorId(idCuentaDestino);
        Categoria categoria = SesionGlobal.usuarioActual.getCategoriaPorId(idCategoria);
        if (cuentaOrigen == null || cuentaDestino == null || categoria == null) {
            mostrarMensaje("Error", "Datos incorrectos", "Revise los IDs de las cuentas o categoría", Alert.AlertType.ERROR);
            return;
        }
        TipoTransaccion tipoTransaccion = TipoTransaccion.valueOf(tipoTransaccionStr);
        Transaccion nuevaTransaccion = new Transaccion(idTransaccion, fecha, tipoTransaccion, monto, descripcion, cuentaOrigen, cuentaDestino, categoria);
        SesionGlobal.usuarioActual.añadirTransaccion(nuevaTransaccion); // Añadir transacción al usuario

        limpiarCampos();
        ArchivoUtil.guardarRegistroLog("Usuario: "+SesionGlobal.usuarioActual.getNombre()+" Gestion Transaccion Controlador", 1, "clickEnviarDinero", ("C:\\td\\Persistencia\\Log\\log.txt"));
        mostrarMensaje("Éxito", "Transacción Enviada", "La transacción ha sido registrada correctamente", Alert.AlertType.INFORMATION);
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
        ArchivoUtil.guardarRegistroLog("Usuario: "+SesionGlobal.usuarioActual.getNombre()+" Gestion Transaccion Controlador", 1, "clickTablaTransacciones", ("C:\\td\\Persistencia\\Log\\log.txt"));
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
        ArchivoUtil.guardarRegistroLog("Usuario: "+SesionGlobal.usuarioActual.getNombre()+" Gestion Transaccion Controlador", 1, "clickVolver", ("C:\\td\\Persistencia\\Log\\log.txt"));
    }

    @FXML
    public void ClickTipoTransaccion(ActionEvent event) {
    }
}
