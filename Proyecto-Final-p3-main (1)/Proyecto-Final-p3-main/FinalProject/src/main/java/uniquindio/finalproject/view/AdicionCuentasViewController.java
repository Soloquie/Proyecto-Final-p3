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
import javafx.stage.Stage;
import uniquindio.finalproject.Model.Usuario;
import uniquindio.finalproject.controller.AdicionCuentasController;
import uniquindio.finalproject.mapping.dto.UsuarioDto;

import java.io.IOException;
import java.util.List;

public class AdicionCuentasViewController {

    @FXML
    public Button BtnLimpiar;

    @FXML
    public Button BtbBack;

    @FXML
    public ComboBox<Usuario> choiceBoxUsuario;

    @FXML
    public TextField txtSaldoCuenta;

    @FXML
    private Button btnAgregarCuenta;

    @FXML
    private MenuButton btnTipoDeCuenta;

    @FXML
    private TextField txtIdCuenta;

    @FXML
    private TextField txtIdNombreBanco;

    @FXML
    private TextField txtNumeroCuenta;

    private AdicionCuentasController adicionCuentasController;

    public AdicionCuentasViewController() {
        adicionCuentasController = new AdicionCuentasController();
    }

    @FXML
    public void initialize() {
        cargarTiposDeCuenta();
        cargarUsuarios();
    }

    private void cargarTiposDeCuenta() {
        adicionCuentasController.getTiposCuenta().forEach(tipo -> {
            MenuItem item = new MenuItem(tipo);
            item.setOnAction(e -> btnTipoDeCuenta.setText(tipo));
            btnTipoDeCuenta.getItems().add(item);
        });
    }

    private void cargarUsuarios() {
        List<Usuario> usuarios = adicionCuentasController.obtenerUsuarios();
        if (usuarios != null) {
            // Convertir la lista de usuarios en una ObservableList de Usuario
            ObservableList<Usuario> observableUsuarios = FXCollections.observableArrayList(usuarios);

            // Asignar la lista de usuarios al ChoiceBox
            choiceBoxUsuario.setItems(observableUsuarios);

            // Mostrar solo el nombre del usuario en el ChoiceBox
            choiceBoxUsuario.setCellFactory(param -> new ListCell<>() {
                @Override
                protected void updateItem(Usuario usuario, boolean empty) {
                    super.updateItem(usuario, empty);
                    setText(empty || usuario == null ? null : usuario.getNombre());
                }
            });

            // Mostrar el nombre del usuario seleccionado
            choiceBoxUsuario.setButtonCell(new ListCell<>() {
                @Override
                protected void updateItem(Usuario usuario, boolean empty) {
                    super.updateItem(usuario, empty);
                    setText(empty || usuario == null ? null : usuario.getNombre());
                }
            });
        } else {
            mostrarMensaje("Error", "Carga de Usuarios", "No se pudieron cargar los usuarios del servidor.", Alert.AlertType.ERROR);
        }
    }



    @FXML
    void ClickAgregarCuenta(ActionEvent event) {
        Usuario usuarioSeleccionado = choiceBoxUsuario.getValue();
        if (usuarioSeleccionado == null) {
            mostrarMensaje("Error", "Usuario no seleccionado", "Debe seleccionar un usuario para agregar la cuenta.", Alert.AlertType.ERROR);
            return;
        }

        boolean resultado = adicionCuentasController.guardarCuenta(
                txtIdCuenta.getText(),
                txtIdNombreBanco.getText(),
                txtNumeroCuenta.getText(),
                btnTipoDeCuenta.getText(),
                usuarioSeleccionado,
                Double.parseDouble(txtSaldoCuenta.getText())
        );

        if (resultado) {
            mostrarMensaje("Notificación", "Cuenta Agregada", "Se ha agregado la cuenta correctamente", Alert.AlertType.INFORMATION);
            limpiarCampos();
        } else {
            mostrarMensaje("Error", "Error al Agregar Cuenta", "No se pudo agregar la cuenta. Verifique los datos o la conexión al servidor.", Alert.AlertType.ERROR);
        }
    }

    @FXML
    void ClickBack(ActionEvent event) {
        abrirVista("/uniquindio/finalproject/VistaGestionUsuario.fxml", event);
    }

    @FXML
    void ClickLimpiar(ActionEvent event) {
        limpiarCampos();
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

    private void limpiarCampos() {
        txtIdCuenta.clear();
        txtIdNombreBanco.clear();
        txtNumeroCuenta.clear();
        btnTipoDeCuenta.setText("Seleccione tipo de cuenta");
        txtSaldoCuenta.clear();
        choiceBoxUsuario.getSelectionModel().clearSelection();
    }

    private void mostrarMensaje(String titulo, String encabezado, String contenido, Alert.AlertType tipo) {
        Alert alerta = new Alert(tipo);
        alerta.setTitle(titulo);
        alerta.setHeaderText(encabezado);
        alerta.setContentText(contenido);
        alerta.showAndWait();
    }

}
