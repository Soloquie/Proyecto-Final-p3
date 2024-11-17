package uniquindio.finalproject.view;

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
import uniquindio.finalproject.controller.GestionUsuarioController;
import uniquindio.finalproject.Model.Usuario;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class GestionUsuarioViewController implements Initializable {

    private final ObservableList<Usuario> listaUsuarios = FXCollections.observableArrayList();
    private final GestionUsuarioController usuarioController = new GestionUsuarioController();
    private Usuario usuarioSeleccionado;

    @FXML
    private TextField txtId, txtNombre, txtCorreo, txtTelefono, txtDireccion, txtSaldo;
    @FXML
    private PasswordField txtContraseña;
    @FXML
    private TableView<Usuario> tableUsuarios;
    @FXML
    private TableColumn<Usuario, String> colId, colNombre, colCorreo, colTelefono, colDireccion, colSaldo;
    @FXML
    private Button btnAgregarCuentas, btnAgregarPresupuestos, btnMostrarEstadisticas, btnGuardar, btnActualizar, btnEliminar, btnLimpiar;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Obtener los usuarios del controlador y agregarlos a la lista observable
        listaUsuarios.addAll(usuarioController.obtenerUsuarios());
        // Configurar las columnas de la tabla para mostrar las propiedades de Usuario
        colId.setCellValueFactory(new PropertyValueFactory<>("usuarioID"));
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colCorreo.setCellValueFactory(new PropertyValueFactory<>("correo"));
        colTelefono.setCellValueFactory(new PropertyValueFactory<>("numeroTelefono"));
        colDireccion.setCellValueFactory(new PropertyValueFactory<>("direccion"));
        colSaldo.setCellValueFactory(new PropertyValueFactory<>("saldoTotal"));
        // Configurar la tabla para mostrar los usuarios
        tableUsuarios.setItems(listaUsuarios);

        // Listener para manejar la selección de usuario en la tabla
        tableUsuarios.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            usuarioSeleccionado = newValue;
            mostrarInformacionUsuario(usuarioSeleccionado);
        });
    }

    private void mostrarInformacionUsuario(Usuario usuario) {
        if (usuario != null) {
            txtId.setText(usuario.getUsuarioID());
            txtNombre.setText(usuario.getNombre());
            txtCorreo.setText(usuario.getCorreo());
            txtTelefono.setText(usuario.getNumeroTelefono());
            txtDireccion.setText(usuario.getDireccion());
            txtSaldo.setText(usuario.getSaldoTotal().toString());
        }
    }

    @FXML
    void ClickGuardar(ActionEvent event) {
        Usuario usuario = new Usuario(
                txtId.getText(),
                txtNombre.getText(),
                txtCorreo.getText(),
                txtTelefono.getText(),
                txtDireccion.getText(),
                Double.valueOf(txtSaldo.getText()),
                txtContraseña.getText()
        );
        usuarioController.guardarUsuario(usuario);
        listaUsuarios.add(usuario);
        limpiarCampos();
    }

    @FXML
    void ClickActualizar(ActionEvent event) {
        usuarioSeleccionado.setNombre(txtNombre.getText());
        usuarioSeleccionado.setCorreo(txtCorreo.getText());
        usuarioSeleccionado.setNumeroTelefono(txtTelefono.getText());
        usuarioSeleccionado.setDireccion(txtDireccion.getText());
        usuarioSeleccionado.setSaldoTotal(Double.valueOf(txtSaldo.getText()));
        usuarioController.actualizarUsuario(usuarioSeleccionado);
        tableUsuarios.refresh();
        limpiarCampos();
    }

    @FXML
    void ClickEliminar(ActionEvent event) {
        usuarioController.eliminarUsuario(usuarioSeleccionado);
        listaUsuarios.remove(usuarioSeleccionado);
        limpiarCampos();
    }

    @FXML
    public void ClickLimpiar(ActionEvent event) {
        limpiarCampos();
    }

    private void limpiarCampos() {
        txtId.clear();
        txtNombre.clear();
        txtCorreo.clear();
        txtTelefono.clear();
        txtDireccion.clear();
        txtSaldo.clear();
        txtContraseña.clear();
    }

    private void mostrarMensaje(String titulo, String mensaje, Alert.AlertType tipo) {
        Alert alerta = new Alert(tipo);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }

    @FXML
    public void ClickAgregarCuentas(ActionEvent event) {
        abrirVista("/uniquindio/finalproject/VistaAdicionDeCuentas.fxml", event, "Adición de Cuentas");
    }

    @FXML
    public void ClickPresupuestos(ActionEvent event) {
        abrirVista("/uniquindio/finalproject/VistaAdicionDePresupuestos.fxml", event, "Adición de Presupuestos");
    }

    private void abrirVista(String vista, ActionEvent event, String titulo) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource(vista));
            Stage newStage = new Stage();
            newStage.setTitle(titulo);
            newStage.setScene(new Scene(root));
            newStage.show();
            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            currentStage.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void clickMostrarEstadisticas(ActionEvent event) {
    }

    @FXML
    public void clickCerrarSesion(ActionEvent event) {
        abrirVista("/uniquindio/finalproject/VistaLogin.fxml", event, "Adicion de Cuentas");
    }
}
