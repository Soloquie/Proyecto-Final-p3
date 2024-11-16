package uniquindio.finalproject.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import uniquindio.finalproject.mapping.dto.AdministradorDto;
import uniquindio.finalproject.mapping.dto.UsuarioDto;
import uniquindio.finalproject.request.LoginRequest;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class LoginController {
    private final String SERVER_HOST = "localhost";
    private final int SERVER_PORT = 12345;

    public UsuarioDto validarCredenciales(String username, String password) {
        try (Socket socket = new Socket(SERVER_HOST, SERVER_PORT);
             ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
             ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream())) {

            // Enviar datos de login al servidor
            outputStream.writeObject(new LoginRequest(username, password));
            outputStream.flush();

            // Recibir respuesta
            Object response = inputStream.readObject();
            if (response instanceof UsuarioDto) {
                return (UsuarioDto) response;
            }

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null; // Retorna null si no se pudo validar
    }

    public void procesarInicioSesion(Object usuario, ActionEvent event) {
        String vista = "";
        String titulo = "";

        if (usuario instanceof AdministradorDto adminDto) {
            vista = "/uniquindio/finalproject/VistaGestionUsuario.fxml";
            titulo = "Gestion de Administrador";
        } else {
            vista = "/uniquindio/finalproject/VistaCuentaDeUsuario.fxml";
            titulo = "Gestion de Usuario";
        }
        abrirVista(vista, event, titulo);
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
}
