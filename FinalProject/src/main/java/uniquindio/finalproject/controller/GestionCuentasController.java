package uniquindio.finalproject.controller;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import uniquindio.finalproject.Model.Cuenta;
import uniquindio.finalproject.Model.TipoCuenta;
import uniquindio.finalproject.exceptions.CuentaNoEncontradaException;
import uniquindio.finalproject.global.SesionGlobal;

import java.io.IOException;

public class GestionCuentasController {

    @FXML
    private Button btnActualizar;

    @FXML
    private Button btnEliminar;

    @FXML
    private Button btnGuardar;

    @FXML
    private Button btnLimpiar;

    @FXML
    private MenuButton btnTipoDeCuenta;

    @FXML
    private TextField txtBuscar;

    @FXML
    private TextField txtNombreBanco;

    @FXML
    private TextField txtNumeroCuenta;

    @FXML
    private TextField txtid;

    @FXML
    private TableView<Cuenta> TablaCuentasUsuario;

    @FXML
    private TableColumn<Cuenta, String> colIdCuenta;

    @FXML
    private TableColumn<Cuenta, String> colNombreBanco;

    @FXML
    private TableColumn<Cuenta, String> colNumCuenta;

    @FXML
    private TableColumn<Cuenta, String> colTipoCuenta;


    @FXML
    public void initialize() {
        colIdCuenta.setCellValueFactory(new PropertyValueFactory<>("idCuenta"));
        colNombreBanco.setCellValueFactory(new PropertyValueFactory<>("nombreBanco"));
        colNumCuenta.setCellValueFactory(new PropertyValueFactory<>("numeroCuenta"));
        colTipoCuenta.setCellValueFactory(new PropertyValueFactory<>("tipoCuenta"));
        actualizarTabla();
        TablaCuentasUsuario.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                mostrarInformacionCuenta(newValue);
            }
        });
        for (TipoCuenta tipo : TipoCuenta.values()) {
            MenuItem item = new MenuItem(tipo.toString());
            item.setOnAction(e -> btnTipoDeCuenta.setText(tipo.toString()));
            btnTipoDeCuenta.getItems().add(item);
        }
    }

    @FXML
    void ClickGuardar(ActionEvent event) {
        String idCuenta = txtid.getText();
        String nombreBanco = txtNombreBanco.getText();
        String numeroCuenta = txtNumeroCuenta.getText();
        String tipoCuentaStr = btnTipoDeCuenta.getText();
        if (idCuenta.isEmpty() || nombreBanco.isEmpty() || numeroCuenta.isEmpty() || tipoCuentaStr.equals("Seleccione tipo de cuenta")) {
            mostrarMensaje("Error", "Campos Vacíos", "Por favor llene todos los campos", Alert.AlertType.ERROR);
            return;
        }
        TipoCuenta tipoCuenta = TipoCuenta.valueOf(tipoCuentaStr);
        Cuenta nuevaCuenta = new Cuenta(idCuenta, nombreBanco, numeroCuenta, tipoCuenta);
        SesionGlobal.usuarioActual.añadirCuenta(nuevaCuenta);
        actualizarTabla();
        limpiarCampos();
    }

    @FXML
    void ClickActualizar(ActionEvent event) {
        Cuenta cuentaSeleccionada = TablaCuentasUsuario.getSelectionModel().getSelectedItem();

        if (cuentaSeleccionada != null) {
            cuentaSeleccionada.setIdCuenta(txtid.getText());
            cuentaSeleccionada.setNombreBanco(txtNombreBanco.getText());
            cuentaSeleccionada.setNumeroCuenta(txtNumeroCuenta.getText());
            cuentaSeleccionada.setTipoCuenta(TipoCuenta.valueOf(btnTipoDeCuenta.getText()));
            actualizarTabla();
            limpiarCampos();
        } else {
            mostrarMensaje("Advertencia", "Actualización Fallida", "No se ha seleccionado ninguna cuenta para actualizar", Alert.AlertType.WARNING);
        }
    }

    @FXML
    void ClickEliminar(ActionEvent event) {
        try {
            // Obtener la cuenta seleccionada de la tabla
            Cuenta cuentaSeleccionada = TablaCuentasUsuario.getSelectionModel().getSelectedItem();

            // Validar si la cuenta seleccionada no es nula
            if (cuentaSeleccionada == null) {
                throw new CuentaNoEncontradaException("No se ha seleccionado ninguna cuenta para eliminar.");
            }

            // Verificar si la cuenta existe en las cuentas asociadas del usuario actual
            if (!SesionGlobal.usuarioActual.getCuentasAsociadas().contains(cuentaSeleccionada)) {
                throw new CuentaNoEncontradaException("La cuenta seleccionada no existe en las cuentas asociadas del usuario.");
            }

            // Eliminar la cuenta de las cuentas asociadas del usuario
            SesionGlobal.usuarioActual.getCuentasAsociadas().remove(cuentaSeleccionada);

            // Actualizar la tabla y limpiar campos
            actualizarTabla();
            limpiarCampos();

            // Mostrar mensaje de éxito
            mostrarMensaje("Éxito", "Cuenta Eliminada", "La cuenta ha sido eliminada correctamente", Alert.AlertType.INFORMATION);
        } catch (CuentaNoEncontradaException e) {
            // Manejo de la excepción y mostrar un mensaje de advertencia
            mostrarMensaje("Advertencia", "Eliminación Fallida", e.getMessage(), Alert.AlertType.WARNING);
        } catch (Exception e) {
            // Manejo genérico de errores
            mostrarMensaje("Error", "Error inesperado", "Ocurrió un error al intentar eliminar la cuenta.", Alert.AlertType.ERROR);
            e.printStackTrace();
        }
    }

    @FXML
    void ClickLimpiar(ActionEvent event) {
        limpiarCampos();
    }

    private void actualizarTabla() {
        TablaCuentasUsuario.setItems(FXCollections.observableArrayList(SesionGlobal.usuarioActual.getCuentasAsociadas()));
        TablaCuentasUsuario.refresh();
    }

    private void mostrarInformacionCuenta(Cuenta cuenta) {
        txtid.setText(cuenta.getIdCuenta());
        txtNombreBanco.setText(cuenta.getNombreBanco());
        txtNumeroCuenta.setText(cuenta.getNumeroCuenta());
        btnTipoDeCuenta.setText(cuenta.getTipoCuenta().toString());
    }

    private void limpiarCampos() {
        txtid.clear();
        txtNombreBanco.clear();
        txtNumeroCuenta.clear();
        btnTipoDeCuenta.setText("Seleccione tipo de cuenta");
    }

    private void mostrarMensaje(String titulo, String encabezado, String contenido, Alert.AlertType tipo) {
        Alert alerta = new Alert(tipo);
        alerta.setTitle(titulo);
        alerta.setHeaderText(encabezado);
        alerta.setContentText(contenido);
        alerta.showAndWait();
    }

    @FXML
    void clickVolver(ActionEvent event) {
        abrirVista("/uniquindio/finalproject/VistaCuentaDeUsuario.fxml", event);
    }

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
}
