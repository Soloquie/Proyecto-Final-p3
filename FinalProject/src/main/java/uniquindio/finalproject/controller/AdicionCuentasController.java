package uniquindio.finalproject.controller;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import uniquindio.finalproject.Model.Cuenta;
import uniquindio.finalproject.global.SesionGlobal;
import uniquindio.finalproject.Model.TipoCuenta;

import java.io.IOException;

public class AdicionCuentasController {

    @FXML
    public Button BtnLimpiar;

    @FXML
    public Button BtbBack;

    @FXML
    private Button btnLimpiar;

    @FXML
    private Button btnAgregarCuenta;

    @FXML
    private MenuButton btnTipoDeCuenta;

    @FXML
    private TextField txtIdCuenta;

    @FXML
    private TextField txtIdNombreBanco;

    @FXML
    private TextField txtNumeroCuenta;

    @FXML
    private TableView<Cuenta> colCuenta; // Supuesto para mostrar la lista de cuentas en una tabla

    // Inicializar los valores del tipo de cuenta
    @FXML
    public void initialize() {
        for (TipoCuenta tipo : TipoCuenta.values()) {
            MenuItem item = new MenuItem(tipo.toString());
            item.setOnAction(e -> btnTipoDeCuenta.setText(tipo.toString()));
            btnTipoDeCuenta.getItems().add(item);
        }
    }

    @FXML
    void ClickAgregarCuenta(ActionEvent event) {
        guardarCuenta();
        mostrarMensaje("Notificación", "Cuenta Agregada", "Se ha agregado la cuenta correctamente", Alert.AlertType.INFORMATION);
        System.out.println("Cuenta agregada exitosamente.");
    }

    @FXML
    void ClickActualizar(ActionEvent event) {
        actualizarCuenta();
        mostrarMensaje("Notificación", "Actualización Correcta", "Se ha actualizado la cuenta correctamente", Alert.AlertType.INFORMATION);
        System.out.println("Cuenta actualizada exitosamente.");
    }

    @FXML
    void ClickEliminar(ActionEvent event) {
        Cuenta cuentaSeleccionada = colCuenta.getSelectionModel().getSelectedItem();
        if (cuentaSeleccionada != null) {
            SesionGlobal.usuarioActual.getCuentasAsociadas().remove(cuentaSeleccionada);
            colCuenta.setItems(FXCollections.observableArrayList(SesionGlobal.usuarioActual.getCuentasAsociadas()));
            limpiarCampos();
        } else {
            mostrarMensaje("Advertencia", "Eliminación Fallida", "No se ha seleccionado ninguna cuenta para eliminar", Alert.AlertType.WARNING);
        }
    }

    @FXML
    void ClickBack(ActionEvent event) {
        abrirVista("/uniquindio/finalproject/VistaGestionUsuario.fxml", event);
    }

    @FXML
    void ClickLimpiar(ActionEvent event) {
        limpiarCampos();
    }

    private void guardarCuenta() {
        String idCuenta = txtIdCuenta.getText();
        String nombreBanco = txtIdNombreBanco.getText();
        String numeroCuenta = txtNumeroCuenta.getText();
        String tipoCuentaStr = btnTipoDeCuenta.getText();

        // Convertir el texto seleccionado en TipoCuenta
        TipoCuenta tipoCuenta = TipoCuenta.valueOf(tipoCuentaStr);

        Cuenta nuevaCuenta = new Cuenta(idCuenta, nombreBanco, numeroCuenta, tipoCuenta);

        limpiarCampos();
    }

    private void actualizarCuenta() {
        Cuenta cuentaSeleccionada = colCuenta.getSelectionModel().getSelectedItem();
        if (cuentaSeleccionada != null) {
            cuentaSeleccionada.setIdCuenta(txtIdCuenta.getText());
            cuentaSeleccionada.setNombreBanco(txtIdNombreBanco.getText());
            cuentaSeleccionada.setNumeroCuenta(txtNumeroCuenta.getText());
            cuentaSeleccionada.setTipoCuenta(TipoCuenta.valueOf(btnTipoDeCuenta.getText()));

            int index = SesionGlobal.usuarioActual.getCuentasAsociadas().indexOf(cuentaSeleccionada);
            if (index != -1) {
                SesionGlobal.usuarioActual.getCuentasAsociadas().set(index, cuentaSeleccionada);
                colCuenta.refresh(); // Refrescar la tabla
                limpiarCampos();
            }
        } else {
            mostrarMensaje("Advertencia", "Actualización Fallida", "No se ha seleccionado ninguna cuenta para actualizar", Alert.AlertType.WARNING);
        }
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

    private void limpiarCampos() {
        txtIdCuenta.clear();
        txtIdNombreBanco.clear();
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
}
