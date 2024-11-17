package uniquindio.finalproject.view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import uniquindio.finalproject.Model.TipoCuenta;
import uniquindio.finalproject.controller.GestionCuentasController;
import uniquindio.finalproject.mapping.dto.CuentaDto;
import uniquindio.finalproject.mapping.dto.UsuarioDto;

import java.util.List;

public class GestionCuentasViewController {

    @FXML
    private TextField txtSaldoCuenta;
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
    private TextField txtNombreBanco;
    @FXML
    private TextField txtNumeroCuenta;
    @FXML
    private TextField txtid;
    @FXML
    private TableView<CuentaDto> TablaCuentasUsuario;
    @FXML
    private TableColumn<CuentaDto, String> colIdCuenta;
    @FXML
    private TableColumn<CuentaDto, String> colNombreBanco;
    @FXML
    private TableColumn<CuentaDto, String> colNumCuenta;
    @FXML
    private TableColumn<CuentaDto, String> colTipoCuenta;

    private GestionCuentasController gestionCuentasController;

    public GestionCuentasViewController() {
        this.gestionCuentasController = new GestionCuentasController();
    }

    @FXML
    public void initialize() {
        colIdCuenta.setCellValueFactory(new PropertyValueFactory<>("idCuenta"));
        colNombreBanco.setCellValueFactory(new PropertyValueFactory<>("nombreBanco"));
        colNumCuenta.setCellValueFactory(new PropertyValueFactory<>("numeroCuenta"));
        colTipoCuenta.setCellValueFactory(new PropertyValueFactory<>("tipoCuenta"));
        cargarTiposCuenta();
        actualizarTabla();
        TablaCuentasUsuario.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                mostrarInformacionCuenta(newValue);
            }
        });
    }

    public void setUsuarioActual(UsuarioDto usuario) {
        gestionCuentasController.setUsuarioActual(usuario);
        actualizarTabla();
    }


    @FXML
    private void ClickGuardar(ActionEvent event) {
        String idCuenta = txtid.getText();
        String nombreBanco = txtNombreBanco.getText();
        String numeroCuenta = txtNumeroCuenta.getText();
        String tipoCuentaStr = btnTipoDeCuenta.getText();
        double saldo = Double.parseDouble(txtSaldoCuenta.getText());

        boolean guardado = gestionCuentasController.guardarCuenta(idCuenta, nombreBanco, numeroCuenta, tipoCuentaStr, saldo);
        if (guardado) {
            mostrarMensaje("Notificación", "Cuenta Guardada", "La cuenta ha sido guardada correctamente", Alert.AlertType.INFORMATION);
            limpiarCampos();
            actualizarTabla();
        } else {
            mostrarMensaje("Error", "Error al Guardar", "No se pudo guardar la cuenta. Verifique los datos.", Alert.AlertType.ERROR);
        }
    }

    private void cargarTiposCuenta() {
        btnTipoDeCuenta.getItems().clear(); // Limpiar cualquier elemento previo
        for (TipoCuenta tipo : TipoCuenta.values()) {
            MenuItem item = new MenuItem(tipo.name());
            item.setOnAction(e -> btnTipoDeCuenta.setText(tipo.name()));
            btnTipoDeCuenta.getItems().add(item);
        }
    }


    @FXML
    private void ClickActualizar(ActionEvent event) {
        CuentaDto cuentaSeleccionada = TablaCuentasUsuario.getSelectionModel().getSelectedItem();

        if (cuentaSeleccionada != null) {
            // Crear una nueva cuenta con los valores actualizados
            CuentaDto cuentaActualizada = new CuentaDto(
                    txtid.getText(),
                    txtNombreBanco.getText(),
                    txtNumeroCuenta.getText(),
                    TipoCuenta.valueOf(btnTipoDeCuenta.getText()),
                    cuentaSeleccionada.usuario(), // Usuario original
                    Double.parseDouble(txtSaldoCuenta.getText())
            );

            // Actualizar la cuenta en la fuente de datos
            gestionCuentasController.actualizarCuenta(cuentaSeleccionada, cuentaActualizada);
            actualizarTabla(); // Refrescar la tabla con los datos actualizados
            limpiarCampos();

            mostrarMensaje("Notificación", "Cuenta Actualizada", "La cuenta ha sido actualizada correctamente", Alert.AlertType.INFORMATION);
        } else {
            mostrarMensaje("Advertencia", "Actualización Fallida", "No se ha seleccionado ninguna cuenta para actualizar", Alert.AlertType.WARNING);
        }
    }


    @FXML
    private void ClickEliminar(ActionEvent event) {
        CuentaDto cuentaSeleccionada = TablaCuentasUsuario.getSelectionModel().getSelectedItem();
        if (cuentaSeleccionada != null) {
            gestionCuentasController.eliminarCuenta(cuentaSeleccionada);
            actualizarTabla();
            limpiarCampos();
        } else {
            mostrarMensaje("Advertencia", "Eliminación Fallida", "No se ha seleccionado ninguna cuenta para eliminar", Alert.AlertType.WARNING);
        }
    }

    public void actualizarTabla() {
        List<CuentaDto> cuentas = gestionCuentasController.obtenerCuentasUsuario();
        ObservableList<CuentaDto> cuentasDto = FXCollections.observableArrayList(cuentas);
        TablaCuentasUsuario.setItems(cuentasDto);
    }

    private void mostrarInformacionCuenta(CuentaDto cuenta) {
        txtid.setText(cuenta.idCuenta());
        txtNombreBanco.setText(cuenta.nombreBanco());
        txtNumeroCuenta.setText(cuenta.numeroCuenta());
        btnTipoDeCuenta.setText(String.valueOf(cuenta.tipoCuenta()));
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
    public void ClickLimpiar(ActionEvent event) {
        limpiarCampos();
    }

    @FXML
    public void clickVolver(ActionEvent event) {
        gestionCuentasController.volver(event);
    }
}
