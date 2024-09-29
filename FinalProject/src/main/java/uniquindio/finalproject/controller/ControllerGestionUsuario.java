package uniquindio.finalproject.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import uniquindio.finalproject.Model.Administrador;
import uniquindio.finalproject.Model.Usuario ;
import uniquindio.finalproject.exceptions.ActualizarUsuarioException;
import uniquindio.finalproject.exceptions.UsuarioNoSeleccionadoException;

import java.net.URL;
import java.util.ResourceBundle;

public class ControllerGestionUsuario implements Initializable {
    Administrador admin = new Administrador();
    Usuario usuarioSeleccionado;
    private final ObservableList<Usuario> usuarioList = FXCollections.observableArrayList();



    @FXML
    private Button btnGuardar;

    @FXML
    private Button btnActualizar;

    @FXML
    private Button btnEliminar;

    @FXML
    private Button btnLimpiar;

    @FXML
    public Button btnAgregarPresupuestos;

    @FXML
    public Button btnAgregarCuentas;

    @FXML
    private TextField txtCorreo;

    @FXML
    private TextField txtDireccion;

    @FXML
    private TextField txtId;

    @FXML
    private TextField txtNombre;

    @FXML
    private TextField txtTelefono;

    @FXML
    public TextField txtSaldo;

    @FXML
    private TableView<Usuario> tableUsuarios;

    @FXML
    private TableColumn<Usuario, String> colId;

    @FXML
    private TableColumn<Usuario, String> colNombre;

    @FXML
    private TableColumn<Usuario, String> colCorreo;

    @FXML
    private TableColumn<Usuario, String> colTelefono;

    @FXML
    private TableColumn<Usuario, String> colDireccion;

    @FXML
    public TableColumn<Usuario, String> colSaldo;

    @FXML
    public Button btnMostrarEstadisticas;

    private ObservableList<Usuario> listaUsuarios;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        listaUsuarios = FXCollections.observableArrayList(
                new Usuario("1", "Juan Perez", "juan@example.com", "12345", "Calle Falsa 123", 21.0),
                new Usuario("2", "Maria Lopez", "maria@example.com", "67890", "Avenida Siempre Viva", 21.0),
                new Usuario("3", "Carlos Sanchez", "carlos@example.com", "11223", "Boulevard Los Olivos", 21.0)
        );

        colId.setCellValueFactory(new PropertyValueFactory<>("usuarioID"));
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colCorreo.setCellValueFactory(new PropertyValueFactory<>("correo"));
        colTelefono.setCellValueFactory(new PropertyValueFactory<>("numeroTelefono"));
        colDireccion.setCellValueFactory(new PropertyValueFactory<>("direccion"));
        colSaldo.setCellValueFactory(new PropertyValueFactory<>("saldoTotal"));

        tableUsuarios.setItems(listaUsuarios);

