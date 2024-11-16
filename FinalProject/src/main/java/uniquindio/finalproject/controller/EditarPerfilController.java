package uniquindio.finalproject.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import uniquindio.finalproject.Model.Usuario;
import uniquindio.finalproject.exceptions.CampoVacioException;
import uniquindio.finalproject.exceptions.MaximoIntentosFallidosException;
import uniquindio.finalproject.global.SesionGlobal;
import javafx.fxml.FXMLLoader;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class EditarPerfilController implements Initializable {

    @FXML
    private PasswordField txtContraseña;

    @FXML
    private TextField txtEditarCorreo;

    @FXML
    private TextField txtEditarDireccion;

    @FXML
    private TextField txtEditarID;

    @FXML
    private TextField txtEditarNombre;

    @FXML
    private TextField txtEditarTelefono;

    @FXML
    private Button btnGuardarCambios;

    private Usuario usuarioActual;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        usuarioActual = SesionGlobal.usuarioActual;
        if (usuarioActual != null) {
            txtEditarID.setText(usuarioActual.getUsuarioID());
            txtEditarNombre.setText(usuarioActual.getNombre());
            txtEditarCorreo.setText(usuarioActual.getCorreo());
            txtEditarDireccion.setText(usuarioActual.getDireccion());
            txtEditarTelefono.setText(usuarioActual.getNumeroTelefono());
            txtContraseña.setText(usuarioActual.getContraseña());
        }
    }

    private int intentosFallidos = 0;

    @FXML
    void ClickGuardar(ActionEvent event) {
        try {
            if (usuarioActual == null) {
                mostrarMensaje("Error", "Usuario no encontrado", "No se ha podido actualizar los datos porque no se ha encontrado el usuario.", Alert.AlertType.ERROR);
                return;
            }

            // Incrementar el contador de intentos fallidos en cada intento
            intentosFallidos++;
            if (intentosFallidos > 5) {
                throw new MaximoIntentosFallidosException("Has alcanzado el límite máximo de intentos fallidos. No puedes realizar esta operación.");
            }

            // Validación de campos vacíos
            if (txtEditarID.getText().isEmpty()) {
                throw new CampoVacioException("El campo ID no puede estar vacío.");
            }
            if (txtEditarNombre.getText().isEmpty()) {
                throw new CampoVacioException("El campo Nombre no puede estar vacío.");
            }
            if (txtEditarCorreo.getText().isEmpty()) {
                throw new CampoVacioException("El campo Correo no puede estar vacío.");
            }
            if (txtEditarDireccion.getText().isEmpty()) {
                throw new CampoVacioException("El campo Dirección no puede estar vacío.");
            }
            if (txtEditarTelefono.getText().isEmpty()) {
                throw new CampoVacioException("El campo Teléfono no puede estar vacío.");
            }
            if (txtContraseña.getText().isEmpty()) {
                throw new CampoVacioException("El campo Contraseña no puede estar vacío.");
            }

            // Validar y realizar operación (aquí la lógica de edición del usuario)
            mostrarMensaje("Éxito", "Operación Realizada", "Se han actualizado los datos correctamente.", Alert.AlertType.INFORMATION);

        } catch (MaximoIntentosFallidosException e) {
            // Mostrar mensaje específico para intentos fallidos
            mostrarMensaje("Error", "Máximo Intentos Fallidos", e.getMessage(), Alert.AlertType.ERROR);
        } catch (CampoVacioException e) {
            mostrarMensaje("Error", "Campo Vacío", e.getMessage(), Alert.AlertType.WARNING);
        } catch (Exception e) {
            mostrarMensaje("Error", "Error inesperado", "Ocurrió un error durante la operación.", Alert.AlertType.ERROR);
            e.printStackTrace();
        }
    }

    @FXML
    public void clickVolver(ActionEvent event) {
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

    private void mostrarMensaje(String title, String header, String content, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
