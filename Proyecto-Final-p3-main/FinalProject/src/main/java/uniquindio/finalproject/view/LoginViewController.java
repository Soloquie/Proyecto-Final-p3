package uniquindio.finalproject.view;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import uniquindio.finalproject.controller.LoginController;
import uniquindio.finalproject.mapping.dto.UsuarioDto;
import uniquindio.finalproject.persistencia.Persistencia;

import java.io.IOException;

public class LoginViewController {
    private final LoginController loginControllerService = new LoginController();

    @FXML
    private Button btnIngresarLogin;

    @FXML
    private TextField txtIngresarContraseña;

    @FXML
    private TextField txtIngresarUsuario;

    @FXML
    void ClickIngresar(ActionEvent event) {
        String username = txtIngresarUsuario.getText();
        String password = txtIngresarContraseña.getText();

        // Validar credenciales a través del controlador, que se conecta al servidor
        Object usuarioSeleccionado = loginControllerService.validarCredenciales(username, password);

        if (usuarioSeleccionado != null) {
            loginControllerService.procesarInicioSesion(usuarioSeleccionado, event);
        } else {
            Persistencia p = new Persistencia();
            p.guardarRegistroLog("Login", 2, "Se ha intentado ingresar a la aplicacion. Credenciales Incorrectas");
            mostrarMensaje("Login", "Error", "Credenciales Incorrectas", Alert.AlertType.ERROR);
        }
    }

    private void mostrarMensaje(String titulo, String encabezado, String contenido, AlertType tipo) {
        Alert alerta = new Alert(tipo);
        alerta.setTitle(titulo);
        alerta.setHeaderText(encabezado);
        alerta.setContentText(contenido);
        alerta.showAndWait();
    }
}
