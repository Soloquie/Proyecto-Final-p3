package uniquindio.finalproject.view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import uniquindio.finalproject.controller.GestionCuentasController;
import uniquindio.finalproject.mapping.dto.CuentaDto;
import uniquindio.finalproject.mapping.dto.UsuarioDto;
import uniquindio.finalproject.Model.TipoCuenta;

import java.util.List;

public class GestionCuentasViewController {

    @FXML
    private TextField txtBuscar, txtSaldoCuenta, txtid, txtNombreBanco, txtNumeroCuenta;

    @FXML
    private TableView<CuentaDto> TablaCuentasUsuario;

    @FXML
    private TableColumn<CuentaDto, String> colIdCuentaUsuario, colNombreBancoCuentaUsuario, colNumeroCuentaUsuario;

    @FXML
    private TableColumn<CuentaDto, String> colTipoCuentaUsuario;

    @FXML
    private MenuButton btnTipoDeCuenta;

    @FXML
    private Button btnGuardar, btnActualizar, btnEliminar, btnLimpiar;

    private GestionCuentasController cuentaUsuarioController;
    private TipoCuenta tipoCuentaSeleccionado;

    @FXML
    public void initialize() {
        colIdCuentaUsuario.setCellValueFactory(new PropertyValueFactory<>("idCuenta"));
        colNombreBancoCuentaUsuario.setCellValueFactory(new PropertyValueFactory<>("nombreBanco"));
        colNumeroCuentaUsuario.setCellValueFactory(new PropertyValueFactory<>("numeroCuenta"));
        colTipoCuentaUsuario.setCellValueFactory(new PropertyValueFactory<>("tipoCuenta"));

        if (cuentaUsuarioController != null) {
            cargarCuentasUsuario();
        }

        // Configurar opciones de tipo de cuenta en el menú
        for (TipoCuenta tipo : TipoCuenta.values()) {
            MenuItem item = new MenuItem(tipo.toString());
            item.setOnAction(event -> {
                tipoCuentaSeleccionado = tipo;
                btnTipoDeCuenta.setText(tipo.toString());
            });
            btnTipoDeCuenta.getItems().add(item);
        }
    }

    public void setUsuarioActual(UsuarioDto usuario) {
        this.cuentaUsuarioController = new GestionCuentasController(usuario);
        cargarCuentasUsuario();
    }

    public void mostrarCuentasUsuario(List<CuentaDto> cuentas) {
        TablaCuentasUsuario.setItems(FXCollections.observableArrayList(cuentas));
    }

    @FXML
    void ClickGuardar(ActionEvent event) {
        cuentaUsuarioController.guardarCuenta(txtid.getText(), txtNombreBanco.getText(), txtNumeroCuenta.getText(), tipoCuentaSeleccionado, Double.valueOf(txtSaldoCuenta.getText()));
        cargarCuentasUsuario();
        limpiarCampos();
    }

    @FXML
    void ClickActualizar(ActionEvent event) {
        CuentaDto cuentaSeleccionada = TablaCuentasUsuario.getSelectionModel().getSelectedItem();
        if (cuentaSeleccionada != null) {
            cuentaUsuarioController.actualizarCuenta(cuentaSeleccionada.idCuenta(), txtid.getText(), txtNombreBanco.getText(), txtNumeroCuenta.getText(), tipoCuentaSeleccionado, Double.valueOf(txtSaldoCuenta.getText()));
            cargarCuentasUsuario();
            limpiarCampos();
        } else {
            mostrarMensaje("Advertencia", "Actualización Fallida", "No se ha seleccionado ninguna cuenta para actualizar", Alert.AlertType.WARNING);
        }
    }

    @FXML
    void ClickEliminar(ActionEvent event) {
        CuentaDto cuentaSeleccionada = TablaCuentasUsuario.getSelectionModel().getSelectedItem();
        if (cuentaSeleccionada != null) {
            cuentaUsuarioController.eliminarCuenta(cuentaSeleccionada);
            cargarCuentasUsuario();
            limpiarCampos();
            mostrarMensaje("Exito", "Eliminación Exitosa", "La cuenta se ha eliminado exitosamente", Alert.AlertType.INFORMATION);
        } else {
            mostrarMensaje("Advertencia", "Eliminación Fallida", "No se ha seleccionado ninguna cuenta para eliminar", Alert.AlertType.WARNING);
        }
    }

    @FXML
    void ClickLimpiar(ActionEvent event) {
        limpiarCampos();
    }

    private void cargarCuentasUsuario() {
        List<CuentaDto> cuentas = cuentaUsuarioController.obtenerCuentasUsuario();
        mostrarCuentasUsuario(cuentas);
    }

    private void limpiarCampos() {
        txtid.clear();
        txtNombreBanco.clear();
        txtNumeroCuenta.clear();
        btnTipoDeCuenta.setText("Seleccione tipo de cuenta");
        tipoCuentaSeleccionado = null;
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
        cuentaUsuarioController.volver(event);
    }
}
