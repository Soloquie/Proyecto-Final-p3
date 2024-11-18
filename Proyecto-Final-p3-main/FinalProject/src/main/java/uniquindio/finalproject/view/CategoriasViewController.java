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
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import uniquindio.finalproject.Model.Categoria;
import uniquindio.finalproject.controller.CategoriasController;
import uniquindio.finalproject.mapping.dto.CategoriaDto;
import uniquindio.finalproject.mapping.dto.UsuarioDto;
import uniquindio.finalproject.persistencia.Persistencia;

import java.io.IOException;
import java.util.List;

public class CategoriasViewController {
    Persistencia persistencia = new Persistencia();

    @FXML
    private Button btnGuardar, btnActualizar, btnEliminar, btnLimpiar, btnBack;

    @FXML
    private TextField txtDescripcion, txtIDCategoria, txtNombreCategoria;

    @FXML
    private TableView<CategoriaDto> colCategoriasUsuario;

    @FXML
    private TableColumn<CategoriaDto, String> idCategoria, nombreCategoria, descripcionCategoria;

    private ObservableList<CategoriaDto> listaCategorias;
    private CategoriasController categoriasController;
    private UsuarioDto usuarioActual;

    @FXML
    public void initialize() {
        categoriasController = new CategoriasController();
        configurarColumnasTabla();
        configurarSeleccionTabla();
    }

    public void setUsuarioActual(UsuarioDto usuarioActual) {
        this.usuarioActual = usuarioActual;
        cargarCategorias();
    }

    private void configurarColumnasTabla() {
        idCategoria.setCellValueFactory(new PropertyValueFactory<>("idCategoria"));
        nombreCategoria.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        descripcionCategoria.setCellValueFactory(new PropertyValueFactory<>("descripcion"));
    }

    private void configurarSeleccionTabla() {
        colCategoriasUsuario.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                mostrarInformacionCategoria(newValue);
            }
        });
    }

    private void cargarCategorias() {
        List<CategoriaDto> categorias = categoriasController.obtenerCategorias(usuarioActual);
        listaCategorias = FXCollections.observableArrayList(categorias);
        colCategoriasUsuario.setItems(listaCategorias);
        colCategoriasUsuario.refresh();
    }


    @FXML
    void ClickGuardar(ActionEvent event) {
        CategoriaDto categoria = new CategoriaDto(txtIDCategoria.getText(), txtNombreCategoria.getText(), txtDescripcion.getText());
        categoriasController.guardarCategoria(usuarioActual, categoria);
        cargarCategorias();
        try {
            persistencia.guardarCategoria(CategoriaDtoACategoria(categoria));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        persistencia.guardarRegistroLog("ClickGuardarCategoria ", 1,"Se ha guardado una categoria exitosamente :"+usuarioActual.nombre());
        limpiarCampos();
    }

    private Categoria CategoriaDtoACategoria(CategoriaDto categoria) {
        return new Categoria(categoria.idCategoria(),categoria.nombre(),categoria.descripcion());
    }

    @FXML
    void ClickActualizar(ActionEvent event) {
        CategoriaDto categoriaSeleccionada = colCategoriasUsuario.getSelectionModel().getSelectedItem();
        if (categoriaSeleccionada != null) {
            CategoriaDto categoria = new CategoriaDto(txtIDCategoria.getText(), txtNombreCategoria.getText(), txtDescripcion.getText());
            categoriasController.actualizarCategoria(categoriaSeleccionada.idCategoria(), categoria, usuarioActual);
            cargarCategorias();
            persistencia.guardarRegistroLog("ClickActualizar ", 1,"Se ha actualizado una categoria exitosamente :"+usuarioActual.nombre());
            limpiarCampos();
        } else {
            persistencia.guardarRegistroLog("ClickActualizar ", 3,"Error al actualizar una categoria :"+usuarioActual.nombre());
            mostrarMensaje("Advertencia", "Actualización Fallida", "No se ha seleccionado ninguna categoría para actualizar", Alert.AlertType.WARNING);
        }
    }

    @FXML
    void ClickEliminar(ActionEvent event) {
        CategoriaDto categoriaSeleccionada = colCategoriasUsuario.getSelectionModel().getSelectedItem();
        if (categoriaSeleccionada != null) {
            categoriasController.eliminarCategoria(usuarioActual, categoriaSeleccionada.idCategoria());
            cargarCategorias();
            persistencia.guardarRegistroLog("ClickEliminar ", 1,"Se ha eliminado una categoria exitosamente :"+usuarioActual.nombre());
            limpiarCampos();
        } else {
            persistencia.guardarRegistroLog("ClickEliminar ", 3,"Error al eliminar una categoria :"+usuarioActual.nombre());
            mostrarMensaje("Advertencia", "Eliminación Fallida", "No se ha seleccionado ninguna categoría para eliminar", Alert.AlertType.WARNING);
        }
    }

    @FXML
    void ClickLimpiar(ActionEvent event) {
        limpiarCampos();
    }

    private void limpiarCampos() {
        txtIDCategoria.clear();
        txtNombreCategoria.clear();
        txtDescripcion.clear();
    }

    private void mostrarInformacionCategoria(CategoriaDto categoria) {
        txtIDCategoria.setText(categoria.idCategoria());
        txtNombreCategoria.setText(categoria.nombre());
        txtDescripcion.setText(categoria.descripcion());
    }

    private void mostrarMensaje(String titulo, String encabezado, String contenido, Alert.AlertType tipo) {
        Alert alerta = new Alert(tipo);
        alerta.setTitle(titulo);
        alerta.setHeaderText(encabezado);
        alerta.setContentText(contenido);
        alerta.showAndWait();
    }

    @FXML
    void ClickBack(ActionEvent event) {
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
