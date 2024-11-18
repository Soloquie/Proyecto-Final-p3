package uniquindio.finalproject.view;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import uniquindio.finalproject.controller.EditarPerfilController;
import uniquindio.finalproject.mapping.dto.UsuarioDto;
import uniquindio.finalproject.persistencia.Persistencia;

import java.io.IOException;

public class EditarPerfilViewController {
    Persistencia persistencia = new Persistencia();

    @FXML
    private PasswordField txtContraseña;
    @FXML
    private TextField txtEditarCorreo, txtEditarDireccion, txtEditarID, txtEditarNombre, txtEditarTelefono, txtSaldo;
    @FXML
    private Button btnGuardarCambios;

    private EditarPerfilController perfilController;
    private UsuarioDto usuarioActual;

    public void setUsuarioActual(UsuarioDto usuario) {
        perfilController = new EditarPerfilController(usuario);
        usuarioActual = perfilController.obtenerUsuarioActual();

        // Rellenar los campos con los datos de usuarioActual
        if (usuarioActual != null) {
            txtEditarID.setText(usuarioActual.usuarioID());
            txtEditarNombre.setText(usuarioActual.nombre());
            txtEditarCorreo.setText(usuarioActual.correo());
            txtEditarDireccion.setText(usuarioActual.direccion());
            txtEditarTelefono.setText(usuarioActual.numeroTelefono());
            txtContraseña.setText(usuarioActual.contraseña());
            txtSaldo.setText(String.valueOf(usuarioActual.saldoTotal()));
        }
    }

    @FXML
    void ClickGuardar(ActionEvent event) {
        if (usuarioActual != null) {
            UsuarioDto usuarioActualizado = new UsuarioDto(
                    txtEditarID.getText(),
                    txtEditarNombre.getText(),
                    txtEditarCorreo.getText(),
                    txtEditarTelefono.getText(),
                    txtEditarDireccion.getText(),
                    Double.valueOf(txtSaldo.getText()),
                    txtContraseña.getText()
            );

            boolean exito = perfilController.guardarCambios(usuarioActualizado);
            if (exito) {
                persistencia.guardarRegistroLog("ClickGuardar", 1, "EditarPerfil. Se ha editado el perfil exitosamente :"+usuarioActual.nombre());
            }
            else {
                persistencia.guardarRegistroLog("ClickGuardar", 3, "EditarPerfil. No se ha podido editar el perfil :"+usuarioActual.nombre());
            }
            mostrarMensaje(exito ? "Éxito" : "Error",
                    exito ? "Datos actualizados" : "Actualización fallida",
                    exito ? "Perfil actualizado correctamente" : "No se pudo actualizar el perfil",
                    exito ? Alert.AlertType.INFORMATION : Alert.AlertType.ERROR);
        }
    }

    private void mostrarMensaje(String title, String header, String content, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }

    @FXML
    public void clickVolver(ActionEvent event) {
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
