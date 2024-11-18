package uniquindio.finalproject.view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import uniquindio.finalproject.controller.GestionTransaccionController;
import uniquindio.finalproject.mapping.dto.CuentaDto;
import uniquindio.finalproject.mapping.dto.UsuarioDto;
import uniquindio.finalproject.Model.TipoTransaccion;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

public class GestionTransaccionViewController {

    @FXML
    private ComboBox<CuentaDto> comboBoxCuentasDestino;

    @FXML
    private Button btnEnviarDinero;

    @FXML
    private Button btnTablaDeTransacciones;

    @FXML
    private MenuButton btnTipoTransaccion;

    @FXML
    private ComboBox<CuentaDto> comboBoxCargarCuentasUsuario;

    @FXML
    private AnchorPane mostrarInformacionCuentaSeleccionada;

    @FXML
    private TextField txtDescripcionTransaccion;

    @FXML
    private DatePicker txtFecha;

    @FXML
    private TextField txtIdTransaccion;

    @FXML
    private TextField txtMonto;

    @FXML
    private Label txtNombreUsuarioActual;

    private GestionTransaccionController transaccionController;

    private TipoTransaccion tipoTransaccionSeleccionado;

    @FXML
    public void initialize() {
        // Configuración del tipo de transacción
        for (TipoTransaccion tipo : TipoTransaccion.values()) {
            MenuItem item = new MenuItem(tipo.toString());
            item.setOnAction(e -> {
                tipoTransaccionSeleccionado = tipo;
                btnTipoTransaccion.setText(tipo.toString());
            });
            btnTipoTransaccion.getItems().add(item);
        }

        // Configuración de selección de cuenta de origen
        comboBoxCargarCuentasUsuario.setOnAction(event -> mostrarInformacionCuentaSeleccionada());
    }

    public void setUsuarioActual(UsuarioDto usuario) {
        this.transaccionController = new GestionTransaccionController(usuario);
        cargarCuentasUsuario();
        cargarCuentasDestino();
    }

    private void cargarCuentasUsuario() {
        List<CuentaDto> cuentas = transaccionController.obtenerCuentasUsuario();
        ObservableList<CuentaDto> cuentasList = FXCollections.observableArrayList(cuentas);
        comboBoxCargarCuentasUsuario.setItems(cuentasList);
    }

    private void cargarCuentasDestino() {
        List<CuentaDto> cuentas = transaccionController.obtenerTodasCuentas();
        ObservableList<CuentaDto> cuentasList = FXCollections.observableArrayList(cuentas);
        comboBoxCuentasDestino.setItems(cuentasList);
    }

    private void mostrarInformacionCuentaSeleccionada() {
        CuentaDto cuentaSeleccionada = comboBoxCargarCuentasUsuario.getSelectionModel().getSelectedItem();
        if (cuentaSeleccionada != null) {
            mostrarInformacionCuenta(cuentaSeleccionada);
        }
    }

    private void mostrarInformacionCuenta(CuentaDto cuenta) {
        mostrarInformacionCuentaSeleccionada.getChildren().clear();

        Label labelIdCuenta = new Label("ID de Cuenta: " + cuenta.idCuenta());
        Label labelNombreBanco = new Label("Banco: " + cuenta.nombreBanco());
        Label labelSaldo = new Label("Saldo: " + cuenta.saldo());

        AnchorPane.setTopAnchor(labelIdCuenta, 10.0);
        AnchorPane.setLeftAnchor(labelIdCuenta, 10.0);

        AnchorPane.setTopAnchor(labelNombreBanco, 30.0);
        AnchorPane.setLeftAnchor(labelNombreBanco, 10.0);

        AnchorPane.setTopAnchor(labelSaldo, 50.0);
        AnchorPane.setLeftAnchor(labelSaldo, 10.0);

        mostrarInformacionCuentaSeleccionada.getChildren().addAll(labelIdCuenta, labelNombreBanco, labelSaldo);
    }

    @FXML
    void ClickEnviarDinero(ActionEvent event) {
        String idTransaccion = txtIdTransaccion.getText();
        LocalDate fecha = txtFecha.getValue();
        Double monto = Double.parseDouble(txtMonto.getText());
        String descripcion = txtDescripcionTransaccion.getText();

        CuentaDto cuentaOrigen = comboBoxCargarCuentasUsuario.getSelectionModel().getSelectedItem();
        CuentaDto cuentaDestino = comboBoxCuentasDestino.getSelectionModel().getSelectedItem();

        if (tipoTransaccionSeleccionado == null || cuentaOrigen == null || cuentaDestino == null) {
            mostrarMensaje("Error", "Campos Vacíos", "Por favor seleccione todas las opciones correctamente.", Alert.AlertType.ERROR);
            return;
        }
        if(cuentaOrigen.getSaldo() < Double.parseDouble(txtMonto.getText())){
            mostrarMensaje("Error", "Presupuesto Excedido", "La cuenta no tiene el suficiente dinero para enviar.", Alert.AlertType.ERROR);
            return;
        }

        transaccionController.enviarTransaccion(idTransaccion, fecha, tipoTransaccionSeleccionado, monto, descripcion, cuentaOrigen, cuentaDestino);
        limpiarCampos();
        mostrarMensaje("Éxito", "Transacción Enviada", "La transacción ha sido registrada correctamente", Alert.AlertType.INFORMATION);
    }

    private void limpiarCampos() {
        txtIdTransaccion.clear();
        txtFecha.setValue(null);
        txtMonto.clear();
        txtDescripcionTransaccion.clear();
        btnTipoTransaccion.setText("Seleccione tipo de transacción");
        tipoTransaccionSeleccionado = null;
    }

    private void mostrarMensaje(String titulo, String encabezado, String contenido, Alert.AlertType tipo) {
        Alert alerta = new Alert(tipo);
        alerta.setTitle(titulo);
        alerta.setHeaderText(encabezado);
        alerta.setContentText(contenido);
        alerta.showAndWait();
    }

    @FXML
    void ClickTablaTransacciones(ActionEvent event) {
        abrirVistaTablaTransacciones(event);
    }


    private void abrirVistaTablaTransacciones(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/uniquindio/finalproject/VistaTablaTransacciones.fxml"));
            Parent root = loader.load();
            TablaTransaccionesViewController controller = loader.getController();
            controller.setUsuarioActual(transaccionController.getUsuarioActual());
            Stage newStage = new Stage();
            newStage.setTitle("Cuenta de Usuario");
            newStage.setScene(new Scene(root));
            newStage.show();

            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            currentStage.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void clickVolver(ActionEvent event) {
        volver(event);
    }

    public void volver(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/uniquindio/finalproject/VistaCuentaDeUsuario.fxml"));
            Parent root = loader.load();
            CuentaUsuarioViewController controller = loader.getController();
            controller.setUsuarioActual(transaccionController.getUsuarioActual());
            Stage newStage = new Stage();
            newStage.setTitle("Cuenta de Usuario");
            newStage.setScene(new Scene(root));
            newStage.show();

            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            currentStage.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void ClickTipoTransaccion(ActionEvent event) {
    }
}
