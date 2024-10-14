package uniquindio.finalproject.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import uniquindio.finalproject.global.SesionGlobal;

import java.io.IOException;

public class CategoriasController {

    @FXML
    private Button btnActualizar;

    @FXML
    private Button btnEliminar;

    @FXML
    private Button btnGuardar;

    @FXML
    private Button btnLimpiar;

    @FXML
    private TextField txtDescripcion;

    @FXML
    private TextField txtIDCategoria;

    @FXML
    private TextField txtNombreCategoria;

    @FXML
    private TableView<Categoria> colCategoriasUsuario;

    @FXML
    private TableColumn<Categoria, String> idCategoria;

    @FXML
    private TableColumn<Categoria, String> nombreCategoria;

    @FXML
    private TableColumn<Categoria, String> descripcionCategoria;

    private ObservableList<Categoria> listaCategorias;

    @FXML
    public void initialize() {
        idCategoria.setCellValueFactory(new PropertyValueFactory<>("idCategoria"));
        nombreCategoria.setCellValueFactory(new PropertyValueFactory<>("nombreCategoria"));
        descripcionCategoria.setCellValueFactory(new PropertyValueFactory<>("descripcionCategoria"));
        listaCategorias = FXCollections.observableArrayList(SesionGlobal.usuarioActual.getCategoriasAsociadas());
        colCategoriasUsuario.setItems(listaCategorias);
        colCategoriasUsuario.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                mostrarInformacionCategoria(newValue);
            }
        });
    }

    @FXML
    void ClickGuardar(ActionEvent event) {
        String id = txtIDCategoria.getText();
        String nombre = txtNombreCategoria.getText();
        String descripcion = txtDescripcion.getText();
        if (id.isEmpty() || nombre.isEmpty() || descripcion.isEmpty()) {
            mostrarMensaje("Error", "Campos Vacíos", "Por favor llene todos los campos", Alert.AlertType.ERROR);
            return;
        }
        Categoria nuevaCategoria = new Categoria(id, nombre, descripcion);
        SesionGlobal.usuarioActual.añadirCategoria(nuevaCategoria);
        actualizarTabla();
        limpiarCampos();
    }

    @FXML
    void ClickActualizar(ActionEvent event) {
        Categoria categoriaSeleccionada = colCategoriasUsuario.getSelectionModel().getSelectedItem();

        if (categoriaSeleccionada != null) {
            categoriaSeleccionada.setIdCategoria(txtIDCategoria.getText());
            categoriaSeleccionada.setNombre(txtNombreCategoria.getText());
            categoriaSeleccionada.setDescripcion(txtDescripcion.getText());
            actualizarTabla();
            limpiarCampos();
        } else {
            mostrarMensaje("Advertencia", "Actualización Fallida", "No se ha seleccionado ninguna categoría para actualizar", Alert.AlertType.WARNING);
        }
    }

    @FXML
    void ClickEliminar(ActionEvent event) {
        Categoria categoriaSeleccionada = colCategoriasUsuario.getSelectionModel().getSelectedItem();
        if (categoriaSeleccionada != null) {
            SesionGlobal.usuarioActual.getCategoriasAsociadas().remove(categoriaSeleccionada);
            actualizarTabla();
            limpiarCampos();
        } else {
            mostrarMensaje("Advertencia", "Eliminación Fallida", "No se ha seleccionado ninguna categoría para eliminar", Alert.AlertType.WARNING);
        }
    }

    @FXML
    void ClickLimpiar(ActionEvent event) {
        limpiarCampos();
    }

    private void actualizarTabla() {
        listaCategorias.setAll(SesionGlobal.usuarioActual.getCategoriasAsociadas());
        colCategoriasUsuario.refresh();  // Refrescar la tabla después de realizar cambios
    }

    private void mostrarInformacionCategoria(Categoria categoria) {
        txtIDCategoria.setText(categoria.getIdCategoria());
        txtNombreCategoria.setText(categoria.getNombreCategoria());
        txtDescripcion.setText(categoria.getDescripcionCategoria());
    }

    private void limpiarCampos() {
        txtIDCategoria.clear();
        txtNombreCategoria.clear();
        txtDescripcion.clear();
    }

    private void mostrarMensaje(String titulo, String encabezado, String contenido, Alert.AlertType tipo) {
        Alert alerta = new Alert(tipo);
        alerta.setTitle(titulo);
        alerta.setHeaderText(encabezado);
        alerta.setContentText(contenido);
        alerta.showAndWait();
    }

    @FXML
    public void ClickBack(ActionEvent event) {
        abrirVista("/uniquindio/finalproject/VistaCuentaDeUsuario.fxml", event);
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
