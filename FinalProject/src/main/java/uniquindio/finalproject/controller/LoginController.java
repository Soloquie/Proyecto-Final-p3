package uniquindio.finalproject.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.MenuButton;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import uniquindio.finalproject.BilleteraVirtual;
import uniquindio.finalproject.Model.Administrador;
import uniquindio.finalproject.Model.Usuario;
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
        String username = txtIngresarUsuario.getText();
        String password = txtIngresarContraseña.getText();
        Usuario usuarioSeleccionado = validarCredenciales(username, password);
        if (usuarioSeleccionado != null) {
            SesionGlobal.usuarioActual = usuarioSeleccionado;
            if (usuarioSeleccionado instanceof Administrador) {
                abrirVista("/uniquindio/finalproject/VistaGestionUsuario.fxml", event);
            } else {
                abrirVista("/uniquindio/finalproject/VistaCuentaDeUsuario.fxml", event);
            }
        } else {
            System.out.println("Credenciales incorrectas.");
        }
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