        tableUsuarios.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                usuarioSeleccionado = newValue;
                mostrarInformacionUsuario(newValue);
            }
        });
    }

    private void mostrarInformacionUsuario(Usuario usuario) {
        txtId.setText(usuario.getUsuarioID());
        txtNombre.setText(usuario.getNombre());
        txtCorreo.setText(usuario.getCorreo());
        txtTelefono.setText(usuario.getNumeroTelefono());
        txtDireccion.setText(usuario.getDireccion());
        txtSaldo.setText(usuario.getSaldoTotal().toString());
    }


    @FXML
    void ClickGuardar(ActionEvent event) {
        try {
            if (txtId.getText().isEmpty() || txtNombre.getText().isEmpty() || txtCorreo.getText().isEmpty() ||
                    txtTelefono.getText().isEmpty() || txtDireccion.getText().isEmpty()) {
                mostrarMensaje("Error", "Error guardado", "Todos los campos deben estar completos antes de guardar.", Alert.AlertType.ERROR);
                throw new ActualizarUsuarioException("Todos los campos deben estar completos antes de guardar.");
            }

            String id = txtId.getText();
            String nombre = txtNombre.getText();
            String correo = txtCorreo.getText();
            String telefono = txtTelefono.getText();
            String direccion = txtDireccion.getText();
            String saldo = txtSaldo.getText();

            Usuario nuevoUsuario = new Usuario(id, nombre, correo, telefono, direccion, Double.valueOf(saldo));
            listaUsuarios.add(nuevoUsuario);
            limpiarCampos();
            mostrarMensaje("Notificacion", "Guardado Correcto", "Se ha guardado al usuario correctamente", Alert.AlertType.INFORMATION);
            System.out.println("Usuario guardado con éxito.");

        } catch (ActualizarUsuarioException e) {
            mostrarMensaje("Error", "Error eliminacion", "Error al guardar el usuario", Alert.AlertType.ERROR);
            System.err.println("Error al guardar: " + e.getMessage());
        } catch (Exception e) {
            mostrarMensaje("Error", "Error guardado", "Error inesperado al guardar el usuario", Alert.AlertType.ERROR);
            System.err.println("Ocurrió un error inesperado al guardar el usuario: " + e.getMessage());
        }
    }


    @FXML
    void ClickEliminar(ActionEvent event) {
        try {
            // Verificar que se haya seleccionado un usuario
            if (usuarioSeleccionado == null) {
                throw new UsuarioNoSeleccionadoException("Debe seleccionar un usuario para eliminar.");
            }

            listaUsuarios.remove(usuarioSeleccionado);
            mostrarMensaje("Notificacion","Usuario eliminado", "Usuario eliminado con exito", Alert.AlertType.INFORMATION);
            System.out.println("Usuario eliminado con éxito.");
            limpiarCampos();

        } catch (UsuarioNoSeleccionadoException e) {
            mostrarMensaje("Error", "Error eliminacion", "Debe seleccionar un usuario para eliminar", Alert.AlertType.ERROR);
            System.err.println("Error al eliminar: " + e.getMessage());
        } catch (Exception e) {
            mostrarMensaje("Error", "Error eliminacion", "Ocurrio un error inesperado", Alert.AlertType.ERROR);
            System.err.println("Ocurrió un error inesperado al eliminar el usuario: " + e.getMessage());
        }
    }

    @FXML
    void ClickLimpiar(ActionEvent event) {
        limpiarCampos();
        System.out.println("Limpiar acción ejecutada");
    }



    private void limpiarCampos() {
        txtId.clear();
        txtNombre.clear();
        txtCorreo.clear();
        txtTelefono.clear();
        txtDireccion.clear();
        txtSaldo.clear();
    }

    @FXML
    public void ClickActualizar(ActionEvent actionEvent) {
        try {
            actualizarUsuario(); // Intentar actualizar el usuario
            mostrarMensaje("Notificacion", "Actualizacion Correcta", "Se ha actualizado al usuario correctamente", Alert.AlertType.INFORMATION);
            System.out.println("Usuario actualizado exitosamente.");
        } catch (UsuarioNoSeleccionadoException | ActualizarUsuarioException e) {
            mostrarMensaje("Error", "Error actualizacion", "No se ha podido actualizar al usuario", Alert.AlertType.ERROR);
            System.out.println(e.getMessage());
        }
    }


    private void actualizarUsuario() throws UsuarioNoSeleccionadoException, ActualizarUsuarioException {
        if (usuarioSeleccionado == null) {
            throw new UsuarioNoSeleccionadoException("No se ha seleccionado ningún usuario.");
        }

        String nuevoId = txtId.getText();
        String nuevoNombre = txtNombre.getText();
        String nuevoCorreo = txtCorreo.getText();
        String nuevoTelefono = txtTelefono.getText();
        String nuevaDireccion = txtDireccion.getText();
        String nuevoSaldo = txtSaldo.getText();

        usuarioSeleccionado.setUsuarioID(nuevoId);
        usuarioSeleccionado.setNombre(nuevoNombre);
        usuarioSeleccionado.setCorreo(nuevoCorreo);
        usuarioSeleccionado.setNumeroTelefono(nuevoTelefono);
        usuarioSeleccionado.setDireccion(nuevaDireccion);
        usuarioSeleccionado.setSaldoTotal(Double.valueOf(nuevoSaldo));

        int index = listaUsuarios.indexOf(usuarioSeleccionado);
        if (index != -1) {
            listaUsuarios.set(index, usuarioSeleccionado);
            limpiarCampos(); // Limpiar los campos después de actualizar
        } else {
            throw new ActualizarUsuarioException("Error al actualizar el usuario en la lista.");
        }
    }


    private void mostrarMensaje(String title, String header, String content, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }

    public void clickMostrarEstadisticas(ActionEvent actionEvent) {
    }

    public void ClickPresupuestos(ActionEvent actionEvent) {
    }

    public void ClickAgregarCuentas(ActionEvent actionEvent) {
    }
}


