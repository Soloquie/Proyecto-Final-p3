package uniquindio.finalproject.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import uniquindio.finalproject.mapping.dto.AdministradorDto;
import uniquindio.finalproject.mapping.dto.UsuarioDto;
import uniquindio.finalproject.request.LoginRequest;
import uniquindio.finalproject.view.CuentaUsuarioViewController;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class LoginController {

    private final String SERVER_HOST = "localhost";
    private final int SERVER_PORT = 12345;
    private Object usuarioAutenticado; // Guarda el usuario autenticado

    public Object validarCredenciales(String username, String password) {
        try (Socket socket = new Socket(SERVER_HOST, SERVER_PORT);
             ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
             ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream())) {

            outputStream.writeObject(new LoginRequest(username, password));
            outputStream.flush();

            Object response = inputStream.readObject();
            if (response instanceof UsuarioDto || response instanceof AdministradorDto) {
                this.usuarioAutenticado = response;
                return response;
            }

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void procesarInicioSesion(Object usuario, ActionEvent event) {
        if (usuario != null) {
            String vista = (usuario instanceof AdministradorDto)
                    ? "/uniquindio/finalproject/VistaGestionUsuario.fxml"
                    : "/uniquindio/finalproject/VistaCuentaDeUsuario.fxml";

            abrirVista(vista, event, "Cuenta de Usuario");
        } else {
            mostrarMensaje("Error", "Credenciales incorrectas", "Las credenciales que se han ingresado son incorrectas", Alert.AlertType.ERROR);
        }
    }

    private void abrirVista(String vista, ActionEvent event, String titulo) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(vista));
            Parent root = loader.load();

            if (usuarioAutenticado instanceof UsuarioDto) {
                CuentaUsuarioViewController controller = loader.getController();
                controller.setUsuarioActual((UsuarioDto) usuarioAutenticado);
            }

            Stage stage = new Stage();
            stage.setTitle(titulo);
            stage.setScene(new Scene(root));
            stage.show();

            ((Stage) ((Node) event.getSource()).getScene().getWindow()).close();
        } catch (IOException e) {
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
}
