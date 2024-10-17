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
import uniquindio.finalproject.global.SesionGlobal;
import uniquindio.finalproject.persistencia.ArchivoUtil;
import uniquindio.finalproject.persistencia.Persistencia;

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

        // Validar que todos los campos estén completos
        if (idCuenta.isEmpty() || nombreBanco.isEmpty() || numeroCuenta.isEmpty() || tipoCuentaStr.equals("Seleccione tipo de cuenta")) {
            mostrarMensaje("Error", "Campos Vacíos", "Por favor llene todos los campos", Alert.AlertType.ERROR);
            return;
        }

        try {
            // Convertir el tipo de cuenta a enum
            TipoCuenta tipoCuenta = TipoCuenta.valueOf(tipoCuentaStr);

            // Crear una nueva cuenta
            Cuenta nuevaCuenta = new Cuenta(idCuenta, nombreBanco, numeroCuenta, tipoCuenta);

            SesionGlobal.usuarioActual.añadirCuenta(nuevaCuenta);

            Persistencia persistencia = new Persistencia();
            persistencia.guardarCuenta(nuevaCuenta);
            ArchivoUtil.guardarRegistroLog("Usuario: "+SesionGlobal.usuarioActual.getNombre()+" Gestion Cuentas Controlador", 1, "clickGuardar", ("C:\\td\\Persistencia\\Log\\log.txt"));
            actualizarTabla();
            limpiarCampos();
            mostrarMensaje("Notificación", "Cuenta Guardada", "La cuenta ha sido guardada correctamente", Alert.AlertType.INFORMATION);

        } catch (IllegalArgumentException e) {
            mostrarMensaje("Error", "Tipo de cuenta inválido", "Seleccione un tipo de cuenta válido.", Alert.AlertType.ERROR);
        } catch (IOException e) {
            mostrarMensaje("Error", "Error al guardar", "No se pudo guardar la cuenta en el archivo.", Alert.AlertType.ERROR);
            e.printStackTrace();
        }
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
            ArchivoUtil.guardarRegistroLog("Usuario: "+SesionGlobal.usuarioActual.getNombre()+" Gestion Cuentas Controlador", 1, "clickActualizar", ("C:\\td\\Persistencia\\Log\\log.txt"));
            limpiarCampos();
        } else {
            mostrarMensaje("Advertencia", "Actualización Fallida", "No se ha seleccionado ninguna cuenta para actualizar", Alert.AlertType.WARNING);
        }
    }

    @FXML
    void ClickEliminar(ActionEvent event) {
        Cuenta cuentaSeleccionada = TablaCuentasUsuario.getSelectionModel().getSelectedItem();
        if (cuentaSeleccionada != null) {
            SesionGlobal.usuarioActual.getCuentasAsociadas().remove(cuentaSeleccionada);
            actualizarTabla();
            limpiarCampos();
            ArchivoUtil.guardarRegistroLog("Usuario: "+SesionGlobal.usuarioActual.getNombre()+" Gestion Cuentas Controlador", 1, "clickEliminar", ("C:\\td\\Persistencia\\Log\\log.txt"));
        } else {
            mostrarMensaje("Advertencia", "Eliminación Fallida", "No se ha seleccionado ninguna cuenta para eliminar", Alert.AlertType.WARNING);
        }
    }

    @FXML
    void ClickLimpiar(ActionEvent event) {
        limpiarCampos();
        ArchivoUtil.guardarRegistroLog("Usuario: "+SesionGlobal.usuarioActual.getNombre()+" Gestion Cuentas Controlador", 1, "clickLimpiar", ("C:\\td\\Persistencia\\Log\\log.txt"));
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
        ArchivoUtil.guardarRegistroLog("Usuario: "+SesionGlobal.usuarioActual.getNombre()+" Gestion Cuentas Controlador", 1, "clickVolver", ("C:\\td\\Persistencia\\Log\\log.txt"));
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
