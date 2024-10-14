package uniquindio.finalproject.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import uniquindio.finalproject.Model.Presupuesto;
import uniquindio.finalproject.Model.Categoria;
import uniquindio.finalproject.global.SesionGlobal;

import java.io.IOException;

public class AdicionPresupuestosController {

    @FXML
    private Button BtbBack;

    @FXML
    private Button BtnAgregarPresupuesto;

    @FXML
    private Button BtnLimpiar;

    @FXML
    private TextField txtDescripcionCategoria;

    @FXML
    private TextField txtIDPresupuesto;

    @FXML
    private TextField txtIdCategoria;

    @FXML
    private TextField txtMontoGastado;

    @FXML
    private TextField txtMontoTotal;

    @FXML
    private TextField txtNombreCategoria;

    @FXML
    private TextField txtNombrePresupuesto;

    @FXML
    void ClickAgregarPresupuesto(ActionEvent event) {
        añadirPresupuesto();
    }

    @FXML
    void ClickBack(ActionEvent event) {
        abrirVista("/uniquindio/finalproject/VistaGestionUsuario.fxml", event);
    }

    @FXML
    void ClickLimpiar(ActionEvent event) {
        limpiarCampos();
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

    public void añadirPresupuesto() {
        String idPresupuesto = txtIDPresupuesto.getText();
        String nombrePresupuesto = txtNombrePresupuesto.getText();
        Double montoTotal;
        Double montoGastado;
        String nombreCategoria = txtNombreCategoria.getText();
        String idCategoria = txtIdCategoria.getText();
        String descripcionCategoria = txtDescripcionCategoria.getText();
        if (idPresupuesto.isEmpty() || nombrePresupuesto.isEmpty() ||
                txtMontoTotal.getText().isEmpty() || txtMontoGastado.getText().isEmpty()) {
            mostrarMensaje("Error", "Campos Vacíos", "Por favor llene todos los campos correctamente", Alert.AlertType.ERROR);
            return;
        }

        try {
            montoTotal = Double.parseDouble(txtMontoTotal.getText());
            montoGastado = Double.parseDouble(txtMontoGastado.getText());
        } catch (NumberFormatException e) {
            mostrarMensaje("Error", "Formato incorrecto", "El monto debe ser un número válido", Alert.AlertType.ERROR);
            return;
        }

        Categoria categoria = new Categoria(idCategoria, nombreCategoria, descripcionCategoria);
        Presupuesto nuevoPresupuesto = new Presupuesto(idPresupuesto, nombrePresupuesto, montoTotal, montoGastado, categoria);
        SesionGlobal.usuarioActual.añadirPresupuesto(nuevoPresupuesto);
        limpiarCampos();
        mostrarMensaje("Éxito", "Presupuesto Añadido", "El presupuesto ha sido añadido correctamente", Alert.AlertType.INFORMATION);
    }

    private void limpiarCampos() {
        txtIDPresupuesto.clear();
        txtNombrePresupuesto.clear();
        txtMontoTotal.clear();
        txtMontoGastado.clear();
        txtDescripcionCategoria.clear();
        txtIdCategoria.clear();
        txtNombreCategoria.clear();
    }

    private void mostrarMensaje(String titulo, String encabezado, String contenido, Alert.AlertType tipo) {
        Alert alerta = new Alert(tipo);
        alerta.setTitle(titulo);
        alerta.setHeaderText(encabezado);
        alerta.setContentText(contenido);
        alerta.showAndWait();
    }
}
