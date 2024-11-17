package uniquindio.finalproject.view;

import javafx.collections.FXCollections;
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
import uniquindio.finalproject.Model.Categoria;
import uniquindio.finalproject.Model.Presupuesto;
import uniquindio.finalproject.Model.Usuario;
import uniquindio.finalproject.global.SesionGlobal;
import uniquindio.finalproject.persistencia.Persistencia;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class PresupuestosController implements Initializable {

    @FXML
    private Button BtbBack;

    @FXML
    private Button btnActualizar;

    @FXML
    private Button btnCrear;

    @FXML
    private Button btnEliminar;

    @FXML
    private Button btnLimpiar;

    @FXML
    private TextField txtDescripcionCategoria;

    @FXML
    private TextField txtIDCategoria;

    @FXML
    private TextField txtIDPresupuesto;

    @FXML
    private TextField txtMontoGastado;

    @FXML
    private TextField txtMontoTotal;

    @FXML
    private TextField txtNombreCategoria;

    @FXML
    private TextField txtNombrePresupuesto;

    @FXML
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Usuario usuarioActual = SesionGlobal.usuarioActual;
        if (usuarioActual != null) {
            colPresupuesto.setItems(FXCollections.observableArrayList(usuarioActual.getPresupuestos()));
            colIdPresupuesto.setCellValueFactory(new PropertyValueFactory<>("idPresupuesto"));
            colNombre.setCellValueFactory(new PropertyValueFactory<>("nombrePresupuesto"));
            colMonto.setCellValueFactory(new PropertyValueFactory<>("montoTotal"));
            colMontoGastado.setCellValueFactory(new PropertyValueFactory<>("montoGastado"));
            colIdCategoria.setCellValueFactory(new PropertyValueFactory<>("idCategoria"));
            colNombreCategoria.setCellValueFactory(new PropertyValueFactory<>("nombreCategoria"));
            colDescripcionCategoria.setCellValueFactory(new PropertyValueFactory<>("descripcionCategoria"));
        }

        colPresupuesto.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                mostrarInformacionPresupuesto(newValue);
            }
        });
    }


    @FXML
    void ClickActualizar(ActionEvent event) {
        actualizarPresupuesto(); // Intentar actualizar el presupuesto
        mostrarMensaje("Notificación", "Actualización Correcta", "Se ha actualizado el presupuesto correctamente", Alert.AlertType.INFORMATION);
        System.out.println("Presupuesto actualizado exitosamente.");
    }


    @FXML
    void ClickBack(ActionEvent event) {
        abrirVista("/uniquindio/finalproject/VistaCuentaDeUsuario.fxml", event);
    }

    @FXML
    void ClickEliminar(ActionEvent event) {
        Presupuesto presupuestoSeleccionado = colPresupuesto.getSelectionModel().getSelectedItem();
        if (presupuestoSeleccionado != null) {
            SesionGlobal.usuarioActual.getPresupuestos().remove(presupuestoSeleccionado);
            colPresupuesto.setItems(FXCollections.observableArrayList(SesionGlobal.usuarioActual.getPresupuestos()));
            limpiarCampos();
        } else {
            System.out.println("No se ha seleccionado ningún presupuesto para eliminar.");
        }
    }


    @FXML
    void ClickGuardar(ActionEvent event) {
        try {
            // Obtener los valores de los campos de texto
            String idPresupuesto = txtIDPresupuesto.getText();
            String nombrePresupuesto = txtNombrePresupuesto.getText();
            Double montoTotal = Double.parseDouble(txtMontoTotal.getText());
            Double montoGastado = Double.parseDouble(txtMontoGastado.getText());
            String idCategoria = txtIDCategoria.getText();
            String nombreCategoria = txtNombreCategoria.getText();
            String descripcionCategoria = txtDescripcionCategoria.getText();

            // Validar que todos los campos estén completos
            if (idPresupuesto.isEmpty() || nombrePresupuesto.isEmpty() || idCategoria.isEmpty() ||
                    nombreCategoria.isEmpty() || descripcionCategoria.isEmpty() || montoTotal == null || montoGastado == null) {
                mostrarMensaje("Error", "Campos Vacíos", "Por favor llene todos los campos", Alert.AlertType.ERROR);
                return;
            }

            Categoria categoria = new Categoria(idCategoria, nombreCategoria, descripcionCategoria);
            Presupuesto nuevoPresupuesto = new Presupuesto(idPresupuesto, nombrePresupuesto, montoTotal, montoGastado, categoria);

            SesionGlobal.usuarioActual.añadirPresupuesto(nuevoPresupuesto);

            Persistencia persistencia = new Persistencia();
            persistencia.guardarCategoria(categoria);     // Guardar la categoría
            persistencia.guardarPresupuesto(nuevoPresupuesto);  // Guardar el presupuesto

            // Actualizar la tabla con los presupuestos actualizados
            colPresupuesto.setItems(FXCollections.observableArrayList(SesionGlobal.usuarioActual.getPresupuestos()));

            // Limpiar los campos después de guardar
            limpiarCampos();

            // Mostrar mensaje de éxito
            mostrarMensaje("Notificación", "Presupuesto Guardado", "El presupuesto ha sido guardado correctamente", Alert.AlertType.INFORMATION);

        } catch (NumberFormatException e) {
            mostrarMensaje("Error", "Error en formato numérico", "Ingrese valores numéricos válidos para los montos.", Alert.AlertType.ERROR);
        } catch (IOException e) {
            mostrarMensaje("Error", "Error al guardar", "No se pudo guardar el presupuesto en el archivo.", Alert.AlertType.ERROR);
            e.printStackTrace();
        }
    }

    @FXML
    void ClickLimpiar(ActionEvent event) {
        limpiarCampos();
    }

    @FXML
    private TableColumn<Presupuesto, Categoria> colDescripcionCategoria;

    @FXML
    private TableColumn<Presupuesto, Categoria> colIdCategoria;

    @FXML
    private TableColumn<Presupuesto, Categoria> colIdPresupuesto;

    @FXML
    private TableColumn<?, ?> colMonto;

    @FXML
    private TableColumn<?, ?> colMontoGastado;

    @FXML
    private TableColumn<?, ?> colNombre;

    @FXML
    private TableColumn<?, ?> colNombreCategoria;

    @FXML
    private TableView<Presupuesto> colPresupuesto;

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
        txtDescripcionCategoria.clear();
        txtIDPresupuesto.clear();
        txtIDCategoria.clear();
        txtMontoGastado.clear();
        txtMontoTotal.clear();
        txtNombreCategoria.clear();
        txtNombrePresupuesto.clear();
    }

    private void mostrarInformacionPresupuesto(Presupuesto presupuesto) {
        txtNombrePresupuesto.setText(presupuesto.getNombrePresupuesto());
        txtNombreCategoria.setText(presupuesto.getCategoria().getNombreCategoria());
        txtIDCategoria.setText(presupuesto.getCategoria().getIdCategoria());
        txtIDPresupuesto.setText(presupuesto.getIdPresupuesto());
        txtDescripcionCategoria.setText(presupuesto.getCategoria().getDescripcionCategoria());
        txtMontoTotal.setText(String.valueOf(presupuesto.getMontoTotal()));
        txtMontoGastado.setText(String.valueOf(presupuesto.getMontoGastado()));
    }

    private void actualizarPresupuesto() {
        Presupuesto presupuestoSeleccionado = colPresupuesto.getSelectionModel().getSelectedItem();

        if (presupuestoSeleccionado == null) {
            System.out.println("No se ha podido actualizar");
        }

        // Obtener los valores de los campos de texto
        String nuevoIdPresupuesto = txtIDPresupuesto.getText();
        String nuevoNombrePresupuesto = txtNombrePresupuesto.getText();
        double nuevoMontoTotal = Double.parseDouble(txtMontoTotal.getText());
        double nuevoMontoGastado = Double.parseDouble(txtMontoGastado.getText());

        // Crear una nueva categoría con los datos actualizados
        Categoria nuevaCategoria = new Categoria(
                txtIDCategoria.getText(),
                txtNombreCategoria.getText(),
                txtDescripcionCategoria.getText()
        );

        // Actualizar el presupuesto seleccionado con los nuevos datos
        presupuestoSeleccionado.setIdPresupuesto(nuevoIdPresupuesto);
        presupuestoSeleccionado.setNombre(nuevoNombrePresupuesto);
        presupuestoSeleccionado.setMontoTotal(nuevoMontoTotal);
        presupuestoSeleccionado.setMontoGastado(nuevoMontoGastado);
        presupuestoSeleccionado.setCategoria(nuevaCategoria);

        int index = SesionGlobal.usuarioActual.getPresupuestos().indexOf(presupuestoSeleccionado);
        if (index != -1) {
            SesionGlobal.usuarioActual.getPresupuestos().set(index, presupuestoSeleccionado);
            colPresupuesto.refresh(); // Refrescar la tabla
            limpiarCampos(); // Limpiar los campos después de actualizar
        }
    }


    private void mostrarMensaje(String title, String header, String content, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }

}
