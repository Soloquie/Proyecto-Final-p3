package uniquindio.finalproject.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.MenuButton;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import uniquindio.finalproject.BilleteraVirtual;
import uniquindio.finalproject.Model.Administrador;
import uniquindio.finalproject.Model.Usuario;
import uniquindio.finalproject.exceptions.UsuarioNoEncontradoException;
import uniquindio.finalproject.global.SesionGlobal;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class LoginController implements Initializable {
    Usuario usuarioSeleccionado;
    SesionGlobal sesionGlobal = new SesionGlobal();


    @FXML
    private Button btnIngresarLogin;

    @FXML
    private AnchorPane btnTipoDeUsuario;

    @FXML
    private MenuButton btnTipoUsuario;

    @FXML
    private TextField txtIngresarContraseña;

    @FXML
    private TextField txtIngresarUsuario;

    @FXML
    void ClickIngresar(ActionEvent event) {
        try {
            // Obtener datos ingresados
            String username = txtIngresarUsuario.getText();
            String password = txtIngresarContraseña.getText();

            // Validar credenciales
            Usuario usuarioSeleccionado = validarCredenciales(username, password);

            // Si el usuario no es encontrado, lanzar la excepción
            if (usuarioSeleccionado == null) {
                throw new UsuarioNoEncontradoException("El usuario con el nombre de usuario " + username + " no fue encontrado.");
            }

            // Establecer la sesión global con el usuario seleccionado
            SesionGlobal.usuarioActual = usuarioSeleccionado;

            // Verificar el tipo de usuario y abrir la vista correspondiente
            if (usuarioSeleccionado instanceof Administrador) {
                abrirVista("/uniquindio/finalproject/VistaGestionUsuario.fxml", event);
            } else {
                abrirVista("/uniquindio/finalproject/VistaCuentaDeUsuario.fxml", event);
            }

        } catch (UsuarioNoEncontradoException e) {
            // Manejo específico de la excepción
            mostrarMensaje("Error", "Usuario No Encontrado", e.getMessage(), Alert.AlertType.ERROR);
        } catch (Exception e) {
            // Manejo genérico de excepciones
            mostrarMensaje("Error", "Error inesperado", "Ocurrió un error al intentar iniciar sesión.", Alert.AlertType.ERROR);
            e.printStackTrace();
        }
    }

    private void mostrarMensaje(String titulo, String encabezado, String contenido, Alert.AlertType tipo) {
        Alert alerta = new Alert(tipo);
        alerta.setTitle(titulo);
        alerta.setHeaderText(encabezado);
        alerta.setContentText(contenido);
        alerta.showAndWait();
    }

    private Usuario validarCredenciales(String username, String password) {
        for (Usuario usuario : SesionGlobal.usuarios) {
            if (usuario.getUsuarioID().equals(username) && usuario.getContraseña().equals(password)) {
                return usuario;
            }
        }
        return null;
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


    @FXML
    void ClickTipoUsuario(ActionEvent event) {

    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }
}
