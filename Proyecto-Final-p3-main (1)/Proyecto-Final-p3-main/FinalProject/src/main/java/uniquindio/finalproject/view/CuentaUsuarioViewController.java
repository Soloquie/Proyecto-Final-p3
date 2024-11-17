package uniquindio.finalproject.view;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import uniquindio.finalproject.controller.CuentaUsuarioController;
import uniquindio.finalproject.mapping.dto.CuentaDto;
import uniquindio.finalproject.mapping.dto.UsuarioDto;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class CuentaUsuarioViewController implements Initializable {



    @FXML
    private TableView<CuentaDto> tablaCuentasUsuario;

    @FXML
    private TableColumn<CuentaDto, String> colIdCuentaUsuario;
    @FXML
    private TableColumn<CuentaDto, String> colNombreBancoCuentaUsuario;
    @FXML
    private TableColumn<CuentaDto, String> colNumeroCuentaUsuario;
    @FXML
    private TableColumn<CuentaDto, Double> colSaldoCuentaUsuario;

    @FXML
    private Label txtSaldoUsuario;

    private CuentaUsuarioController cuentaUsuarioController;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Configurar columnas de la tabla sin cargar datos
        colIdCuentaUsuario.setCellValueFactory(new PropertyValueFactory<>("idCuenta"));
        colNombreBancoCuentaUsuario.setCellValueFactory(new PropertyValueFactory<>("nombreBanco"));
        colNumeroCuentaUsuario.setCellValueFactory(new PropertyValueFactory<>("numeroCuenta"));
        colSaldoCuentaUsuario.setCellValueFactory(new PropertyValueFactory<>("saldo"));


        // Solo cargar datos si el controlador está inicializado
        if (cuentaUsuarioController != null) {
            cargarCuentasUsuario();
        }
    }

    // Dentro de setUsuarioActual
    public void setUsuarioActual(UsuarioDto usuario) {
        this.cuentaUsuarioController = new CuentaUsuarioController(usuario);
        cargarCuentasUsuario();
    }

    public void mostrarCuentasUsuario(List<CuentaDto> cuentas) {
        tablaCuentasUsuario.getItems().setAll(cuentas);
    }

    public void mostrarSaldoUsuario(double saldo) {
        txtSaldoUsuario.setText(String.valueOf(saldo));
    }

    @FXML
    public void ClickEnviarDinero(ActionEvent event) {
        // Implementación para enviar dinero
    }

// Asumiendo que `colIdCuentaUsuario`, `colNombreBancoCuentaUsuario`, etc. son TableColumn<CuentaDto, String>



    private void cargarCuentasUsuario() {
        List<CuentaDto> cuentas = obtenerCuentasUsuarioDesdeServidor();
        ObservableList<CuentaDto> cuentasDto = FXCollections.observableArrayList(cuentas);
        tablaCuentasUsuario.setItems(cuentasDto);
    }

    private List<CuentaDto> obtenerCuentasUsuarioDesdeServidor() {
        return cuentaUsuarioController.cargarCuentasUsuario(this);
    }

    private void abrirVista1(String vista, ActionEvent event, UsuarioDto usuario) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(vista));
            Parent root = loader.load();
            GestionCuentasViewController controller = loader.getController();
            controller.setUsuarioActual(usuario);  // Establece el usuario actual aquí

            // Ahora que el usuario se ha establecido, puedes cargar la tabla de cuentas
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
    public void clickCuentasUsuario(ActionEvent event) {
        abrirVista1("/uniquindio/finalproject/VistaGestionDeCuentas.fxml", event, cuentaUsuarioController.getUsuarioActual());

    }

    @FXML
    public void clickTransaccionesUsuario(ActionEvent event) {
        cuentaUsuarioController.abrirVistaTransaccionesUsuario(event);
    }

    @FXML
    public void clickPerfil(ActionEvent event) {
        cuentaUsuarioController.abrirVistaEditarPerfil(event);
    }

    @FXML
    public void clickCerrarSesion(ActionEvent event) {
        cerrarSesion(event);
    }

    @FXML
    public void clickCategoriasUsuario(ActionEvent event) {
        cuentaUsuarioController.abrirVistaCategoriasUsuario(event);
    }

    @FXML
    public void clickPresupuestosUsuario(ActionEvent event) {
        cuentaUsuarioController.abrirVistaPresupuestosUsuario(event);
    }

    public void cerrarSesion(ActionEvent event) {
        abrirVista("/uniquindio/finalproject/VistaLogin.fxml", event);
    }

    private void abrirVista(String vista, ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(vista));
            Parent root = loader.load();
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
