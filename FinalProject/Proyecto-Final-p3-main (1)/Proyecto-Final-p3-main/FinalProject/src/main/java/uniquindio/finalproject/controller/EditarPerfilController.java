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
import uniquindio.finalproject.global.SesionGlobal;
import javafx.fxml.FXMLLoader;
import uniquindio.finalproject.persistencia.ArchivoUtil;

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

    @FXML
    void ClickGuardar(ActionEvent event) {
        if (usuarioActual != null) {
            usuarioActual.setUsuarioID(txtEditarID.getText());
            usuarioActual.setNombre(txtEditarNombre.getText());
            usuarioActual.setCorreo(txtEditarCorreo.getText());
            usuarioActual.setDireccion(txtEditarDireccion.getText());
            usuarioActual.setNumeroTelefono(txtEditarTelefono.getText());
            usuarioActual.setContraseña(txtContraseña.getText());
            ArchivoUtil.guardarRegistroLog("Usuario: "+SesionGlobal.usuarioActual.getNombre()+" Editar Perfil Controlador", 1, "clickGuardar", ("C:\\td\\Persistencia\\Log\\log.txt"));
            mostrarMensaje("Éxito", "Datos actualizados", "Se han actualizado los datos del perfil correctamente", Alert.AlertType.INFORMATION);
        } else {
            mostrarMensaje("Error", "Usuario no encontrado", "No se ha podido actualizar los datos porque no se ha encontrado el usuario.", Alert.AlertType.ERROR);
        }
    }

    @FXML
    public void clickVolver(ActionEvent event) {
        ArchivoUtil.guardarRegistroLog("Usuario: "+SesionGlobal.usuarioActual.getNombre()+" Editar Perfil Controlador", 1, "clickVolver", ("C:\\td\\Persistencia\\Log\\log.txt"));
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
