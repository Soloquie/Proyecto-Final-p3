package uniquindio.finalproject.view;

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
import uniquindio.finalproject.Model.Categoria;
import uniquindio.finalproject.controller.PresupuestosController;
import uniquindio.finalproject.mapping.dto.PresupuestoDto;
import uniquindio.finalproject.mapping.dto.UsuarioDto;

import java.io.IOException;
import java.util.List;

public class PresupuestosViewController {

    @FXML
    private Button btnBack, btnActualizar, btnCrear, btnEliminar, btnLimpiar;

    @FXML
    private TextField txtDescripcionCategoria, txtIDCategoria, txtIDPresupuesto, txtMontoGastado, txtMontoTotal, txtNombreCategoria, txtNombrePresupuesto;

    @FXML
    private TableView<PresupuestoDto> colPresupuesto;

    @FXML
    private TableColumn<PresupuestoDto, String> colIdPresupuesto, colNombre, colMonto, colMontoGastado, colIdCategoria, colNombreCategoria, colDescripcionCategoria;

    private PresupuestosController presupuestosController;
    private UsuarioDto usuarioActual;

    @FXML
    public void initialize() {
        colIdPresupuesto.setCellValueFactory(new PropertyValueFactory<>("idPresupuesto"));
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombrePresupuesto"));
        colMonto.setCellValueFactory(new PropertyValueFactory<>("montoTotal"));
        colMontoGastado.setCellValueFactory(new PropertyValueFactory<>("montoGastado"));
        colIdCategoria.setCellValueFactory(new PropertyValueFactory<>("idCategoria"));
        colNombreCategoria.setCellValueFactory(new PropertyValueFactory<>("nombreCategoria"));
        colDescripcionCategoria.setCellValueFactory(new PropertyValueFactory<>("descripcionCategoria"));



        colPresupuesto.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                mostrarInformacionPresupuesto(newValue);
            }
        });
    }

    public void setUsuarioActual(UsuarioDto usuario) {
        this.usuarioActual = usuario;
        this.presupuestosController = new PresupuestosController(usuario);
        cargarPresupuestos();
    }

    @FXML
    void ClickGuardar(ActionEvent event) {
        PresupuestoDto nuevoPresupuesto = obtenerDatosPresupuesto();
        if (nuevoPresupuesto != null) {
            presupuestosController.guardarPresupuesto(nuevoPresupuesto);
            cargarPresupuestos();
            limpiarCampos();
            mostrarMensaje("Notificación", "Presupuesto Guardado", "El presupuesto ha sido guardado correctamente", Alert.AlertType.INFORMATION);
        }
    }

    @FXML
    void ClickActualizar(ActionEvent event) {
        PresupuestoDto presupuestoSeleccionado = obtenerDatosPresupuesto();
        if (presupuestoSeleccionado != null) {
            presupuestosController.actualizarPresupuesto(presupuestoSeleccionado);
            cargarPresupuestos();
            limpiarCampos();
            mostrarMensaje("Notificación", "Actualización Correcta", "Se ha actualizado el presupuesto correctamente", Alert.AlertType.INFORMATION);
        }
    }

    @FXML
    void ClickEliminar(ActionEvent event) {
        PresupuestoDto presupuestoSeleccionado = colPresupuesto.getSelectionModel().getSelectedItem();
        if (presupuestoSeleccionado != null) {
            presupuestosController.eliminarPresupuesto(presupuestoSeleccionado.idPresupuesto());
            cargarPresupuestos();
            limpiarCampos();
        }
    }

    private PresupuestoDto obtenerDatosPresupuesto() {
        try {
            Categoria categoria = new Categoria(txtIDCategoria.getText(),
                    txtNombreCategoria.getText(),
                    txtDescripcionCategoria.getText());
            return new PresupuestoDto(
                    txtIDPresupuesto.getText(),
                    txtNombrePresupuesto.getText(),
                    Double.parseDouble(txtMontoTotal.getText()),
                    Double.parseDouble(txtMontoGastado.getText()),
                    categoria

            );
        } catch (NumberFormatException e) {
            mostrarMensaje("Error", "Datos inválidos", "Ingrese valores numéricos válidos.", Alert.AlertType.ERROR);
            return null;
        }
    }



    private void cargarPresupuestos() {
        List<PresupuestoDto> presupuestos = presupuestosController.obtenerPresupuestos();
        colPresupuesto.setItems(FXCollections.observableArrayList(presupuestos));
    }

    private void mostrarInformacionPresupuesto(PresupuestoDto presupuesto) {
        txtIDPresupuesto.setText(presupuesto.idPresupuesto());
        txtNombrePresupuesto.setText(presupuesto.nombrePresupuesto());
        txtMontoTotal.setText(String.valueOf(presupuesto.getMontoTotal()));
        txtMontoGastado.setText(String.valueOf(presupuesto.getMontoGastado()));
        txtIDCategoria.setText(presupuesto.categoria().getIdCategoria());
        txtNombreCategoria.setText(presupuesto.categoria().getNombreCategoria());
        txtDescripcionCategoria.setText(presupuesto.categoria().getDescripcionCategoria());
    }

    private void limpiarCampos() {
        txtIDPresupuesto.clear();
        txtNombrePresupuesto.clear();
        txtMontoTotal.clear();
        txtMontoGastado.clear();
        txtIDCategoria.clear();
        txtNombreCategoria.clear();
        txtDescripcionCategoria.clear();
    }

    private void mostrarMensaje(String title, String header, String content, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }

    @FXML
    public void ClickLimpiar(ActionEvent event){
        limpiarCampos();
    }

    @FXML
    public void ClickBack(ActionEvent event) {
        volver(event, usuarioActual);
    }

    public void volver(ActionEvent event, UsuarioDto usuario) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/uniquindio/finalproject/VistaCuentaDeUsuario.fxml"));
            Parent root = loader.load();
            CuentaUsuarioViewController controller = loader.getController();
            controller.setUsuarioActual(usuario);

            // Configurar y mostrar la nueva ventana
            Stage newStage = new Stage();
            newStage.setTitle("Cuenta de Usuario");
            newStage.setScene(new Scene(root));
            newStage.show();

            // Cerrar la ventana actual
            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            currentStage.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
