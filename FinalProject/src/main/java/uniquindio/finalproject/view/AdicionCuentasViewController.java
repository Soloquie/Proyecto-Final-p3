package uniquindio.finalproject.view;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import uniquindio.finalproject.Model.TipoCuenta;
import uniquindio.finalproject.controller.AdicionCuentasController;

import java.io.IOException;

public class AdicionCuentasViewController {

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

    private AdicionCuentasController adicionCuentasController;

    public AdicionCuentasViewController() {
        adicionCuentasController = new AdicionCuentasController();
    }

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
        boolean resultado = adicionCuentasController.guardarCuenta(
                txtIdCuenta.getText(),
                txtIdNombreBanco.getText(),
                txtNumeroCuenta.getText(),
                btnTipoDeCuenta.getText()
        );

        if (resultado) {
            mostrarMensaje("Notificación", "Cuenta Agregada", "Se ha agregado la cuenta correctamente", Alert.AlertType.INFORMATION);
            limpiarCampos();
        } else {
            mostrarMensaje("Error", "Error al Agregar Cuenta", "No se pudo agregar la cuenta. Verifique los datos.", Alert.AlertType.ERROR);
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
