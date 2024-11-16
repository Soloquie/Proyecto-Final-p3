package uniquindio.finalproject.view;

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
import uniquindio.finalproject.controller.AdicionPresupuestosController;

import java.io.IOException;

public class AdicionPresupuestosViewController {

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

    private AdicionPresupuestosController adicionPresupuestosController;

    public AdicionPresupuestosViewController() {
        adicionPresupuestosController = new AdicionPresupuestosController();
    }

    @FXML
    void ClickAgregarPresupuesto(ActionEvent event) {
        boolean resultado = adicionPresupuestosController.añadirPresupuesto(
                txtIDPresupuesto.getText(),
                txtNombrePresupuesto.getText(),
                txtMontoTotal.getText(),
                txtMontoGastado.getText(),
                txtNombreCategoria.getText(),
                txtIdCategoria.getText(),
                txtDescripcionCategoria.getText()
        );

        if (resultado) {
            mostrarMensaje("Éxito", "Presupuesto Añadido", "El presupuesto ha sido añadido correctamente", Alert.AlertType.INFORMATION);
            limpiarCampos();
        } else {
            mostrarMensaje("Error", "Error en la Adición", "No se pudo añadir el presupuesto. Verifique los datos.", Alert.AlertType.ERROR);
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
